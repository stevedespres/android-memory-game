package com.example.usrlocal.memory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.Serializable;

import game.Card;
import game.Memory;

public class CardFragment extends Fragment implements Serializable {
    /**
     * Variables allow the link between a card object and a fragment
     */
    private static final String ARG_CARD = "card";
    private Card card;

    /**
     * Graphics elements for a card
     */
    protected FrameLayout frame;
    protected ImageView backImage;
    protected ImageView frontImage;

    /**
     * Fragment constructor
     */
    public CardFragment() {
    }

    /**
     * Function to instanciate a new fragment
     * Automatically call
     *
     * @param card the associate card object
     * @return fragment
     */
    public static CardFragment newInstance(Card card) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD, card);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            card = (Card) getArguments().getSerializable(ARG_CARD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_card, container, false);
        // Association with graphics elements
        frame = v.findViewById(R.id.frame);
        backImage = v.findViewById(R.id.backImage);
        frontImage = v.findViewById(R.id.frontImage);
        frontImage.setImageDrawable(this.getActivity().getDrawable(card.getImageViewId()));
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Listener for a card click
                backImage.setVisibility(View.INVISIBLE);
                frontImage.setVisibility(View.VISIBLE);
                if (Memory.getInstance().pickACard(card)) {
                    ((GameActivity) getActivity()).endGame(true);
                }
                ((GameActivity) getActivity()).resetWrongCards();
            }
        });
        return v;
    }

    /**
     * Function to return wrong cards
     */
    public void wrongCard() {
        backImage.setVisibility(View.VISIBLE);
        frontImage.setVisibility(View.INVISIBLE);
    }

    /**
     * Getter for the card object
     *
     * @return card
     */
    public Card getCard() {
        return card;
    }
}
