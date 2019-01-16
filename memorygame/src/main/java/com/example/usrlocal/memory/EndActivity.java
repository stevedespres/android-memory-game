package com.example.usrlocal.memory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import game.Memory;

public class EndActivity extends Activity {

    private boolean isWin;

    /**
     * Graphic elements for the end activity
     */
    protected TextView winText;
    protected Button menuButton;
    protected Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        // Get the result of the game (win or lose)
        Intent intent = getIntent();
        isWin = intent.getBooleanExtra("win", false);
        // Initialisation of the activity
        winText = findViewById(R.id.winText);
        menuButton = findViewById(R.id.menuButton);
        quitButton = findViewById(R.id.quitButton);
    }
    @Override
    protected void onStart() {
        super.onStart();
        // if the game is win
        if (isWin) {
            // Update score and text
            winText.setText("Vous avez gagné !");
            Memory.getInstance().setNbPlayedGames(Memory.getInstance().getNbPlayedGames() + 1);
            Memory.getInstance().setNbWin(Memory.getInstance().getNbWin() + 1);
        } else {
            // Update score and text
            Memory.getInstance().setNbPlayedGames(Memory.getInstance().getNbPlayedGames() + 1);
            winText.setText("Vous avez perdu, le temps est écoulé !");
        }

        // Listener for the menu button
        // Allow to return to the menu
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(EndActivity.this, MainActivity.class);
                startActivity(menuIntent);
                finish();
            }
        });

        // Listener for the menu button
        // Allow to return to quit the app
        // Save the score in a pref file
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.PREFS_FILE, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("nbParties", Memory.getInstance().getNbPlayedGames());
                editor.putInt("nbVictoires", Memory.getInstance().getNbWin());
                editor.apply();
                finish();
            }
        });

    }
}
