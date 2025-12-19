package ca.cmpt276.as3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ca.cmpt276.as3.databinding.ActivityMainBinding;

/**
 * Class that welcomes the user to the game
 * Contains animations and will automatically
 * proceed if user does not click the main menu
 */
public class MainActivity extends AppCompatActivity {

    private Button mainMenu, skip;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.cmpt276.as3.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        skip = findViewById(R.id.skip);
        skip.setVisibility(View.VISIBLE);
        mainMenu = findViewById(R.id.mainMenuButton);
        skipButtonClick();
        mainMenuButtonClick();
        startTitleAnimation();
        startFindAnimation();
        startButtonAnimation();
        timeAfterAnimation();

        // 4 second launch to menu after animations
        // total of 5000 ms of animation
        // 9000ms - 5000ms = 4000ms = 4 seconds
        handler  = new Handler();
        handler.postDelayed(run, 9000);
    }

    private void timeAfterAnimation(){
        Handler animationAfterTime = new Handler();
        animationAfterTime.postDelayed(time, 5300);
    }

    private void skipButtonClick(){
        skip.setOnClickListener(view -> {
            Intent i = MainMenuActivity.makeLaunchIntent(MainActivity.this);
            startActivity(i);
            handler.removeCallbacks(run);
            finish();
        });
    }

    private void mainMenuButtonClick(){
        mainMenu.setOnClickListener(view -> {
            Intent i = MainMenuActivity.makeLaunchIntent(MainActivity.this);
            startActivity(i);
            handler.removeCallbacks(run);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Animations were found from
    // https://www.journaldev.com/9481/android-animation-example
    private void startTitleAnimation(){
        TextView title = findViewById(R.id.mainTitle);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadein);
        title.startAnimation(animation);
    }

    private void startFindAnimation(){
        TextView findPacMan = findViewById(R.id.findMainTitle);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.slowfadein);
        findPacMan.startAnimation(animation);
    }

    private void startButtonAnimation(){
        Button button = findViewById(R.id.mainMenuButton);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.slidedown);
        button.startAnimation(animation);
    }

    Runnable run = () -> {
        Intent i = MainMenuActivity.makeLaunchIntent(MainActivity.this);
        startActivity(i);
        finish();
    };

    Runnable time = () -> skip.setVisibility(View.GONE);
}