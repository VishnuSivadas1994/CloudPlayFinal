package com.vi.cloudplayfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vi on 2018-03-23.
 */

public class AddURItoDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="songpath.db";
    public static final String TABLE_NAME="path1";
    public static final String COL_1="song_path";

    String create="create table "+TABLE_NAME+" ("+COL_1+" text)";



    public AddURItoDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public boolean insertpath(String path){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        String ss = path;
        contentValues.put(COL_1,ss);

        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<String> viewpath(){

        List<String> playlist = new ArrayList<String>();
        String selectQuery = "SELECT song_path FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String song;
                song=cursor.getString(0);

                playlist.add(song);
            } while (cursor.moveToNext());
        }
        return playlist;
    }
}
