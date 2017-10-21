package com.example.android.movieslibrary.activities;

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

import com.example.android.movieslibrary.R;
import com.example.android.movieslibrary.adapters.MoviesCursorAdapter;
import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;
import com.example.android.movieslibrary.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIES_LOADER = 0;
    public static final String KEY_ADD_NEW = "add_new";

    MoviesCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class)
                        .putExtra(KEY_ADD_NEW, true);
                startActivity(intent);
            }
        });

        ListView moviesListView = findViewById(R.id.list);

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

    public List<Movie> getDummyMoviesMap() {
        List<Movie> dummyMoviesMap = new ArrayList<>();

        // Realistic ratings, arranged in increasing orderd
        dummyMoviesMap.add(new Movie("Star Wars - The Last Jedi", MoviesEntry.GENRE_SCIENCE_FICTION, MoviesEntry.RATING_UNKNOWN));
        dummyMoviesMap.add(new Movie("The Notebook", MoviesEntry.GENRE_ROMANCE, MoviesEntry.RATING_VERY_BAD));
        dummyMoviesMap.add(new Movie("Jaws", MoviesEntry.GENRE_ACTION, MoviesEntry.RATING_BAD));
        dummyMoviesMap.add(new Movie("Toy Story", MoviesEntry.GENRE_ANIMATION, MoviesEntry.RATING_GOOD));
        dummyMoviesMap.add(new Movie("The Matrix", MoviesEntry.GENRE_SCIENCE_FICTION, MoviesEntry.RATING_VERY_GOOD));
        dummyMoviesMap.add(new Movie("Interstellar", MoviesEntry.GENRE_ACTION, MoviesEntry.RATING_GREAT));

        return dummyMoviesMap;
    }

    private void insertMovies() {
        List<Movie> dummyMoviesMap = getDummyMoviesMap();
        ContentValues values = new ContentValues();
        values.put(MoviesEntry.COLUMN_MOVIES_NAME, "Interstellar");
        values.put(MoviesEntry.COLUMN_MOVIES_GENRE, MoviesEntry.GENRE_ACTION);
        values.put(MoviesEntry.COLUMN_MOVIES_SUMMARY, "This is a great movie about space travel");

        for (Movie key : dummyMoviesMap) {

            values.put(MoviesEntry.COLUMN_MOVIES_NAME, key.getName());
            values.put(MoviesEntry.COLUMN_MOVIES_GENRE, key.getGenre());
            values.put(MoviesEntry.COLUMN_MOVIES_RATING, key.getRating());

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
                MoviesEntry.COLUMN_MOVIES_NAME,
                MoviesEntry.COLUMN_MOVIES_GENRE,
                MoviesEntry.COLUMN_MOVIES_SUMMARY};

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
