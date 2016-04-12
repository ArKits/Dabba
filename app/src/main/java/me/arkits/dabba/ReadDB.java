package me.arkits.dabba;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReadDB extends AppCompatActivity {

    private List<Emoji> emojiList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmojiAdapter eAdapter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_db);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        eAdapter = new EmojiAdapter(db.getAllEmojis());

        Log.d("Reading: ", "Reading all contacts..");
        List<Emoji> emojis = db.getAllEmojis();

        for (Emoji cn : emojis) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getLabel() + " ,Phone: " + cn.getText();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position ) {

                final int pos = position;

                context = recyclerView.getContext();


                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                emojiList = db.getAllEmojis();
                                Emoji emoji = emojiList.get(pos);
                                Toast.makeText(getApplicationContext(), emoji.getLabel() + " deleted", Toast.LENGTH_SHORT).show();
                                db.deleteEmoji(emoji);
                                eAdapter.notifyItemRemoved(pos);
                                eAdapter = new EmojiAdapter(db.getAllEmojis());
                                recyclerView.setAdapter(eAdapter);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();




            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ReadDB.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ReadDB.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }





    }

