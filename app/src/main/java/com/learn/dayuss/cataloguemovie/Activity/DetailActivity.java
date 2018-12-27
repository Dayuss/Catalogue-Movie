package com.learn.dayuss.cataloguemovie.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.dayuss.cataloguemovie.BuildConfig;
import com.learn.dayuss.cataloguemovie.Database.FavoriteHelper;
import com.learn.dayuss.cataloguemovie.Model.Detail.Genre;
import com.learn.dayuss.cataloguemovie.Model.Detail.MovieDetail;
import com.learn.dayuss.cataloguemovie.Model.Detail.ProductionCompany;
import com.learn.dayuss.cataloguemovie.Model.MovieFavorite;
import com.learn.dayuss.cataloguemovie.R;
import com.learn.dayuss.cataloguemovie.Rest.MovieService;
import com.learn.dayuss.cataloguemovie.Rest.RestClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.COUNTRY;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.DETAIL_POSTER;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.MOVIE_ID;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.RATING;
import static com.learn.dayuss.cataloguemovie.Database.DatabaseContract.FavoritColumn.TITLE;

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
    private FloatingActionButton favBtn;

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
        favBtn = findViewById(R.id.favbtn);

        id = getIntent().getIntExtra(EXTRA_ID, 0);

        setDetail();

        final Uri uri = Uri.parse(CONTENT_URI+"/"+id);

        // cek jika movie ini favorit
        Cursor checkFav = getContentResolver().query(uri, null, null, null, null);

        if (checkFav != null){
            if(checkFav.moveToFirst()) {
                favBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
                isFav=true;
            }else{
                favBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                isFav=false;
            }
        }



        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();

                if(isFav){
                    getContentResolver().delete(uri,null,null);
                    favBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                    isFav=false;
                }else{
                    values.put(MOVIE_ID,id);
                    values.put(TITLE,tvTitle.getText().toString());
                    values.put(DETAIL_POSTER,img);
                    values.put(RATING, rate);
                    values.put(COUNTRY, country);

                    getContentResolver().insert(CONTENT_URI,values);

                    favBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
                    isFav=true;
                }
            }
        });

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
