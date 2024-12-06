package com.mobile2.p3210_a3.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile2.p3210_a3.R;
import com.mobile2.p3210_a3.databinding.FragmentSearchMoviesBinding;
import com.mobile2.p3210_a3.viewmodel.MyAdapter;
import com.mobile2.p3210_a3.viewmodel.SearchViewModel;


public class SearchMovies extends Fragment {

    FragmentSearchMoviesBinding binding;
    SearchViewModel viewModel;
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        binding = FragmentSearchMoviesBinding.inflate(inflater, container, false);
//        View view = binding.getRoot();
//
//        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
//        viewModel.getMovieList().observe(getViewLifecycleOwner(), movieList -> {
//            Log.i("tag", "Update View");
//            myAdapter.updateMovies(movieList);

            // COMMENTED OUT BECAUSE TOASTS ARE BROKEN FOR SOME REASON
//            if(movieList.size() == 0 || movieList.isEmpty()){
//                Toast.makeText(this, "No movies found.", Toast.LENGTH_SHORT).show();
//            }
//        });

        return inflater.inflate(R.layout.fragment_search_movies, container, false);
//        return view;
    }
}