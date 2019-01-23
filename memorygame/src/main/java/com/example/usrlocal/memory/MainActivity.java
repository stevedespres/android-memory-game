package com.example.usrlocal.memory;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import game.Memory;
import game.MemoryException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Instance of the game
    private Memory game = Memory.getInstance();

    /**
     * Graphics elements in the main activity
     */
    protected Button playButton;
    protected RadioButton easyRadioButton;
    protected RadioButton middleRadioButton;
    protected RadioButton hardRadioButton;
    protected TextView nbPlayedGame;
    protected TextView nbWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get prefs (win games and played games) in the prefs files
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        game.setNbPlayedGames(prefs.getInt("nbGames", 0));//0 is the default value.
        game.setNbWin(prefs.getInt("nbWins", 0)); //0 is the default value.

        // Configuration of the graphics elements
        playButton = (Button) findViewById(R.id.buttonPlay);
        easyRadioButton = (RadioButton) findViewById(R.id.radioButtonEasy);
        middleRadioButton = (RadioButton) findViewById(R.id.radioButtonMiddle);
        hardRadioButton = (RadioButton) findViewById(R.id.radioButtonHard);
        nbPlayedGame = (TextView) findViewById(R.id.nbGames);
        nbWin = (TextView) findViewById(R.id.nbWins);
    }

    @Override
    protected void onStart() {
        super.onStart();
        playButton.setOnClickListener(this);
        nbWin.setText(Integer.toString(game.getNbWin()));
        nbPlayedGame.setText(Integer.toString(game.getNbPlayedGames()));
    }

    /**
     * Init the game with the right number of pairs
     *
     * @param nbPairs number of pairs
     * @return true if the game is instantiate correctly
     */
    public boolean initGame(final int nbPairs) {
        // Create a memory game
        try {
            game.init(nbPairs);
            return true;
        } catch (MemoryException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this).setTitle("Erreur").setMessage(e.getMessage()).setPositiveButton("OK", null);
            alert.show();
            return false;
        }
    }

    /**
     * On create Option Menu (toolbar)
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public int getNbPairsfromDifficulty() {
        if (easyRadioButton.isChecked()) {
            game.setDifficulty("Facile");
            game.setGameTimer(500L);
            return 4;
        }
        if (middleRadioButton.isChecked()) {
            game.setDifficulty("Moyen");
            game.setGameTimer(750L);
            return 5;
        }
        if (hardRadioButton.isChecked()) {
            game.setDifficulty("Difficile");
            game.setGameTimer(1000L);
            return 6;
        }
        return 0;
    }

    /**
     * Show Credits
     */
    private void showCredits() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this).setTitle("Crédits").setMessage("Cette application a été dévelopée par Guillaume COURTIN, Nathan DUBERNARD & Steve DESPRES, ISTIA EI5 IHMRV").setPositiveButton("OK", null);
        alert.show();
    }

    /**
     * Activate or deactivate the timer in game
     */
    private void activeTimer() {
        AlertDialog.Builder alert;
        game.setWithTime(!game.getWithTime());
        if (game.getWithTime())
            alert = new AlertDialog.Builder(this).setTitle("Timer").setMessage("Le jeu est configuré avec le contre la montre").setPositiveButton("OK", null);
        else
            alert = new AlertDialog.Builder(this).setTitle("Timer").setMessage("Le jeu est configuré sans le contre la montre").setPositiveButton("OK", null);
        alert.show();
    }

    /**
     * On options item selected : manage click on the action bar
     *
     * @param item item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.creditItem:
                showCredits();
                return true;
            case R.id.timerItem:
                activeTimer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * On click : manage click on the view components
     *
     * @param view view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonPlay:
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                if (initGame(getNbPairsfromDifficulty())) {
                    startActivity(intent);
                    finish();
                }
        }
    }
}
