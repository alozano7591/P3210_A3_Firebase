package com.example.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.p3210_a3.ItemClickListener;
import com.example.p3210_a3.viewmodel.MyAdapter;
import com.example.p3210_a3.databinding.ActivityMainBinding;
import com.example.p3210_a3.model.MovieModel;
import com.example.p3210_a3.viewmodel.SearchViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    ActivityMainBinding binding;
    MyAdapter myAdapter;
    SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        myAdapter = new MyAdapter(this, new ArrayList<>());
        myAdapter.setClickListener(this);

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(myAdapter);

        viewModel.getMovieList().observe(this, movieList -> {
            Log.i("tag", "Update View");
            myAdapter.updateMovies(movieList);

            if(movieList.size() == 0 || movieList.isEmpty()){
                Toast.makeText(this, "No movies found.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchButton.setOnClickListener(v -> {
            String title = binding.searchEditText.getText().toString().trim();
            if (!title.isEmpty()) {
                viewModel.fetchMoviesByTitle(title);
            } else {
                Toast.makeText(this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v, int pos) {
        Log.i("tag", "Go to movie clicked");
        goToMovieDetails(myAdapter.getMovies().get(pos));
    }

    public void goToMovieDetails(MovieModel movieModel){
        if(movieModel == null){
            Toast.makeText(this, "Error: movie object is null!", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.i("tag", "goToMovieDetails making intent");

            Intent intentMovieObj = new Intent(this, MovieDetailsActivity.class);
            intentMovieObj.putExtra("id", movieModel.getImdbID());
            intentMovieObj.putExtra("title", movieModel.getTitle());
            intentMovieObj.putExtra("year", movieModel.getYear());
            intentMovieObj.putExtra("posterUrl", movieModel.getPosterUrl());

            startActivity(intentMovieObj);
        }
    }

}