package com.learn.dayuss.cataloguemovie.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.learn.dayuss.cataloguemovie.Database.DatabaseContract;
import com.learn.dayuss.cataloguemovie.Database.FavoriteHelper;

import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.TABLE_FAVORITE;

/**
 * Created by dayuss on 22/12/18.
 */

public class FavoriteProvider extends ContentProvider {

    private static final int FAV = 101;
    private static final int FAV_ID = 102;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_FAVORITE, FAV);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_FAVORITE + "/#", FAV_ID);
    }
    private FavoriteHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Log.d("GET", String.valueOf(sUriMatcher.match(uri)));

        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case FAV:
                cursor = favoriteHelper.queryProvider();
                break;
            case FAV_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added ;

        switch (sUriMatcher.match(uri)){
            case FAV:
                added = favoriteHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                updated =  favoriteHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                deleted =  favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        Log.d("DELETE", String.valueOf(favoriteHelper.deleteProvider(uri.getLastPathSegment())));

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

}
