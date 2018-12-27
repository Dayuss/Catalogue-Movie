package com.learn.dayuss.cataloguemovie.Rest;


import com.learn.dayuss.cataloguemovie.Model.Detail.MovieDetail;
import com.learn.dayuss.cataloguemovie.Model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dayuss on 15/11/18.
 */

public interface MovieService {

    @GET("/3/movie/now_playing")
    Call<MovieResult> getMovie(@Query("api_key") String apiKey);

    @GET("/3/search/movie")
    Call<MovieResult> searchMovie(@Query("api_key") String apiKey, @Query("query") String value);

    @GET("/3/movie/{movie_id}")
    Call<MovieDetail> getDetail(@Path("movie_id") int value, @Query("api_key") String apiKey);

    @GET("/3/movie/popular")
    Call<MovieResult> getPopular(@Query("api_key") String apiKey);

    @GET("/3/movie/upcoming")
    Call<MovieResult> getUpcoming(@Query("api_key") String apiKey);

}
