package com.mobile2.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile2.p3210_a3.ItemClickListener;
import com.mobile2.p3210_a3.R;
import com.mobile2.p3210_a3.databinding.FragmentFavouriteMoviesBinding;
import com.mobile2.p3210_a3.model.FavMovieModel;
import com.mobile2.p3210_a3.viewmodel.FavouriteAdapter;
import com.mobile2.p3210_a3.viewmodel.FavouriteViewModel;

import java.util.ArrayList;


public class FavouriteMovies extends Fragment implements ItemClickListener {

    FragmentFavouriteMoviesBinding binding;
    FavouriteViewModel viewModel;
    FavouriteAdapter favAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        favAdapter = new FavouriteAdapter(this.getContext(), new ArrayList<>());
        favAdapter.setClickListener(this);

        binding.recyclerViewFavouritesList.setLayoutManager(layoutManager);
        binding.recyclerViewFavouritesList.setAdapter(favAdapter);

        viewModel.getFavouritesFromDb();

        viewModel.getFavList().observe(getViewLifecycleOwner(), favMovieModels -> {

            favAdapter.updateFavourites(favMovieModels);

        });

        return view;
    }

    @Override
    public void onClick(View v, int pos){
        goToFavouriteDetails(favAdapter.getFavourites().get(pos));
    }

    public void goToFavouriteDetails(FavMovieModel favModel){
                                            // UNSURE ABOUT THIS \/
        Intent goToFavouriteDetailsIntent = new Intent(this.getContext(), FavouriteDetailsActivity.class);
        goToFavouriteDetailsIntent.putExtra("id", favModel.getImdbID());
        goToFavouriteDetailsIntent.putExtra("poster", favModel.getPosterUrl());
        goToFavouriteDetailsIntent.putExtra("title", favModel.getTitle());
        goToFavouriteDetailsIntent.putExtra("year", favModel.getYear());

        startActivity(goToFavouriteDetailsIntent);
    }
}