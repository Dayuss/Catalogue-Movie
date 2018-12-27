package com.learn.dayuss.cataloguemovie.Adapter;

/**
 * Created by Nursing Bank IT Dept on 3/13/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.dayuss.cataloguemovie.Activity.DetailActivity;
import com.learn.dayuss.cataloguemovie.Model.MovieData;
import com.learn.dayuss.cataloguemovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieData> movies;
    private int rowLayout;
    private Context context;
    private String BASE_IMAGE = "http://image.tmdb.org/t/p/w500/";

    public MovieAdapter(List<MovieData> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        final MovieViewHolder pvh = new MovieViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveWithDataIntent = new Intent(context, DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_ID, movies.get(pvh.getAdapterPosition()).getId());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_TITLE, movies.get(pvh.getAdapterPosition()).getTitle());
                context.startActivity(moveWithDataIntent);
            }
        });
        return pvh;
    }



    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView lang;
        TextView rating;
        ImageView poster;

        public MovieViewHolder(View v) {
            super(v);
            movieTitle =  v.findViewById(R.id.title);
            poster =  v.findViewById(R.id.poster);
            rating = v.findViewById(R.id.rating_count);
            lang    = v.findViewById(R.id.lang);
        }
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.rating.setText(Double.toString(movies.get(position).getVoteAverage()));
        holder.lang.setText(movies.get(position).getOriginalLanguage());
        Picasso.get()
                .load(BASE_IMAGE+movies.get(position).getPosterPath())
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}