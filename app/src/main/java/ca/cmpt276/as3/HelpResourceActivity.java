package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.as3.databinding.ActivityHelpResourceBinding;


/**
 * Class that contains the resources used as citations
 * Can be clicked on to access the websites
 */
public class HelpResourceActivity extends AppCompatActivity {

    TextView cite1, cite2, cite3, cite4, cite5, cite6, cite7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.cmpt276.as3.databinding.ActivityHelpResourceBinding binding = ActivityHelpResourceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        cite1 = findViewById(R.id.resource1);
        cite1.setMovementMethod(LinkMovementMethod.getInstance());

        cite2 = findViewById(R.id.resource2);
        cite2.setMovementMethod(LinkMovementMethod.getInstance());

        cite3 = findViewById(R.id.resource3);
        cite3.setMovementMethod(LinkMovementMethod.getInstance());

        cite4 = findViewById(R.id.resource4);
        cite4.setMovementMethod(LinkMovementMethod.getInstance());

        cite5 = findViewById(R.id.resource5);
        cite5.setMovementMethod(LinkMovementMethod.getInstance());

        cite6 = findViewById(R.id.resource6);
        cite6.setMovementMethod(LinkMovementMethod.getInstance());

        cite7 = findViewById(R.id.resource7);
        cite7.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c, HelpResourceActivity.class);
    }

}