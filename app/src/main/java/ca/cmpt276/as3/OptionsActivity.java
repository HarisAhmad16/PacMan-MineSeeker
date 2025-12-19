package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.as3.databinding.ActivityOptionsBinding;

/**
 * Class that is the options of the game
 * User can change the game board size and
 * number of mines where it will automatically
 * save
 */
public class OptionsActivity extends AppCompatActivity {

    public static final String SIZE = "Size";
    public static final String BOARD_PREFS = "AppPrefs";
    public static final String MINE = "Mine";
    public static final String MINE_PREFS = "AppPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.cmpt276.as3.databinding.ActivityOptionsBinding binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        createBoardSizeButtons();
        createMineSizeButtons();
    }

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c, OptionsActivity.class);
    }

    private void createBoardSizeButtons() {
        RadioGroup sizeOfBoard = findViewById(R.id.radio_group_board_size);
        String[] numSizes = getResources().getStringArray(R.array.board_size);

        // Create the buttons
        for(String i : numSizes){
            final String numSize = i;

            RadioButton button = new RadioButton(this);
            button.setTextColor(Color.WHITE);
            button.setText(i);

            // Set on click callbacks
            button.setOnClickListener(view -> saveNumSize(numSize));

            // Add to radio group
            sizeOfBoard.addView(button);

            // Select default button
            if(numSize.equals(getNumSize(this))){
                button.setChecked(true);
            }
        }
    }

    private void saveNumSize(String numSize) {
        SharedPreferences prefs = this.getSharedPreferences(BOARD_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SIZE, numSize);
        editor.apply();
    }

    public static String getNumSize(Context context){
        SharedPreferences prefs = context.getSharedPreferences(BOARD_PREFS, MODE_PRIVATE);
        String defaultSize = context.getResources().getString(R.string.default_board_size);
        return prefs.getString(SIZE, defaultSize);
    }

    private void createMineSizeButtons() {
        RadioGroup sizeOfMine = findViewById(R.id.radio_group_mine_size);
        String[] numMines = getResources().getStringArray(R.array.mine_size);

        // Create the buttons
        for(String i : numMines){
            final String numMine = i;

            RadioButton button = new RadioButton(this);
            button.setTextColor(Color.WHITE);
            button.setText(i);

            // Set on click callbacks
            button.setOnClickListener(view -> saveMineSize(numMine));

            // Add to radio group
            sizeOfMine.addView(button);

            // Select default button
            if(numMine.equals(getNumMine(this))){
                button.setChecked(true);
            }
        }
    }

    private void saveMineSize(String numMine) {
        SharedPreferences prefs = this.getSharedPreferences(MINE_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(MINE, numMine);
        editor.apply();
    }

    public static String getNumMine(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MINE_PREFS, MODE_PRIVATE);
        String defaultMineSize = context.getResources().getString(R.string.default_mine_size);
        return prefs.getString(MINE, defaultMineSize);
    }
}