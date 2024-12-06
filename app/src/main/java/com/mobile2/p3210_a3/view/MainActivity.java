package com.mobile2.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// FRAGMENT STUFF \/
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
// FRAGMENT STUFF /\
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobile2.p3210_a3.ItemClickListener;
import com.mobile2.p3210_a3.R; // ALSO PART OF FRAGMENT TESTING
import com.mobile2.p3210_a3.viewmodel.MyAdapter;
import com.mobile2.p3210_a3.databinding.ActivityMainBinding;
import com.mobile2.p3210_a3.model.MovieModel;
import com.mobile2.p3210_a3.viewmodel.SearchViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    ActivityMainBinding binding;
    MyAdapter myAdapter;
    SearchViewModel viewModel;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Establish firebase auth variable and check if user logged in
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        // initialize db. Might not be a good spot to have it
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        binding.logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        // FRAGMENT EXPERIMENTATION ZONE \/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

        // this line loads the search fragment when MainActivity is created
        replaceFragment(new SearchMovies());

        // this is the listener for the Nav Bar. if an item is selected, it grabs the corresponding...
        // ... ID, then uses that to load the appropriate fragment
        binding.bottomNavBar.setOnItemSelectedListener(item -> {

            switch(item.getItemId()) {

                case(R.id.navItem_Search):
                    replaceFragment(new SearchMovies());
                    break;

                case(R.id.navItem_Favourites):
                    replaceFragment(new FavouriteMovies());
                    break;
            }
            return true; // shut up compiler
        });
    }

    // this method actually handles loading the fragment, and takes in the fragment...
    // ... to be loaded as an arg
    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.testLayout, fragment);
        ft.commit();
    }

    // FRAGMENT EXPERIMENTATION ZONE /\/\/\/\/\/\/\//\/\/\/\/\/\/\/\/\/\/\/\/\

    public void logout(){
        mAuth.signOut();
        Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
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