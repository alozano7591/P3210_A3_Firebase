package com.mobile2.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobile2.p3210_a3.R;
import com.mobile2.p3210_a3.databinding.ActivityMovieDetailsBinding;
import com.mobile2.p3210_a3.model.FavMovieModel;
import com.mobile2.p3210_a3.utils.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    String movieId;

    String title;
    String year;
    String plot;
    String genre;
    String posterURL;
    String imdbRating;

    ActivityMovieDetailsBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("tag", "MovieDetails OnCreate");

        setContentView(R.layout.activity_movie_details);

        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        Intent movieIntentObj = getIntent();
        movieId = movieIntentObj.getStringExtra("id");
        title = movieIntentObj.getStringExtra("title");
        year = movieIntentObj.getStringExtra("year");
        posterURL = movieIntentObj.getStringExtra("posterUrl");
        binding.movieTitleTV.setText(title);

        fetchMoviesDetailsById(movieId);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoBackToMain();
            }
        });

    }

    private void fetchMoviesDetailsById(String imdbId){
        Log.i("tag", "enter fetchMoviesByTitle search " + imdbId);
        String baseUrl = "https://www.omdbapi.com/?apikey=865e2af0&i=";
        String getUrl = baseUrl + imdbId;     // search using movie id

        ApiClient.get(getUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("tag", "onFailure");
                Log.i("tag", e.getMessage().toString());
                Log.i("tag", "IMDb id: " + imdbId);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i("tag", "onResponse IMDb id: " + imdbId);
                if(response.isSuccessful()){
                    assert response.body() != null;
                    String responseData = response.body().string();
                    runOnUiThread(() -> {
                        Log.i("tag", responseData);
                        try {
                            parseData(responseData);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        });
    }

    private void parseData(String data) throws JSONException{
        JSONObject json = new JSONObject(data);

        title = json.getString("Title");
        year = json.getString("Year");
        plot = json.getString("Plot");
        genre = json.getString("Genre");
        imdbRating = json.getString("imdbRating");

        runOnUiThread(() -> {
            updateUI();
        });

    }

    public void updateUI(){
        binding.movieTitleTV.setText(title);
        binding.textViewYear.setText(year);
        binding.textViewPlot.setText(plot);
        binding.textViewGenre.setText(genre);
        binding.textViewIMDbRating.setText("IMBd: " + imdbRating);

        ImageView posterIV = binding.imageViewPoster;

        Glide.with(posterIV.getContext())
                .load(posterURL)
                .placeholder(R.drawable.theatre447)
                .into(posterIV);
    }

    private void saveToFireStoreFavourites(FavMovieModel movie, FirebaseFirestore db){
        String userId = mAuth.getCurrentUser().getUid();

        db.collection("userMovieFavourites").document(userId).collection("movies")
                .add(movie)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(MovieDetailsActivity.this, "Movie added to favourites!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(MovieDetailsActivity.this, "Failed to add movie: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    public void GoBackToMain(){
        startActivity(new Intent(this, MainActivity.class));
    }
}