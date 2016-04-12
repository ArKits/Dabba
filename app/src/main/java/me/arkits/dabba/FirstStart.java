package me.arkits.dabba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstStart extends AppCompatActivity implements View.OnClickListener {

    private Button startApp, openSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);


        startApp = (Button)findViewById(R.id.go);
        openSettings = (Button)findViewById(R.id.Settings);

        startApp.setOnClickListener(this);
        openSettings.setOnClickListener(this);


        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(FirstStart.this);
        SharedPreferences.Editor edit = sp.edit();

        edit.putBoolean("IS_LOGIN", true);
        edit.commit();

        Log.d("Insert: ", "Adding default emojis");

        DatabaseHandler db = new DatabaseHandler(this);

        db.addEmoji( new Emoji("⊂(▀¯▀⊂)", "⊂(▀¯▀⊂)"));

        db.addEmoji( new Emoji("ᕕ(⌐■_■)ᕗ ♪♬", "ᕕ(⌐■_■)ᕗ ♪♬"));

        db.addEmoji( new Emoji("༼つ ◕_◕ ༽つ", "༼つ ◕_◕ ༽つ"));

        db.addEmoji( new Emoji("▐  ⊙ ▃ ⊙ ▐", "▐  ⊙ ▃ ⊙ ▐"));








    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go:

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.Settings:

                Intent c = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
                startActivity(c);
                break;

            default:
                break;
        }

    }
}
