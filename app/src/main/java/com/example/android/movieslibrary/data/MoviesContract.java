package com.example.android.movieslibrary.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.List;

public final class MoviesContract {

    private MoviesContract() {}

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

        public final static String COLUMN_MOVIES_GENDER = "gender";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_ACTION = 1;
        public static final int GENDER_ADVENTURE = 2;
        public static final int GENDER_ANIMATION = 3;
        public static final int GENDER_COMEDY = 4;
        public static final int GENDER_CRIME = 5;
        public static final int GENDER_DRAMA = 6;
        public static final int GENDER_FANTASY = 7;
        public static final int GENDER_HISTORICAL = 8;
        public static final int GENDER_HISTORICAL_FICTION = 9;
        public static final int GENDER_HORROR = 10;
        public static final int GENDER_MAGICAL_REALISM = 11;
        public static final int GENDER_MYSTERY = 12;
        public static final int GENDER_PARANOID = 13;
        public static final int GENDER_PHILOSOPHICAL = 14;
        public static final int GENDER_POLITICAL = 15;
        public static final int GENDER_ROMANCE = 16;
        public static final int GENDER_SAGA = 17;
        public static final int GENDER_SATIRE = 18;
        public static final int GENDER_SCIENCE_FICTION = 19;
        public static final int GENDER_SLICE_OF_LIFE = 21;
        public static final int GENDER_SPECULATIVE = 22;
        public static final int GENDER_SURREAL = 23;
        public static final int GENDER_THRILLER = 124;
        public static final int GENDER_URBAN = 25;
        public static final int GENDER_WESTERN = 26;

        public static List<Integer> GenderList() {
            return Arrays.asList(
                    GENDER_UNKNOWN,
                    GENDER_ACTION,
                    GENDER_ADVENTURE,
                    GENDER_ANIMATION,
                    GENDER_COMEDY,
                    GENDER_CRIME,
                    GENDER_DRAMA,
                    GENDER_FANTASY,
                    GENDER_HISTORICAL,
                    GENDER_HISTORICAL_FICTION,
                    GENDER_HORROR,
                    GENDER_MAGICAL_REALISM,
                    GENDER_MYSTERY,
                    GENDER_PARANOID,
                    GENDER_PHILOSOPHICAL,
                    GENDER_POLITICAL,
                    GENDER_ROMANCE,
                    GENDER_SAGA,
                    GENDER_SATIRE,
                    GENDER_SCIENCE_FICTION,
                    GENDER_SLICE_OF_LIFE,
                    GENDER_SPECULATIVE,
                    GENDER_SURREAL,
                    GENDER_THRILLER,
                    GENDER_URBAN,
                    GENDER_WESTERN);
        }

        public static boolean isValidGender(int gender) {
            return gender >= 0 && gender <= GenderList().size();
        }
    }

}

