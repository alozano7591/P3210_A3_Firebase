/*
Alfredo Lozano
Assignment 2
 */
// Note for myself:  alt + insert to easily add constructor, getters, setters, etc

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
import com.mobile2.p3210_a3.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<MovieModel> movieModels;
    ItemClickListener clickListener;

    public MyAdapter(Context context, List<MovieModel> movieModels) {
        this.context = context;
        this.movieModels = new ArrayList<>(movieModels);
    }

    public void setClickListener(ItemClickListener myListener){this.clickListener = myListener;}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_layout, parent, false);

        return new MyViewHolder(movieView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel movieModel = movieModels.get(position);

        //This gets our image from the url and assigns to the imageView
        Glide.with(holder.poserImageView.getContext())
            .load(movieModel.getPosterUrl())
            .placeholder(R.drawable.theatre447)         // I did not want empty image spots so I added a default image
            .into(holder.poserImageView);

        holder.title.setText(movieModel.getTitle());
        holder.year.setText(movieModel.getYear());

        Log.i("tag", "Adapter bindupdate");
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public List<MovieModel> getMovies(){
        return movieModels;
    }

    public void updateMovies(List<MovieModel> movieModels){
        //I have these logs because I was suffering a lot from weird errors with the list
        this.movieModels.clear();
        this.movieModels.addAll(movieModels);
        notifyDataSetChanged();

        Log.i("tag", "Adapter updateMovies finished. There are " + movieModels.size() + " movies");
    }

}
