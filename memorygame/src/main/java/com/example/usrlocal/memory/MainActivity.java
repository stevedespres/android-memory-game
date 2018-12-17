package com.example.usrlocal.memory;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import game.Memory;
import game.MemoryException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Memory game;

    private Button playButton;
    private RadioButton easyRadioButton;
    private RadioButton middleRadioButton;
    private RadioButton hardRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.buttonPlay);
        easyRadioButton  = (RadioButton) findViewById(R.id.radioButtonEasy);
        middleRadioButton  = (RadioButton) findViewById(R.id.radioButtonMiddle);
        hardRadioButton  = (RadioButton) findViewById(R.id.radioButtonHard);

    }

    /**
     * On start
     */
    @Override
    protected void onStart(){
        super.onStart();
        playButton.setOnClickListener(this);
    }

    public boolean initGame(final int nbPairs){
        // Create a memory game
        try {
            game = Memory.getInstance(nbPairs);
            return true;
        } catch (MemoryException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this).setTitle("Erreur").setMessage(e.getMessage()).setPositiveButton("OK", null);
            alert.show();
            return false;
        }
    }

    /**
     * On create Option Menu (toolbar)
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public int getNbPairsfromDifficulty(){
        if(easyRadioButton.isChecked()){
            return 4;
        }
        if(middleRadioButton.isChecked()){
            return 5;
        }
        if(hardRadioButton.isChecked()){
            return 6;
        }
        return 0;
    }

    /**
     * Show Credits
     */
    private void showCredits(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this).setTitle("Crédits").setMessage("Cette application a été dévelopée par Guillaume COURTIN, Nathan DUBERNARD & Steve DESPRES, ISTIA EI5 IHMRV").setPositiveButton("OK", null);
        alert.show();
    }

    /**
     * On options item selected : manage click on the action bar
     * @param item item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_credits:
                showCredits();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * On click : manage click on the view components
     * @param view view
     */
    @Override
    public void onClick(View view){

        switch(view.getId()){
            case R.id.buttonPlay:
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                if( initGame(getNbPairsfromDifficulty())){
                    intent.putExtra("GAME", game);
                    startActivity(intent);
                }
        }
    }


}
