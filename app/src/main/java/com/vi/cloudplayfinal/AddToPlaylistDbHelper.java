package com.vi.cloudplayfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vi on 2018-03-20.
 */

public class AddToPlaylistDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="playlist.db";
    public static final String TABLE_NAME="prototype";
    public static final String COL_1="song_name";

    String ss;

    String create="create table "+TABLE_NAME+" ("+COL_1+" text)";

    public AddToPlaylistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertdata(String song){


        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        ss=song;
        contentValues.put(COL_1,ss);

        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;


    }

    public List<String> viewplaylist(){

        List<String> playlist = new ArrayList<String>();
        String selectQuery = "SELECT song_name FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String song;
                song=cursor.getString(0);

                // Adding contact to list
                playlist.add(song);
            } while (cursor.moveToNext());
        }
        return playlist;



    }

}
