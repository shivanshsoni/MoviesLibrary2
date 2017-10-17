package com.example.android.movieslibrary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;

public class MoviesDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MoviesDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "shelter.db";

    private static final int DATABASE_VERSION = 2;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " ("
                + MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MoviesEntry.COLUMN_MOVIES_NAME + " TEXT NOT NULL, "
                + MoviesEntry.COLUMN_MOVIES_GENRE + " INTEGER NOT NULL, "
                + MoviesEntry.COLUMN_MOVIES_RATING + " INTEGER NOT NULL, "
                + MoviesEntry.COLUMN_MOVIES_SUMMARY + " TEXT);";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeQueryRating = "ALTER TABLE " + MoviesEntry.TABLE_NAME + " ADD COLUMN " + MoviesEntry.COLUMN_MOVIES_RATING + " INTEGER NOT NULL DEFAULT 0";
        String upgradeQuerySummary = "ALTER TABLE " + MoviesEntry.TABLE_NAME + " ADD COLUMN " + MoviesEntry.COLUMN_MOVIES_SUMMARY+ " TEXT";
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL(upgradeQueryRating);
            db.execSQL(upgradeQuerySummary);
        }
    }
}