package com.example.usrlocal.memory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

import game.Card;
import game.Memory;
import game.MemoryException;

public class CardFragment extends Fragment implements Serializable {
    /**
     * Attributs faisant la liason avec la carte côté métier
     */
    private static final String ARG_CARD = "card";
    private Card card;

    /**
     * Elements graphiques de la carte
     */
    protected FrameLayout zone;
    protected ImageView dos;
    protected ImageView face;


    public CardFragment() {
    }

    /**
     * Méthode qui permet d'instancier un nouveau fragment ici une carte
     * Automatiquement appelé
     *
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_card, container, false);
        zone = v.findViewById(R.id.zone);
        dos = v.findViewById(R.id.dos);

        face = v.findViewById(R.id.face);
        face.setImageDrawable(this.getActivity().getDrawable(card.getImageViewId()));
        zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dos.setVisibility(View.INVISIBLE);
                    face.setVisibility(View.VISIBLE);
                    if (Memory.getInstance(0).pickACard(card)) {
                        ((GameActivity) getActivity()).endGame(true);
                    }
                    ((GameActivity) getActivity()).resetWrongCards();
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
    public void wrongCard() {
        dos.setVisibility(View.VISIBLE);
        face.setVisibility(View.INVISIBLE);
    }

    public Card getCard() {
        return card;
    }
}
