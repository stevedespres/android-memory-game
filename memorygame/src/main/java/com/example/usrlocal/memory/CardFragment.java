package com.example.usrlocal.memory;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class CardFragment extends Fragment {
    private static final String ARG_IMAGE_VIEW_ID = "imageViewId";
    private static final String ARG_CARD_ID = "cardId";
    private static final String ARG_PAIR_ID = "pairId";

    // TODO: Rename and change types of parameters
    private int imageViewId;
    private int cardId;
    private int pairId;

    protected FrameLayout zone ;
    protected ImageView dos;
    protected ImageView face;


    public CardFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(int cardId, int pairId, int imageViewId) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CARD_ID, pairId);
        args.putInt(ARG_PAIR_ID, pairId);
        args.putInt(ARG_IMAGE_VIEW_ID, imageViewId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardId = getArguments().getInt(ARG_CARD_ID);
            pairId = getArguments().getInt(ARG_PAIR_ID);
            imageViewId = getArguments().getInt(ARG_IMAGE_VIEW_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_card, container, false);
        zone = (FrameLayout) v.findViewById(R.id.zone);
        dos = (ImageView)v.findViewById(R.id.dos);

        face = (ImageView)v.findViewById(R.id.face);
        face.setImageDrawable( this.getActivity().getDrawable(imageViewId));
        zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),imageViewId,Toast.LENGTH_LONG).show();
                dos.setVisibility(View.INVISIBLE);
                face.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }
}
