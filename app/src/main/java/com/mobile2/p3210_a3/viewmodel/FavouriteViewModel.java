package com.mobile2.p3210_a3.viewmodel;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobile2.p3210_a3.model.FavMovieModel;

import java.util.List;

public class FavouriteViewModel extends ViewModel  {

    private final MutableLiveData<List<FavMovieModel>> favList = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public FavouriteViewModel(){

    }

    public LiveData<List<FavMovieModel>> getFavList(){
        return favList;
    }

    // method to get a specific movie
    public void getFavouritesFromDb(){
        String userId = mAuth.getCurrentUser().getUid();

        db.collection("userMovieFavourites")
                .document(userId)
                .collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("tag", document.getId() + " => " + document.getId());
                            }
                        }
                        else {
                            Log.w("tag", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    // method to turn json data into something consumable by the view
}
