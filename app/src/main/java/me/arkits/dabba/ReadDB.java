package me.arkits.dabba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ReadDB extends AppCompatActivity {

    private List<Emoji> emojiList = new ArrayList<>();

    private RecyclerView recyclerView;
    private EmojiAdapter eAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_db);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        eAdapter = new EmojiAdapter(emojiList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);

        prepareEmojiData();



    }

    private void prepareEmojiData(){
        DatabaseHandler db = new DatabaseHandler(this);

        for(int i=1;i<=db.getEmojiCount();i++){
            Emoji emoji = db.getEmoji(i);
            emojiList.add(emoji);
        }



        eAdapter.notifyDataSetChanged();
    }
}
