package com.learn.dayuss.myfavoritemovie.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dayuss on 20/12/18.
 */

public class DatabaseContract {

    public static String TABLE_FAVORITE = "favorite";
    public static final class FavoritColumn implements BaseColumns {
        public static String TITLE ="title";
        public static String DETAIL_POSTER = "detailPoster";
        public static String RATING="rating";
        public static String MOVIE_ID="MovieId";
        public static String COUNTRY="country";

    }

    public static final String AUTHORITY = "com.learn.dayuss.cataloguemovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble( cursor.getColumnIndex(columnName) );
    }
}