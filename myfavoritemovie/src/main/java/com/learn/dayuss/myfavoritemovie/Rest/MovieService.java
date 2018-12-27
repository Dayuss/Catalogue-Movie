package com.learn.dayuss.myfavoritemovie.Rest;

import com.learn.dayuss.myfavoritemovie.Model.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dayuss on 15/11/18.
 */

public interface MovieService {

    @GET("/3/movie/{movie_id}")
    Call<MovieDetail> getDetail(@Path("movie_id") int value, @Query("api_key") String apiKey);


}
