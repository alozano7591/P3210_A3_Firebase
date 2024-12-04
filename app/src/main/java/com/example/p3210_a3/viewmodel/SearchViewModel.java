package com.example.p3210_a3.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p3210_a3.model.MovieModel;
import com.example.p3210_a3.utils.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<MovieModel>> movieList = new MutableLiveData<>();

    public SearchViewModel(){

    }

    public LiveData<List<MovieModel>> getMovieList(){
        return movieList;
    }

    public void fetchMoviesByTitle(String title){

        Log.i("tag", "enter fetchMoviesByTitle search " + title);
        String baseUrl = "https://www.omdbapi.com/?apikey=865e2af0&s=";
        String getUrl = baseUrl + title;

        ApiClient.get(getUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("tag", "onFailure");
                Log.i("tag", e.getMessage().toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i("tag", "onResponse");
                if(response.isSuccessful()){
                    assert response.body() != null;
                    String responseData = response.body().string();

                    try {
                        parseData(responseData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    private void parseData(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        Log.i("tag", "parseData called");

        if(json.has("Search")){
            JSONArray searchResults = json.getJSONArray("Search");

            //movieList.clear();
            List<MovieModel> newMovieModelList = new ArrayList<>();
            for(int i = 0; i < searchResults.length(); i++)
            {
                JSONObject resultJson = searchResults.getJSONObject(i);

                String id = resultJson.optString("imdbID", "NO ID");
                String title = resultJson.optString("Title", "Unknown title");
                String year = resultJson.optString("Year", "Unkown year");
                String imgUrl = resultJson.optString("Poster", "missing url");

                MovieModel movieModel = new MovieModel(id, title, year, imgUrl);

                newMovieModelList.add(movieModel);
                //Log.i("tag", "Added movie title: " + title + " with ID: " + id);
            }

            // update our live data with the new movie list
            movieList.postValue(newMovieModelList);

            Log.i("tag", "parseData finished");
        }
    }

}
