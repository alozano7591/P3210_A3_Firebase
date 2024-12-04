package com.example.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.p3210_a3.R;
import com.example.p3210_a3.utils.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView titleTV;
    TextView yearTV;
    TextView plotTV;
    TextView genreTV;
    TextView imdbRatingTV;
    ImageView posterIV;
    Button backButton;

    String movieId;

    String title;
    String year;
    String plot;
    String genre;
    String posterURL;
    String imdbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("tag", "MovieDetails OnCreate");

        setContentView(R.layout.activity_movie_details);

        Intent movieIntentObj = getIntent();
        movieId = movieIntentObj.getStringExtra("id");
        title = movieIntentObj.getStringExtra("title");
        year = movieIntentObj.getStringExtra("year");
        posterURL = movieIntentObj.getStringExtra("posterUrl");

        titleTV = findViewById(R.id.movieTitleTV);
        posterIV = findViewById(R.id.imageViewPoster);
        yearTV = findViewById(R.id.textViewYear);
        plotTV = findViewById(R.id.textViewPlot);
        genreTV = findViewById(R.id.textViewGenre);
        imdbRatingTV = findViewById(R.id.textViewIMDbRating);

        titleTV.setText(title);

        backButton = findViewById(R.id.backButton);

        fetchMoviesDetailsById(movieId);

        backButton.setOnClickListener(new View.OnClickListener() {
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
        titleTV.setText(title);
        yearTV.setText(year);
        plotTV.setText(plot);
        genreTV.setText(genre);
        imdbRatingTV.setText("IMBd: " + imdbRating);

        Glide.with(posterIV.getContext())
                .load(posterURL)
                .placeholder(R.drawable.theatre447)
                .into(posterIV);
    }

    public void GoBackToMain(){
        startActivity(new Intent(this, MainActivity.class));
    }
}