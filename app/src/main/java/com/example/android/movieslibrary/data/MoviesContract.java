package com.example.android.movieslibrary.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.List;

public final class MoviesContract {

    private MoviesContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.movieslibrary";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;


        public final static String TABLE_NAME = "movies";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_MOVIES_NAME = "name";

        public final static String COLUMN_MOVIES_GENRE = "gender";

        public final static String COLUMN_MOVIES_RATING = "rating";

        public final static String COLUMN_MOVIES_SUMMARY = "summary";

        public static final int GENRE_UNKNOWN = 0;
        public static final int GENRE_ACTION = 1;
        public static final int GENRE_ADVENTURE = 2;
        public static final int GENRE_ANIMATION = 3;
        public static final int GENRE_COMEDY = 4;
        public static final int GENRE_CRIME = 5;
        public static final int GENRE_DRAMA = 6;
        public static final int GENRE_FANTASY = 7;
        public static final int GENRE_HISTORICAL = 8;
        public static final int GENRE_HISTORICAL_FICTION = 9;
        public static final int GENRE_HORROR = 10;
        public static final int GENRE_MAGICAL_REALISM = 11;
        public static final int GENRE_MYSTERY = 12;
        public static final int GENRE_PARANOID = 13;
        public static final int GENRE_PHILOSOPHICAL = 14;
        public static final int GENRE_POLITICAL = 15;
        public static final int GENRE_ROMANCE = 16;
        public static final int GENRE_SAGA = 17;
        public static final int GENRE_SATIRE = 18;
        public static final int GENRE_SCIENCE_FICTION = 19;
        public static final int GENRE_SLICE_OF_LIFE = 21;
        public static final int GENRE_SPECULATIVE = 22;
        public static final int GENRE_SURREAL = 23;
        public static final int GENRE_THRILLER = 24;
        public static final int GENRE_URBAN = 25;
        public static final int GENRE_WESTERN = 26;

        public static List<Integer> GenderList() {
            return Arrays.asList(
                    GENRE_UNKNOWN,
                    GENRE_ACTION,
                    GENRE_ADVENTURE,
                    GENRE_ANIMATION,
                    GENRE_COMEDY,
                    GENRE_CRIME,
                    GENRE_DRAMA,
                    GENRE_FANTASY,
                    GENRE_HISTORICAL,
                    GENRE_HISTORICAL_FICTION,
                    GENRE_HORROR,
                    GENRE_MAGICAL_REALISM,
                    GENRE_MYSTERY,
                    GENRE_PARANOID,
                    GENRE_PHILOSOPHICAL,
                    GENRE_POLITICAL,
                    GENRE_ROMANCE,
                    GENRE_SAGA,
                    GENRE_SATIRE,
                    GENRE_SCIENCE_FICTION,
                    GENRE_SLICE_OF_LIFE,
                    GENRE_SPECULATIVE,
                    GENRE_SURREAL,
                    GENRE_THRILLER,
                    GENRE_URBAN,
                    GENRE_WESTERN);
        }

        public static final int RATING_UNKNOWN = 0;
        public static final int RATING_VERY_BAD = 1;
        public static final int RATING_BAD = 2;
        public static final int RATING_GOOD = 3;
        public static final int RATING_VERY_GOOD = 4;
        public static final int RATING_GREAT = 5;

        public static boolean isValidGender(int gender) {
            return gender >= 0 && gender <= GenderList().size();
        }

        public static boolean isValigRating(int rating) {
            return rating >= RATING_UNKNOWN && rating <= RATING_GREAT;
        }
    }

}

