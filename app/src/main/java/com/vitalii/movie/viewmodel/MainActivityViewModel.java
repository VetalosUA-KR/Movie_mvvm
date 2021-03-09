package com.vitalii.movie.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vitalii.movie.model.MovieRepository;
import com.vitalii.movie.model.Result;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Result>> getAllMovieData() {

        return movieRepository.getMutableLiveData();
    }
}
