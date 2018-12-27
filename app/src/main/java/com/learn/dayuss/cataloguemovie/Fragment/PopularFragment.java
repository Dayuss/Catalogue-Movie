package com.learn.dayuss.cataloguemovie.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

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

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PopularFragment extends Fragment {
    private String title;
    private static final String ARG_TITLE = "title";
    private RecyclerView recyclerView;
    private Bundle savedState = null;
    private ProgressBar progressBar;

    public PopularFragment() {
        // Required empty public constructor

    }

    public Context getcontext(){
        return this.getContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            title = extras.getString(ARG_TITLE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        recyclerView = rootView.findViewById(R.id.rv_movieP);
        progressBar = rootView.findViewById(R.id.progress_bar);

        loadMovieData();

        return rootView;

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public void loadMovieData(){
        MovieService apiService = RestClient.getClient().create(MovieService.class);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Call<MovieResult> call = null;
        call = apiService.getPopular(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult>call, Response<MovieResult> response) {
                List<MovieData> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.custom_row, getContext()));
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e("MOVIE_CATALOGUE_FAILURE", t.toString());

            }


        });
    }
}