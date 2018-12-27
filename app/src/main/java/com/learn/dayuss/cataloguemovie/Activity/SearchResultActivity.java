package com.learn.dayuss.cataloguemovie.Activity;

import android.graphics.Movie;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.learn.dayuss.cataloguemovie.Adapter.MovieAdapter;
import com.learn.dayuss.cataloguemovie.BuildConfig;
import com.learn.dayuss.cataloguemovie.Model.MovieData;
import com.learn.dayuss.cataloguemovie.Model.MovieResult;
import com.learn.dayuss.cataloguemovie.R;
import com.learn.dayuss.cataloguemovie.Rest.MovieService;
import com.learn.dayuss.cataloguemovie.Rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchResultActivity extends AppCompatActivity {
    public static String EXTRA_QUERY = "extra_query";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    public final static String LIST_STATE_KEY = "recycler_list_state";
    Parcelable listState;
    private  String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        query = getIntent().getStringExtra(EXTRA_QUERY);
        setTitle("Result for: "+ query);
        recyclerView = findViewById(R.id.rv_movie_result);
        progressBar = findViewById(R.id.progress_bar);

        loadMovieData(query);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        // Save list state
        listState = recyclerView.getLayoutManager().onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
        super.onSaveInstanceState(state);
    }
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null)
            listState = state.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (listState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }


    public void loadMovieData(String query){
        MovieService apiService = RestClient.getClient().create(MovieService.class);
        progressBar.setVisibility(VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));

        Call<MovieResult> call = null;
        call = apiService.searchMovie(BuildConfig.API_KEY,query);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult>call, Response<MovieResult> response) {
                List<MovieData> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.custom_row, SearchResultActivity.this));
                progressBar.setVisibility(GONE);

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e("MOVIE_CATALOGUE_FAILURE", t.toString());

            }


        });
    }
}
