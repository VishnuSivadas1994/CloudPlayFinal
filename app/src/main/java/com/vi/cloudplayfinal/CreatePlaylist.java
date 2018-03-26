package com.vi.cloudplayfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vi on 2018-03-20.
 */

public class CreatePlaylist extends SQLiteOpenHelper {

    public static final String PLAYLIST_NAME="Playlist1.db";
    public static final String createdbquery="CREATE DATABASE "+PLAYLIST_NAME;



    public CreatePlaylist(Context context) {
        super(context, PLAYLIST_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createdbquery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
