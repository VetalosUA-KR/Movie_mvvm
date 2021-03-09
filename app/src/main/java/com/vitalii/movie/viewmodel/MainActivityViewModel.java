package com.vitalii.movie.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.vitalii.movie.model.MovieDataSource;
import com.vitalii.movie.model.MovieDataSourceFactory;
import com.vitalii.movie.model.MovieRepository;
import com.vitalii.movie.model.Result;
import com.vitalii.movie.service.MovieApiService;
import com.vitalii.movie.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//page list действует между DataSource и PageListAdapter
public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<MovieDataSource> movieDataSourceLiveData;
    //для автоматического управления потоками с помощью этого класса несколько потоков будут загружаться паралельно
    private Executor executor;
    private LiveData<PagedList<Result>> pagedListLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);

        MovieApiService movieApiService = RetrofitInstance.getService();
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(application, movieApiService);

        movieDataSourceLiveData = movieDataSourceFactory.getMutableLiveData();

        //определяем конфигурацию pageList
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10) //кол-во начально загружаемых элементов
                .setPageSize(20) // кол-во элементов загружаемых в pageList
                .setPrefetchDistance(3)// кол-во изначально загружаемых страниц
                .build();

        executor = Executors.newCachedThreadPool();
        pagedListLiveData = new LivePagedListBuilder<Long, Result>(movieDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<List<Result>> getAllMovieData() {

        return movieRepository.getMutableLiveData();
    }

    public LiveData<PagedList<Result>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}








