package com.example.android.movieslibrary.activities;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.movieslibrary.R;
import com.example.android.movieslibrary.data.MoviesContract.MoviesEntry;

import static com.example.android.movieslibrary.activities.CatalogActivity.KEY_ADD_NEW;

public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_MOVIES_LOADER = 0;

    private Uri mCurrentMoviesUri;

    private TextInputLayout mNameEditText;

    private RatingBar rtbRating;

    private TextInputLayout mMovieSummary;

    private Spinner mGenreSpinner;

    private boolean editable = false;


    private int mGender = MoviesEntry.GENRE_ACTION;
    private int mRating = MoviesEntry.RATING_UNKNOWN;

    private boolean mMoviesHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mMoviesHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentMoviesUri = intent.getData();

        if (mCurrentMoviesUri == null) {
            setTitle(getString(R.string.editor_activity_title_new_movies));

            invalidateOptionsMenu();
        } else {


            getLoaderManager().initLoader(EXISTING_MOVIES_LOADER, null, this);
        }
        mNameEditText = findViewById(R.id.edit_movies_name);

        rtbRating = findViewById(R.id.rtbRating);

        mMovieSummary = findViewById(R.id.edit_movies_summary);

        mGenreSpinner = findViewById(R.id.spinner_genre);

        if (intent.getBooleanExtra(KEY_ADD_NEW, false)) {
            editable = true;
            changeEditableState();
        }

        mNameEditText.setOnTouchListener(mTouchListener);

        mGenreSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_genre_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenreSpinner.setAdapter(genderSpinnerAdapter);

        mGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    mGender = position;
                } else {
                    mGender = MoviesEntry.GENRE_UNKNOWN;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = MoviesEntry.GENRE_UNKNOWN;
            }
        });

        rtbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRating = Math.round(rating);
            }
        });
    }

    private void saveMovies() {
        String nameString = mNameEditText.getEditText().getText().toString().trim();
        String summaryString = mMovieSummary.getEditText().getText().toString().trim();


        if (mCurrentMoviesUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(summaryString) && mGender == MoviesEntry.GENRE_UNKNOWN) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(MoviesEntry.COLUMN_MOVIES_NAME, nameString);
        values.put(MoviesEntry.COLUMN_MOVIES_GENRE, mGender);
        values.put(MoviesEntry.COLUMN_MOVIES_RATING, mRating);
        values.put(MoviesEntry.COLUMN_MOVIES_SUMMARY, summaryString);

        if (mCurrentMoviesUri == null) {
            Uri newUri = getContentResolver().insert(MoviesEntry.CONTENT_URI, values);


        } else {
            int rowsAffected = getContentResolver().update(mCurrentMoviesUri, values, null, null);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        changeEditableState();
        MenuItem editMenuItem = menu.findItem(R.id.action_edit);
        MenuItem deleteMenuItem = menu.findItem(R.id.action_delete);
        MenuItem saveMenuItem = menu.findItem(R.id.action_save);
        editMenuItem.setVisible(!editable);
        deleteMenuItem.setVisible(editable);
        saveMenuItem.setVisible(editable);
        if (mCurrentMoviesUri == null) {
            deleteMenuItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveMovies();
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_edit:
                editable = !editable;
                changeEditableState();
                return true;
            case android.R.id.home:
                if (!mMoviesHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeEditableState() {
        mNameEditText.getEditText().setEnabled(editable);
        mMovieSummary.getEditText().setEnabled(editable);
        rtbRating.setIsIndicator(!editable);
        mGenreSpinner.setEnabled(editable);
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (!mMoviesHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MoviesEntry._ID,
                MoviesEntry.COLUMN_MOVIES_NAME,
                MoviesEntry.COLUMN_MOVIES_GENRE,
                MoviesEntry.COLUMN_MOVIES_RATING,
                MoviesEntry.COLUMN_MOVIES_SUMMARY};

        return new CursorLoader(this,
                mCurrentMoviesUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_NAME);

            int genderColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_GENRE);

            int ratingColumIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_RATING);

            int summaryColumnIndex = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIES_SUMMARY);

            String name = cursor.getString(nameColumnIndex);

            int gender = cursor.getInt(genderColumnIndex);

            int rating = cursor.getInt(ratingColumIndex);

            String summary = cursor.getString(summaryColumnIndex);

            rtbRating.setRating(rating);
            mNameEditText.getEditText().setText(name);
            mMovieSummary.getEditText().setText(summary);

            mNameEditText.getEditText().setText(name);

            mGenreSpinner.setSelection(gender);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.getEditText().setText("");
        mMovieSummary.getEditText().setText("");
        mGenreSpinner.setSelection(0); // Select "Unknown" gender
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteMovies();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteMovies() {
        if (mCurrentMoviesUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentMoviesUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_movies_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_movies_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}