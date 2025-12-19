package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.as3.databinding.ActivityHelpBinding;

/**
 * Class that is the help page of the game
 * Shows user how to play and info about the creator
 */
public class HelpActivity extends AppCompatActivity {

    TextView linkTextView;
    private Button resourceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.cmpt276.as3.databinding.ActivityHelpBinding binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        linkTextView = findViewById(R.id.creatorText);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        resourceButton = findViewById(R.id.resourceButton);
        resourceButtonClick();
    }

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c, HelpActivity.class);
    }

    private void resourceButtonClick(){
        resourceButton.setOnClickListener(view -> {
            Intent i = HelpResourceActivity.makeLaunchIntent(HelpActivity.this);
            startActivity(i);
        });
    }
}