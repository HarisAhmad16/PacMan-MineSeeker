package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import ca.cmpt276.as3.databinding.ActivityGameBinding;
import ca.cmpt276.as3.model.GameLogic;

/**
 * Class that controls the entire game being played
 * Dynamically builds the board and places mines randomly
 * Scan animations and sound added on user click
 * Correctly showcases how many mines and scans have been used
 * Pop up dialog after all mines found taking user back to the main menu
 */
public class GameActivity extends AppCompatActivity {

    private static int NUM_COLS;
    private static int NUM_ROWS;
    private static int NUM_MINES;
    private int outOfMines;
    private int scans;
    Button[][] buttons;
    String[][] temp;

    MediaPlayer pacmanFound;
    MediaPlayer pacmanNotFound;

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, GameActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.cmpt276.as3.databinding.ActivityGameBinding binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        GameLogic logic = GameLogic.getInstance();
        NUM_COLS = logic.getCol();
        NUM_ROWS = logic.getRow();
        NUM_MINES = logic.getNumOfMines();
        buttons = new Button[NUM_ROWS][NUM_COLS];
        temp = new String[NUM_ROWS][NUM_COLS];
        pacmanFound = MediaPlayer.create(GameActivity.this, R.raw.pacmanfound);
        pacmanNotFound = MediaPlayer.create(GameActivity.this, R.raw.pacmannotfound);
        populateButtons();
        randomizeMines();
        displayGameScans();
        displayGameText();
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);

        for(int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < NUM_COLS; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.getBackground().setAlpha(95);
                button.setTextColor(Color.WHITE);

                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setPadding(0,0,0,0);

                button.setOnClickListener(v -> gridButtonClicked(FINAL_COL, FINAL_ROW));
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void randomizeMines() {
        Random rand = new Random();
        int row;
        int col;

        for (int i = 0; i < NUM_MINES; i++) {

            col = rand.nextInt(NUM_COLS);
            row = rand.nextInt(NUM_ROWS);

            if (buttons[row][col].getText().toString().equals(" ")) {
                i--;
            } else {
                buttons[row][col].setText(" ");
            }
        }
    }

    private void setNumbers(){
        int number;

        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLS; col++){
                if(!buttons[row][col].getText().toString().equals(" ")){
                    number = 0;

                    for(int currCol = 0; currCol < NUM_COLS; currCol++){
                        if(buttons[row][currCol].getText().toString().equals(" ")){
                            number++;
                        }
                    }

                    for(int currRow = 0; currRow < NUM_ROWS; currRow++){
                        if(buttons[currRow][col].getText().toString().equals(" ")){
                            number++;
                        }
                    }

                    temp[row][col] = String.valueOf(number);
                }
            }
        }
    }

    private String getNumbers(int row, int col){
        setNumbers();
        return temp[row][col];
    }

    // Regex expressions found from StackOverflow
    // https://stackoverflow.com/questions/4736/learning-regular-expressions
    private void updateTheNumbers(){
        setNumbers();
        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLS; col++){
                if(buttons[row][col].getText().toString().matches("[0-9]+")){
                    buttons[row][col].setText(temp[row][col]);
                }
            }
        }
    }

    private void displayGameText(){
        TextView mines = findViewById(R.id.numberOfMinesFound);
        String mineFounds = "Found " + outOfMines + " of " + NUM_MINES + " mines";
        mines.setText(mineFounds);
    }

    private void displayGameScans(){
        TextView scan = findViewById(R.id.scansUsed);
        String scansDone = "# Scans used: " + scans;
        scan.setText(scansDone);
    }

    private void winner(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setIcon(R.drawable.pacman_icon)
                .setTitle("All The Pac-Man Have Been Rescued!")
                .setMessage("Thank you for finding all of us! We are now being sent back to our arcade world! All the best! - The Pac-Man")
                .setPositiveButton("OK!", (dialogInterface, i) -> finish())
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void scanAnimation(int scanRow, int scanCol){
        for(int i = 0; i < NUM_COLS; i++){
            Animation animation = AnimationUtils.loadAnimation(GameActivity.this, R.anim.scan);
            buttons[scanRow][i].startAnimation(animation);
        }

        for(int i = 0; i < NUM_ROWS; i++){
            Animation animation = AnimationUtils.loadAnimation(GameActivity.this, R.anim.scan);
            buttons[i][scanCol].startAnimation(animation);
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                int height = button.getHeight();
                button.setMinWidth(width);
                button.setMaxWidth(width);
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void pacmanFoundSound(){
        pacmanFound.start();
    }

    private void pacmanNotFoundSound(){
        pacmanNotFound.start();
    }

    private void gridButtonClicked(int col, int row) {

        Button button = buttons[row][col];

        // Button has a number and has already been scanned
        if(button.getText().toString().matches("[0-9]+")){
            pacmanNotFoundSound();
            return;
        }

        // User clicks on an already found mine
        if(button.getText().toString().equals("  ")){
            scans++;
            displayGameScans();
            buttons[row][col].setText(getNumbers(row, col));
            scanAnimation(row, col);
            pacmanNotFoundSound();
        }

        // User clicks on a button that has a mine
        else if(button.getText().toString().equals(" ")){
            button.setText("  ");
            outOfMines++;
            displayGameText();

            // Lock the buttons size
            lockButtonSizes();
            pacmanFoundSound();

            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_icon);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            if(outOfMines == NUM_MINES){
                winner();
            }
        }

        // User clicks on a button that holds a number
        else{
            scans++;
            displayGameScans();
            buttons[row][col].setText(getNumbers(row, col));
            pacmanNotFoundSound();
            scanAnimation(row, col);
        }

        // Update the numbers of the buttons
        updateTheNumbers();
    }
}