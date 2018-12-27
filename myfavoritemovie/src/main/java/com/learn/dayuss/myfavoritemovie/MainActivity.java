package com.learn.dayuss.myfavoritemovie;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.learn.dayuss.myfavoritemovie.Adapter.FavoriteAdapter;

import static com.learn.dayuss.myfavoritemovie.Database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Cursor list;
    private FavoriteAdapter adapter;
    private RecyclerView rvFavMovie;
    private TextView tvNotfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFavMovie = findViewById(R.id.rvFavMovie);
        progressBar = findViewById(R.id.progress_bar_fav);
        tvNotfound = findViewById(R.id.tvNotfound);

        rvFavMovie.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        adapter = new FavoriteAdapter(this);
        adapter.setListfav(list);
        rvFavMovie.setAdapter(adapter);

        new LoadMovie().execute();

    }

    private class LoadMovie extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor fav) {
            super.onPostExecute(fav);
            progressBar.setVisibility(View.GONE);

            list = fav;
            adapter.setListfav(list);
            adapter.notifyDataSetChanged();
            rvFavMovie.setAdapter(adapter);

            if (list.getCount() == 0) {
                tvNotfound.setVisibility(View.VISIBLE);
                tvNotfound.setText(R.string.notfound);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
