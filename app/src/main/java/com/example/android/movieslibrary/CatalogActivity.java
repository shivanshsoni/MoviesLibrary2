package com.example.android.movieslibrary;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;

import java.util.HashMap;
import java.util.Map;

public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIES_LOADER = 0;

    MoviesCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView moviesListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        moviesListView.setEmptyView(emptyView);

        mCursorAdapter = new MoviesCursorAdapter(this, null);
        moviesListView.setAdapter(mCursorAdapter);

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
            Uri currentMoviesUri = ContentUris.withAppendedId(MoviesEntry.CONTENT_URI, id);

                intent.setData(currentMoviesUri);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(MOVIES_LOADER, null, this);
    }

    public HashMap<String, Integer> getDummyMoviesMap(){
        HashMap<String, Integer> dummyMoviesMap = new HashMap<>();

        dummyMoviesMap.put("Intersteller", MoviesEntry.GENDER_ACTION);
        dummyMoviesMap.put("Jaws", MoviesEntry.GENDER_ACTION);
        dummyMoviesMap.put("Toy Story", MoviesEntry.GENDER_ANIMATION);
        dummyMoviesMap.put("The Matrix", MoviesEntry.GENDER_SCIFI);
        dummyMoviesMap.put("The Notebook", MoviesEntry.GENDER_ROMANCE);
        return dummyMoviesMap;
    }

    private void insertMovies() {
        HashMap<String,Integer> dummyMovieMap = getDummyMoviesMap();

        for (String key : dummyMovieMap.keySet()) {
            ContentValues values = new ContentValues();
            values.put(MoviesEntry.COLUMN_MOVIES_NAME, key);
            values.put(MoviesEntry.COLUMN_MOVIES_GENDER, dummyMovieMap.get(key));
            getContentResolver().insert(MoviesEntry.CONTENT_URI, values);
        }
    }

    private void deleteAllMovies() {
        int rowsDeleted = getContentResolver().delete(MoviesEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from movies database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertMovies();
                return true;
            case R.id.action_delete_all_entries:
                deleteAllMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MoviesEntry._ID,
                MoviesEntry.COLUMN_MOVIES_NAME };

        return new CursorLoader(this,   // Parent activity context
                MoviesEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
