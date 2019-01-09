package com.example.usrlocal.memory;

import android.content.Context;
import android.content.Intent;
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

import game.Card;
import game.Memory;
import game.MemoryException;

public class CardFragment extends Fragment {
    /**
     * Attributs faisant la liason avec la carte côté métier
     */
    private static final String ARG_CARD = "card";
    private Card card;

    /**
     * Elements graphiques de la carte
     */
    protected FrameLayout zone ;
    protected ImageView dos;
    protected ImageView face;


    public CardFragment() {
    }

    /**
     * Méthode qui permet d'instancier un nouveau fragment ici une carte
     * Automatiquement appelé
     * @param card objet métier représentant la carte
     * @return le fragment généré
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
            card.setFrament(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_card, container, false);
        zone = v.findViewById(R.id.zone);
        dos = v.findViewById(R.id.dos);

        face = v.findViewById(R.id.face);
        face.setImageDrawable( this.getActivity().getDrawable(card.getImageViewId()));
        zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dos.setVisibility(View.INVISIBLE);
                    face.setVisibility(View.VISIBLE);
                    switch(Memory.getInstance(0).pickACard(card)){
                        case 0:
                            Toast.makeText(getContext(),"First card :" + card.getPairId(),Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(getContext(),"Second card :" + card.getPairId(),Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toast.makeText(getContext(),"Fin du jeu",Toast.LENGTH_LONG).show();
                            break;
                        case 3:
                            Toast.makeText(getContext(),"Mauvaise carte :" + card.getPairId(),Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getContext(),"Erreur :" + card.getPairId(),Toast.LENGTH_LONG).show();
                    }
                } catch (MemoryException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    /**
     * Méthode qui permet de remettre la carte dans un état inconnu lorsque c'est la mauvaise
     */
    public void wrongCard(){
        dos.setVisibility(View.VISIBLE);
        face.setVisibility(View.INVISIBLE);
    }
}
