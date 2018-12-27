package com.learn.dayuss.cataloguemovie.Model;

import android.database.Cursor;

import com.learn.dayuss.cataloguemovie.Database.DatabaseContract;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.getColumnDouble;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.getColumnInt;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.getColumnString;

/**
 * Created by dayuss on 20/12/18.
 */

public class MovieFavorite {
    private String TITLE;
    private String DETAIL_POSTER;
    private Double RATING;
    private Integer ID;
    private Integer MOVIE_ID;
    private String COUNTRY;

    public MovieFavorite(){

    }
    public MovieFavorite(Cursor cursor){
        this.ID = getColumnInt(cursor, _ID);
        this.TITLE = getColumnString(cursor, DatabaseContract.FavoritColumn.TITLE);
        this.DETAIL_POSTER = getColumnString(cursor, DatabaseContract.FavoritColumn.DETAIL_POSTER);
        this.RATING = getColumnDouble(cursor, DatabaseContract.FavoritColumn.RATING);
        this.MOVIE_ID = getColumnInt(cursor, DatabaseContract.FavoritColumn.MOVIE_ID);
        this.COUNTRY = getColumnString(cursor, DatabaseContract.FavoritColumn.COUNTRY);
    }
    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getDETAIL_POSTER() {
        return DETAIL_POSTER;
    }

    public void setDETAIL_POSTER(String DETAIL_POSTER) {
        this.DETAIL_POSTER = DETAIL_POSTER;
    }

    public Double getRATING() {
        return RATING;
    }

    public void setRATING(Double RATING) {
        this.RATING = RATING;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getMOVIE_ID() {
        return MOVIE_ID;
    }

    public void setMOVIE_ID(Integer MOVIE_ID) {
        this.MOVIE_ID = MOVIE_ID;
    }
}
