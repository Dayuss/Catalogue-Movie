package com.learn.dayuss.myfavoritemovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.learn.dayuss.myfavoritemovie.Model.Genre;
import com.learn.dayuss.myfavoritemovie.Model.MovieDetail;
import com.learn.dayuss.myfavoritemovie.Model.ProductionCompany;
import com.learn.dayuss.myfavoritemovie.Rest.MovieService;
import com.learn.dayuss.myfavoritemovie.Rest.RestClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {
    public static String EXTRA_ID = "extra_id";
    public static String EXTRA_TITLE = "extra_title";
    private String BASE_IMAGE = "http://image.tmdb.org/t/p/w500/";

    private ImageView cover;
    private ImageView detailPoster;
    private TextView releaseDate ;
    private TextView Revenue;
    private RatingBar ratingBar;
    private TextView gendre;
    private TextView overview;
    private ImageView logoCompanies;
    private TextView companyName;
    private TextView originCountry;
    private TextView tvTitle;
    private int id;
    private MovieDetail movies;
    private String img;
    private Double rate;
    private String country;
    private Boolean isFav=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cover = findViewById(R.id.main_backdrop);
        tvTitle = findViewById(R.id.tvTitle);
        detailPoster = findViewById(R.id.detailPoster);
        releaseDate = findViewById(R.id.releaseDate);
        Revenue= findViewById(R.id.Revenue);
        ratingBar= findViewById(R.id.ratingBar);
        gendre = findViewById(R.id.gendre);
        overview= findViewById(R.id.overview);
        logoCompanies= findViewById(R.id.logoCompanies);
        originCountry= findViewById(R.id.originCountry);
        companyName= findViewById(R.id.companyName);

        id = getIntent().getIntExtra(EXTRA_ID, 0);

        setDetail();

    }


    public void setDetail(){
        MovieService apiService = RestClient.getClient().create(MovieService.class);

        Call<MovieDetail> call = apiService.getDetail(id, BuildConfig.API_KEY);

        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail>call, Response<MovieDetail> response) {
                movies = response.body();
                tvTitle.setText(movies.getTitle());
                Picasso.get()
                        .load(BASE_IMAGE+movies.getBackdropPath())
                        .into(cover);
                Picasso.get()
                        .load(BASE_IMAGE+movies.getPosterPath())
                        .into(detailPoster);
                releaseDate.setText(movies.getReleaseDate());
                Revenue.setText(String.valueOf(movies.getRevenue()));

                img = movies.getPosterPath();
                rate = movies.getVoteAverage();
                float rating = (float) (movies.getVoteAverage()/2);
                ratingBar.setRating(rating);

                overview.setText(movies.getOverview());

                List<Genre> gendres = movies.getGenres();
                String concatGendre = "";
                for(int i=0;i<gendres.size();i++){
                    concatGendre+=String.valueOf(gendres.get(i).getName()) + " | ";
                }
                gendre.setText(concatGendre);

                List<ProductionCompany> pc = movies.getProductionCompanies();
                if(pc.size() > 0 ){
                    Picasso.get()
                            .load(BASE_IMAGE+pc.get(0).getLogoPath())
                            .into(logoCompanies);
                    companyName.setText(pc.get(0).getName());
                    originCountry.setText(pc.get(0).getOriginCountry());
                    country = pc.get(0).getOriginCountry();

                }

            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.e("MOVIE_CATALOGUE_FAILURE", t.toString());

            }


        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
