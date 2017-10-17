package com.example.android.movieslibrary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;
import com.example.android.movieslibrary.model.Movie;

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
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView genreTextView = (TextView) view.findViewById(R.id.genre);
        ImageView detailsImageView = (ImageView) view.findViewById(R.id.movie_info_icon);

        int nameColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_NAME);
        int genreColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_GENDER);
        int summaryColumnIndex = cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIES_SUMMARY);

        final String MoviesName = cursor.getString(nameColumnIndex);
        int genreIndex = cursor.getInt(genreColumnIndex);
        final String movieSummary = cursor.getString(summaryColumnIndex);

        final String genreName = context.getResources().getStringArray(R.array.array_gender_options)[genreIndex];
        nameTextView.setText(MoviesName);
        genreTextView.setText(genreName);

        detailsImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(context, MovieDetailActivity.class);
                infoIntent.putExtra(MOVIE_NAME, MoviesName);
                infoIntent.putExtra(MOVIE_SUMMARY, movieSummary);
                context.startActivity(infoIntent);
            }
        });
    }
}
