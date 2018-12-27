package com.learn.dayuss.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.learn.dayuss.cataloguemovie.Model.MovieFavorite;

import java.util.ArrayList;

import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.COUNTRY;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.DETAIL_POSTER;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.MOVIE_ID;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.RATING;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.TITLE;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn._ID;

/**
 * Created by dayuss on 20/12/18.
 */

public class FavoriteHelper {
    private static String DATABASE_TABLE = DatabaseContract.TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private static SQLiteDatabase database;
    private static SQLiteDatabase database2;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,MOVIE_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,MOVIE_ID + " = ?", new String[]{id});
    }

}
