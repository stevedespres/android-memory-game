package com.example.usrlocal.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Card;
import game.Memory;

public class GameActivity extends AppCompatActivity {

    protected GridLayout container;
    private Memory game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        // Get Year data from UEsChoicesActivity
        container = (GridLayout) findViewById(R.id.container);
        game = (Memory) intent.getSerializableExtra("GAME");
        switch (game.getNPairs()){
            case 4:
            container.setColumnCount(4);
            container.setRowCount(2);
            break;
            case 5:
                container.setColumnCount(2);
                container.setRowCount(5);
                break;
            case 6:
                container.setColumnCount(4);
                container.setRowCount(3);
                break;
        }
        initGameView();

    }

    private void initGameView(){

        List<CardFragment> cardsFragments = new ArrayList<>();
        List<Card> cards = game.getCardsList();

        // Create card fragments
        for(Card c : cards){
            Bundle b = new Bundle();
            b.putSerializable("card", c);

            CardFragment cf = new CardFragment();
            cf.setArguments(b);

            cardsFragments.add(cf);

        }
        // Shuffle cards
        Collections.shuffle(cardsFragments);

        for(CardFragment cf : cardsFragments){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,cf)
                    .commit();
        }
    }
}
