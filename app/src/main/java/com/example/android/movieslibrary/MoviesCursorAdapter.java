package com.example.android.movieslibrary;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;

public class MoviesCursorAdapter extends CursorAdapter {

    public MoviesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView genreTextView = (TextView) view.findViewById(R.id.genre);


        int nameColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_NAME);
        int genreColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_GENDER);

        String MoviesName = cursor.getString(nameColumnIndex);
        int genreIndex = cursor.getInt(genreColumnIndex);

        String genreName = context.getResources().getStringArray(R.array.array_gender_options)[genreIndex];
        nameTextView.setText(MoviesName);
        genreTextView.setText(genreName);
    }
}
