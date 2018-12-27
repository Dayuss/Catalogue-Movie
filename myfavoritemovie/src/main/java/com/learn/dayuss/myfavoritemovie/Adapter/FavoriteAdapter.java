package com.learn.dayuss.myfavoritemovie.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.dayuss.myfavoritemovie.DetailActivity;
import com.learn.dayuss.myfavoritemovie.Model.MovieFavorite;
import com.learn.dayuss.myfavoritemovie.R;
import com.squareup.picasso.Picasso;

/**
 * Created by dayuss on 20/12/18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{
    private Activity activity;
    private String BASE_IMAGE = "http://image.tmdb.org/t/p/w500/";
    private Cursor listfav;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListfav(Cursor listfav) {
        this.listfav = listfav;
    }
    public Cursor getListFav() {
        return listfav;
    }



    @Override
    public FavoriteViewHolder onCreateViewHolder(final ViewGroup parent,
                                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        final FavoriteViewHolder pvh = new FavoriteViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MovieFavorite fav = getItem(pvh.getAdapterPosition());
                Intent moveWithDataIntent = new Intent(parent.getContext(), DetailActivity.class);
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_ID, fav.getMOVIE_ID());
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_TITLE, fav.getTITLE());
                parent.getContext().startActivity(moveWithDataIntent);
            }
        });
        return pvh;
    }



    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView lang;
        TextView rating;
        ImageView poster;

        public FavoriteViewHolder(View v) {
            super(v);
            movieTitle =  v.findViewById(R.id.title);
            poster =  v.findViewById(R.id.poster);
            rating = v.findViewById(R.id.rating_count);
            lang    = v.findViewById(R.id.lang);
        }
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, final int position) {
        final MovieFavorite favItem = getItem(position);

        holder.movieTitle.setText(favItem.getTITLE());
        holder.rating.setText(Double.toString(favItem.getRATING()));
        holder.lang.setText(favItem.getCOUNTRY());
        Picasso.get()
                .load(BASE_IMAGE+favItem.getDETAIL_POSTER())
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        if (listfav == null) return 0;
        return listfav.getCount();
    }

    private MovieFavorite getItem(int position){
        if (!listfav.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieFavorite(listfav);
    }
}