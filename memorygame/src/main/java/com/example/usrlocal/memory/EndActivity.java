package com.example.usrlocal.memory;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class EndActivity extends Activity {

    private boolean isWin;
    protected TextView winText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .5));

        Intent intent = getIntent();
        isWin = intent.getBooleanExtra("win", false);
        winText = (TextView) findViewById(R.id.winText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isWin) {
            winText.setText("Vous avez gagné !");
        } else {
            winText.setText("Vous avez perdu, le temps est écoulé !");
        }
    }
}
