package com.example.android.movieslibrary.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

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

        public final static String COLUMN_MOVIES_NAME ="name";

        public final static String COLUMN_MOVIES_GENDER = "gender";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_ACTION = 1;
        public static final int GENDER_ROMANCE = 2;
        public static final int GENDER_ANIMATION = 3;
        public static final int GENDER_SCIFI = 4;



        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_ACTION || gender == GENDER_ROMANCE || gender == GENDER_ANIMATION || gender == GENDER_SCIFI) {
                return true;
            }
            return false;
        }
    }

}

