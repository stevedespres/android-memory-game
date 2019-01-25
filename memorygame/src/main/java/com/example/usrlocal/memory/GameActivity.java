package com.example.usrlocal.memory;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Card;
import game.Memory;

public class GameActivity extends AppCompatActivity {

    // Instance of the game
    private Memory game = Memory.getInstance();

    /**
     * Graphics elements
     */
    protected GridLayout container;
    protected List<CardFragment> cardsFragments = new ArrayList<>();
    protected ProgressBar timerBar = null;
    protected TextView titleInGame;

    //Game timer
    private TimerGame timer = new TimerGame();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initialisation of the graphics elements
        titleInGame = findViewById(R.id.titleInGame);
        timerBar = findViewById(R.id.timerBar);
        container = findViewById(R.id.container);
        switch (game.getNPairs()) {
            case 4:
                container.setColumnCount(2);
                container.setRowCount(4);
                break;
            case 5:
                container.setColumnCount(2);
                container.setRowCount(5);
                break;
            case 6:
                container.setColumnCount(3);
                container.setRowCount(4);
                break;
        }
        //Initialisation of the game view
        initGameView();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //Start the timer
        if(game.getWithTime())
            timer.execute();
        else
            findViewById(R.id.timerLayout).setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause(){
        super.onPause();
        timer.cancel(true);
        finish();
    }

    private void initGameView() {
        // Create all fragments
        // One for each card in the game
        for (Card c : game.getCards()) {
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
        //Update the title with the difficulty
        titleInGame.setText("Memory : " + game.getDifficulty());
    }

    /**
     * Function allow to return the wrong cards
     */
    public void resetWrongCards() {
        for (CardFragment c : cardsFragments) {
            if (!c.getCard().isVisible())
                c.wrongCard();
        }
    }

    /**
     * Function call when the game is ended
     *
     * @param isWin boolean if the game is win
     */
    public void endGame(boolean isWin) {
        Intent endActivityIntent = new Intent(GameActivity.this, EndActivity.class);
        if (isWin) {
            timer.cancel(true);
            endActivityIntent.putExtra("win", true);
            startActivity(endActivityIntent);
            finish();
        } else {
            endActivityIntent.putExtra("win", false);
            startActivity(endActivityIntent);
        }
    }

    /**
     * TimerGame class
     */
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
            for (int i = 0; i <= 100; i++) {
                if(this.isCancelled())
                    break;
                try {
                    Thread.sleep(game.getGameTimer());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
