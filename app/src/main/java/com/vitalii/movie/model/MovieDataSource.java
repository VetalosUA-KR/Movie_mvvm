package com.vitalii.movie.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.vitalii.movie.R;
import com.vitalii.movie.service.MovieApiService;
import com.vitalii.movie.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//First generic type is which we will be send, second is which we will get as response
public class MovieDataSource extends PageKeyedDataSource<Long, Result> {

    private MovieApiService movieApiService;
    private Application application;

    public MovieDataSource(MovieApiService movieApiService, Application application) {
        this.movieApiService = movieApiService;
        this.application = application;
    }

    //method to get first page
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Result> callback) {
        movieApiService = RetrofitInstance.getService();
        Call<MovieApiResponse> call = movieApiService.getPopularMoviesWithPaging(
                application.getApplicationContext().getString(R.string.api_key),
                1
        );
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                MovieApiResponse movieApiResponse = response.body();
                if(movieApiResponse != null && movieApiResponse.getResults() != null) {
                    ArrayList<Result> results = (ArrayList<Result>) movieApiResponse.getResults();
                    callback.onResult(results, null, (long) 2);
                }
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Result> callback) {

    }

    //method to get next pages
    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull final LoadCallback<Long, Result> callback) {
        movieApiService = RetrofitInstance.getService();
        Call<MovieApiResponse> call = movieApiService.getPopularMoviesWithPaging(
                application.getApplicationContext().getString(R.string.api_key),
                params.key
        );
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                MovieApiResponse movieApiResponse = response.body();
                if(movieApiResponse != null && movieApiResponse.getResults() != null) {
                    ArrayList<Result> results = (ArrayList<Result>) movieApiResponse.getResults();
                    // params.key it's a previous page
                    callback.onResult(results, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });
    }
}
