package com.example.android.movieslibrary.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.movieslibrary.R;
import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;

public class MoviesCursorAdapter extends CursorAdapter {

    public static final String MOVIE_SUMMARY = "Summary";
    public static final String MOVIE_NAME = "movieName";
    public static final String MOVIE_GENRE = "movieGenre";

    public MoviesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView genreTextView = view.findViewById(R.id.genre);

        int nameColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_NAME);
        int genreColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_GENRE);
        int summaryColumnIndex = cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIES_SUMMARY);

        final String moviesName = cursor.getString(nameColumnIndex);
        int genreIndex = cursor.getInt(genreColumnIndex);

        final String genreName = context.getResources().getStringArray(R.array.array_genre_options)[genreIndex];
        nameTextView.setText(moviesName);
        genreTextView.setText(genreName);
    }
}
