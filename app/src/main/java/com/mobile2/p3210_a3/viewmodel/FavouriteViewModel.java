package com.mobile2.p3210_a3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile2.p3210_a3.model.FavMovieModel;

import java.util.List;

public class FavouriteViewModel extends ViewModel  {

    private final MutableLiveData<List<FavMovieModel>> favList = new MutableLiveData<>();

    public FavouriteViewModel(){

    }

    public LiveData<List<FavMovieModel>> getFavList(){
        return favList;
    }

    // method to get a specifc movie

    // method to turn json data into something consumable by the view
}
