package me.arkits.dabba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archi on 4/8/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "emojisManager";

    // Contacts table name
    private static final String TABLE_EMOJIS = "emojis";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LABEL = "label";
    private static final String KEY_TEXT = "text";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMOJIS_TABLE =  "CREATE TABLE " + TABLE_EMOJIS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LABEL + " TEXT," + KEY_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_EMOJIS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOJIS);

        // Create tables again
        onCreate(db);

    }

    public void addEmoji(Emoji emoji) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LABEL, emoji.getLabel());
        values.put(KEY_TEXT, emoji.getText());

        // Inserting Row
        db.insert(TABLE_EMOJIS, null, values);
        db.close(); // Closing database connection
    }

     Emoji getEmoji(int id) {
         SQLiteDatabase db = this.getReadableDatabase();

         Cursor cursor = db.query(TABLE_EMOJIS, new String[] { KEY_ID,
                         KEY_LABEL, KEY_TEXT }, KEY_ID + "=?",
                 new String[] { String.valueOf(id) }, null, null, null, null);
         if (cursor != null)
             cursor.moveToFirst();

         Emoji emoji = new Emoji(Integer.parseInt(cursor.getString(0)),
                 cursor.getString(1), cursor.getString(2));
         // return contact
         return emoji;




    }

    public List<Emoji> getAllEmojis() {
        List<Emoji> emojiList = new ArrayList<Emoji>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMOJIS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Emoji emoji = new Emoji();
                emoji.setID(Integer.parseInt(cursor.getString(0)));
                emoji.setLabel(cursor.getString(1));
                emoji.setText(cursor.getString(2));

                emojiList.add(emoji);
            } while (cursor.moveToNext());
        }

        return emojiList;
    }


    public int getEmojiCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMOJIS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }

    public String getEmojiTextFromId(int id) {

        Emoji emoji = getEmoji(id);

        String text = emoji.getText();


        return text;
    }



    public int updateEmoji(Emoji emoji) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LABEL, emoji.getLabel());
        values.put(KEY_TEXT, emoji.getText());

        // updating row
        return db.update(TABLE_EMOJIS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(emoji.getID()) });
    }

    public void deleteEmoji(Emoji emoji) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMOJIS, KEY_ID + " = ?",
                new String[] { String.valueOf(emoji.getID()) });
        db.close();
    }






}
