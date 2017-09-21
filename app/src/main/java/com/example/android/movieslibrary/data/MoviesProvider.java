package com.example.android.movieslibrary.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;

public class MoviesProvider extends ContentProvider {

    public static final String LOG_TAG = MoviesProvider.class.getSimpleName();

    private static final int MOVIES = 100;

    private static final int MOVIES_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES, MOVIES);
        sUriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES + "/#", MOVIES_ID);
    }

    private MoviesDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:

                cursor = database.query(MoviesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MOVIES_ID:
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(MoviesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return insertMovies(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMovies(Uri uri, ContentValues values) {
        String name = values.getAsString(MoviesEntry.COLUMN_MOVIES_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Movies requires a name");
        }

        Integer gender = values.getAsInteger(MoviesEntry.COLUMN_MOVIES_GENDER);
        if (gender == null || !MoviesEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Movies requires valid gender");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(MoviesEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return updateMovies(uri, contentValues, selection, selectionArgs);
            case MOVIES_ID:
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateMovies(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    private int updateMovies(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(MoviesEntry.COLUMN_MOVIES_NAME)) {
            String name = values.getAsString(MoviesEntry.COLUMN_MOVIES_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Movies requires a name");
            }
        }

        if (values.containsKey(MoviesEntry.COLUMN_MOVIES_GENDER)) {
            Integer gender = values.getAsInteger(MoviesEntry.COLUMN_MOVIES_GENDER);
            if (gender == null || !MoviesEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Movies requires valid gender");
            }
        }

       if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(MoviesEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                rowsDeleted = database.delete(MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIES_ID:
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MoviesEntry.CONTENT_LIST_TYPE;
            case MOVIES_ID:
                return MoviesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
