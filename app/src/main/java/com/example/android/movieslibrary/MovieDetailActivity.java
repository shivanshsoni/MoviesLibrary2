package com.example.android.movieslibrary;

import static com.example.android.movieslibrary.MoviesCursorAdapter.MOVIE_NAME;
import static com.example.android.movieslibrary.MoviesCursorAdapter.MOVIE_SUMMARY;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by dimitris on 15/10/2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

  private TextView mMovieTitle, mSummaryMovie;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);

    mMovieTitle = (TextView) findViewById(R.id.movieTitle);
    mSummaryMovie = (TextView) findViewById(R.id.summaryMovie);
    Intent intent = getIntent();

    String name = intent.getStringExtra(MOVIE_NAME);
    String summary = intent.getStringExtra(MOVIE_SUMMARY);
    mMovieTitle.setText(name);
    mSummaryMovie.setText(summary);

  }

}
