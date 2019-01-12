package com.example.usrlocal.memory;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Card;
import game.Memory;

public class GameActivity extends AppCompatActivity {

    private Memory game;
    protected GridLayout container;
    protected List<CardFragment> cardsFragments = new ArrayList<>();
    protected ProgressBar timerBar = null;
    private TimerGame timer = new TimerGame();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        timerBar = (ProgressBar) findViewById(R.id.timerBar);
        // Get Year data from UEsChoicesActivity
        container = (GridLayout) findViewById(R.id.container);
        game = (Memory) intent.getSerializableExtra("GAME");
        switch (game.getNPairs()) {
            case 4:
                container.setColumnCount(3);
                container.setRowCount(4);
                break;
            case 5:
                container.setColumnCount(3);
                container.setRowCount(4);
                break;
            case 6:
                container.setColumnCount(3);
                container.setRowCount(4);
                break;
        }
        initGameView();
        timer.execute();

    }

    private void initGameView() {
        // Create card fragments
        for (Card c : game.getCardsList()) {
            Bundle b = new Bundle();
            b.putSerializable("card", c);

            CardFragment cf = new CardFragment();
            cf.setArguments(b);

            cardsFragments.add(cf);

        }
        // Shuffle cards
        Collections.shuffle(cardsFragments);

        for (CardFragment cf : cardsFragments) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, cf)
                    .commit();
        }
    }

    public void resetWrongCards() {
        for (CardFragment c : cardsFragments) {
            if (!c.getCard().isVisible())
                c.wrongCard();
        }
    }

    public void endGame(boolean isWin) {
        Intent endActivityIntent = new Intent(GameActivity.this, EndActivity.class);
        if (isWin) {
            timer.cancel(true);
            endActivityIntent.putExtra("win", true);
            startActivity(endActivityIntent);
        } else {
            endActivityIntent.putExtra("win", false);
            startActivity(endActivityIntent);
        }
    }

    private class TimerGame extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            timerBar.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            timerBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String result = "";
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result += i;
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            endGame(false);
        }
    }
}
