package com.example.usrlocal.memory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import game.Memory;

public class EndActivity extends Activity {

    private boolean isWin;
    protected TextView winText;
    private Button menuButton;
    private Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent intent = getIntent();
        isWin = intent.getBooleanExtra("win", false);
        winText = findViewById(R.id.winText);
        menuButton = findViewById(R.id.menuButton);
        quitButton = findViewById(R.id.quitButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isWin) {
            winText.setText("Vous avez gagné !");
            Memory.getInstance().setNbPartiesJouees(Memory.getInstance().getNbPartiesJouees() + 1);
            Memory.getInstance().setNbVictoires(Memory.getInstance().getNbVictoires() + 1);
        } else {
            Memory.getInstance().setNbPartiesJouees(Memory.getInstance().getNbPartiesJouees() + 1);
            winText.setText("Vous avez perdu, le temps est écoulé !");
        }

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(EndActivity.this, MainActivity.class);
                startActivity(menuIntent);
                finish();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.PREFS_FILE, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("nbParties", Memory.getInstance().getNbPartiesJouees());
                editor.putInt("nbVictoires", Memory.getInstance().getNbVictoires());
                editor.apply();
                finish();
            }
        });

    }
}
