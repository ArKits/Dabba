package me.arkits.dabba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mlabel, mtext;
    private Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlabel = (EditText) findViewById(R.id.labl);
        mtext = (EditText) findViewById(R.id.txt);
        mAdd = (Button) findViewById(R.id.addDB);

        mAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addDB:

                Log.d("Insert: ", "AddDB clicked");

                DatabaseHandler db = new DatabaseHandler(this);
                String label = mlabel.toString();
                String text = mtext.toString();

                db.addEmoji( new Emoji(label, text));

                Log.d("Insert: ", "Added emoji of label");


                break;
            // case R.id.register:
            //     Intent i = new Intent(this, Register.class);
            //     startActivity(i);
            //     break;

            default:
                break;
        }

    }
}


