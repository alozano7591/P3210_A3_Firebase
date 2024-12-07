package com.mobile2.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile2.p3210_a3.ItemClickListener;
import com.mobile2.p3210_a3.R;
import com.mobile2.p3210_a3.databinding.FragmentFavouriteMoviesBinding;
import com.mobile2.p3210_a3.databinding.FragmentSearchMoviesBinding;
import com.mobile2.p3210_a3.model.FavMovieModel;
import com.mobile2.p3210_a3.model.MovieModel;
import com.mobile2.p3210_a3.viewmodel.FavouriteAdapter;
import com.mobile2.p3210_a3.viewmodel.FavouriteViewModel;
import com.mobile2.p3210_a3.viewmodel.MyAdapter;
import com.mobile2.p3210_a3.viewmodel.SearchViewModel;

import java.util.ArrayList;


public class SearchMovies extends Fragment implements ItemClickListener {

    FragmentSearchMoviesBinding binding;
    SearchViewModel viewModel;
    MyAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchMoviesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        movieAdapter = new MyAdapter(this.getContext(), new ArrayList<>());
        movieAdapter.setClickListener(this);

        binding.moviesRecyclerView.setLayoutManager(layoutManager);
        binding.moviesRecyclerView.setAdapter(movieAdapter);

        viewModel.getMovieList().observe(getViewLifecycleOwner(), movieList -> {
            Log.i("tag", "Update View");
            movieAdapter.updateMovies(movieList);

            if(movieList.size() == 0 || movieList.isEmpty()){
                Toast.makeText(this.getContext(), "No movies found.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.movSearchButton.setOnClickListener(v -> {
            String title = binding.movSearchEditText.getText().toString().trim();
            if (!title.isEmpty()) {
                viewModel.fetchMoviesByTitle(title);
            } else {
                Toast.makeText(this.getContext(), "Please enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });

        //return inflater.inflate(R.layout.fragment_search_movies, container, false);
        return view;
    }

    @Override
    public void onClick(View v, int pos){
        goToMovieDetails(movieAdapter.getMovies().get(pos));
    }

    public void goToMovieDetails(MovieModel movieModel){
        if(movieModel == null){
            Toast.makeText(this.getContext(), "Error: movie object is null!", Toast.LENGTH_SHORT).show();
        }

        Intent intentMovieObj = new Intent(this.getContext(), MovieDetailsActivity.class);
        intentMovieObj.putExtra("id", movieModel.getImdbID());
        intentMovieObj.putExtra("title", movieModel.getTitle());
        intentMovieObj.putExtra("year", movieModel.getYear());
        intentMovieObj.putExtra("posterUrl", movieModel.getPosterUrl());

        startActivity(intentMovieObj);
    }
}