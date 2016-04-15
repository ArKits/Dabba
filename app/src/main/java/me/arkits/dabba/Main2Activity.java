package me.arkits.dabba;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Emoji> emojiList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmojiAdapter eAdapter;
    private Context context;

   // private EditText mlabel, mtext;

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context = recyclerView.getContext();

                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialog_add);

                final EditText dialogText = (EditText) dialog.findViewById(R.id.dialogLabel);

                Button dialogAdd = (Button) dialog.findViewById(R.id.dAdd);

                Button dialogPaste = (Button) dialog.findViewById(R.id.paste);

                dialogAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseHandler db = new DatabaseHandler(context);
                        final String dLabel = dialogText.getText().toString();
                        db.addEmoji( new Emoji(dLabel, dLabel));
                        Log.d("Insert: ", "Added emoji of label "+dLabel);
                        eAdapter = new EmojiAdapter(db.getAllEmojis());
                        recyclerView.setAdapter(eAdapter);

                        dialog.dismiss();
                    }
                });

                dialogPaste.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String pasteText;

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        if(clipboard.hasPrimaryClip()== true){
                            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                            pasteText = item.getText().toString();
                            dialogText.setText(pasteText);

                        }else{

                            Toast.makeText(getApplicationContext(), "Nothing to Paste", Toast.LENGTH_SHORT).show();

                        }


                    }
                });







                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        eAdapter = new EmojiAdapter(db.getAllEmojis());

        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);

        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);


        recyclerView.addOnItemTouchListener(new ReadDB.RecyclerTouchListener(getApplicationContext(), recyclerView, new ReadDB.ClickListener() {
            @Override
            public void onClick(View view, int position) {

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
                     .show();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

    if (id == R.id.about) {

            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);

        }

        else if (id == R.id.settings_input) {

            Intent c = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
            startActivity(c);

        }

        else if (id == R.id.reload_firststart) {

            Intent c = new Intent(this, FirstStart.class);
            startActivity(c);

        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }






}
