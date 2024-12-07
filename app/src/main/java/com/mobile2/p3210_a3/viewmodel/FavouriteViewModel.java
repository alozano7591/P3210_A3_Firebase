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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobile2.p3210_a3.model.FavMovieModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                            List<FavMovieModel> newFavList = new ArrayList<FavMovieModel>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("tag", "document ID: " + document.getId() + " => document data: " + document.getData());
                                newFavList.add(convertData(document.getId(), document.getData()));
                            }
                            favList.postValue(newFavList);
                        }
                        else {
                            Log.w("tag", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    // method to turn json data into something consumable by the view
    private FavMovieModel convertData(String docID, Map<String, Object> data){
        Log.i("tag", "convertData called");
        JSONObject convertedData = new JSONObject(data);
        Log.i("tag", convertedData.toString());

        String posterUrl = convertedData.optString("posterUrl", "no poster URL");
        String plot = convertedData.optString("plot", "no plot"); //oopsies!
        String year = convertedData.optString("year", "no year");
        String imdbID = convertedData.optString("imdbID", "no imdbID");
        String title = convertedData.optString("title", "no title");

        //return new FavMovieModel(posterUrl, year, imdbID, title);

        // Updated to include plot so that we can see the plot when going to the fav movie details
        //Document ID is the unique Firestore id used for the specific movie. Turns out we need this for proper CRUD
        return new FavMovieModel(docID, imdbID, posterUrl, title, year, plot);
    }

}
