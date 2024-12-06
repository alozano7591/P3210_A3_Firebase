package com.mobile2.p3210_a3.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile2.p3210_a3.ItemClickListener;
import com.mobile2.p3210_a3.R;
import com.mobile2.p3210_a3.model.FavMovieModel;
import com.mobile2.p3210_a3.model.MovieModel;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {

    Context context;
    List<FavMovieModel> favMovieModels;
    ItemClickListener clickListener;

    public FavouriteAdapter(Context context, List<FavMovieModel> favMovieModels){
        this.context = context;
        this.favMovieModels = favMovieModels;
    }

    public void setClickListener(ItemClickListener listener){
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View favouriteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_layout, parent, false);

        return new FavouriteViewHolder(favouriteView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        FavMovieModel favModel = favMovieModels.get(position);

        Glide.with(holder.posterImageView.getContext())
                .load(favModel.getPosterUrl())
                .placeholder(R.drawable.theatre447)         // no reason to drop the default image :)
                .into(holder.posterImageView);

        holder.title.setText(favModel.getTitle());
        holder.year.setText(favModel.getYear());

        Log.i("tag", "Favourite Adapter bind update");
    }

    @Override
    public int getItemCount(){
        return favMovieModels.size();
    }

    public List<FavMovieModel> getFavourites(){
        return favMovieModels;
    }

    public void updateFavourites(List<FavMovieModel> favModels){
        this.favMovieModels.clear();
        this.favMovieModels.addAll(favModels);
        notifyDataSetChanged();

        Log.i("tag", "Adapter updateMovies finished. There are " + favMovieModels.size() + " movies");
    }
}
