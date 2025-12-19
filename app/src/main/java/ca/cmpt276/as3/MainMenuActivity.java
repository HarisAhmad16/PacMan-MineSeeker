package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import ca.cmpt276.as3.databinding.ActivityMainMenuBinding;
import ca.cmpt276.as3.model.GameLogic;


/**
 * Class that is the main menu
 * Contains the start game that will put the user in the game
 * Contains the options that allows the user to customize the game
 * Contains the help to show the user how to play, info on the creator,
 * and the resources used within the entire game
 */
public class MainMenuActivity extends AppCompatActivity {

    private Button helpButton, optionsButton, gameButton;
    private GameLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.cmpt276.as3.databinding.ActivityMainMenuBinding binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        logic = GameLogic.getInstance();

        helpButton = findViewById(R.id.helpButton);
        helpButtonClick();
        optionsButton = findViewById(R.id.optionsButton);
        optionsButtonClick();
        gameButton = findViewById(R.id.startGameButton);
        gameButtonClick();

        refreshButtons();
    }

    private void refreshButtons() {
        String buttons = OptionsActivity.getNumSize(this);
        String mines = OptionsActivity.getNumMine(this);
        logic.calculateRows(buttons);
        logic.calculateColumns(buttons);
        logic.calculateMines(mines);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshButtons();
    }

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c, MainMenuActivity.class);
    }

    private void helpButtonClick(){
        helpButton.setOnClickListener(view -> {
            Intent i = HelpActivity.makeLaunchIntent(MainMenuActivity.this);
            startActivity(i);
        });
    }

    private void optionsButtonClick(){
        optionsButton.setOnClickListener(view -> {
            Intent i = OptionsActivity.makeLaunchIntent(MainMenuActivity.this);
            startActivity(i);
        });
    }

    private void gameButtonClick(){
        gameButton.setOnClickListener(view -> {
            Intent i = GameActivity.makeLaunchIntent(MainMenuActivity.this);
            startActivity(i);
        });
    }
}