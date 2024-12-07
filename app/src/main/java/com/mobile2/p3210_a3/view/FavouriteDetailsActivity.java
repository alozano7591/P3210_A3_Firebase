package com.mobile2.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobile2.p3210_a3.R;
import com.mobile2.p3210_a3.databinding.ActivityFavouriteDetailsBinding;
import com.mobile2.p3210_a3.databinding.ActivityMovieDetailsBinding;
import com.mobile2.p3210_a3.model.FavMovieModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDetailsActivity extends AppCompatActivity {

    String docId;
    String movieId;
    String title;
    String posterURL;
    String plot;

    ActivityFavouriteDetailsBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavouriteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // if mAuth is null then we are not logged in, so send user to login
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        db = FirebaseFirestore.getInstance();

        Intent movieIntentObj = getIntent();
        docId = movieIntentObj.getStringExtra("docID");
        movieId = movieIntentObj.getStringExtra("imdbID");
        title = movieIntentObj.getStringExtra("title");
        posterURL = movieIntentObj.getStringExtra("poster");
        plot = movieIntentObj.getStringExtra("plot");

        updateUI();

        // NEW ADDITION
        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMoviePlot(binding.plotTV.getText().toString());
            }
        });
        // NEW ADDITION

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoBackToMain();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMovieFromDb();
            }
        });

    }

    public void updateUI(){
        binding.titleTV.setText(title);
        binding.plotTV.setText(plot);
        ImageView posterIV = binding.posterIV;

        Glide.with(posterIV.getContext())
                .load(posterURL)
                .placeholder(R.drawable.theatre447)
                .into(posterIV);
    }

    // NEW ADDITION
    public void updateMoviePlot(String newPlot){
        String userId = mAuth.getCurrentUser().getUid();

        db.collection("userMovieFavourites")
                .document(userId)
                .collection("movies")
                .document(docId)
                .update("plot", newPlot)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("tag", "Plot edited successfully.");
                        Toast.makeText(FavouriteDetailsActivity.this, "Plot edited successfully.", Toast.LENGTH_SHORT).show();
                        // goBackToFavouritesList();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("tag", "Error editing plot.", e);
                    Toast.makeText(FavouriteDetailsActivity.this, "Error editing plot.", Toast.LENGTH_SHORT).show();
                });
    }
    // NEW ADDITION

    public void deleteMovieFromDb(){
        String userId = mAuth.getCurrentUser().getUid();

        db.collection("userMovieFavourites")
                .document(userId)
                .collection("movies")
                .document(docId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void unused) {
                          Log.w("tag", "Deletion Successful.");
                          Toast.makeText(FavouriteDetailsActivity.this, "Movie deleted successfully.", Toast.LENGTH_SHORT).show();
                          // goBackToFavouritesList();
                      }
                  })
                .addOnFailureListener(e -> {
                    Log.w("tag", "Error getting documents.", e);
                    Toast.makeText(FavouriteDetailsActivity.this, "Failed to delete movie.", Toast.LENGTH_SHORT).show();
                });
    }

    public void GoBackToMain(){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void goBackToFavouritesList() {
        // figure out how to navigate to favourites fragment when loading mainactivity
    }
}