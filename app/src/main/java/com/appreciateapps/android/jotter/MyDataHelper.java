package com.appreciateapps.android.jotter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MyDataHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserNotes.db";
    public static final int DATABASE_VERSION = 1;

    public static class DBItem implements BaseColumns {
        public static final String TABLE = "notes";
        public static final String NOTE_COL = "note";
    }

    public static final String CREATE_STR =
            "CREATE TABLE " + DBItem.TABLE + " (" +
                    DBItem._ID + " INTEGER PRIMARY KEY," +
                    DBItem.NOTE_COL + " TEXT";


    public MyDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBItem.TABLE);
        onCreate(db);
    }
}
