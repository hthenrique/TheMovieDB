package com.example.themoviedb.UI.Main;

import android.content.Context;

import com.example.themoviedb.API.RequestApi;
import com.example.themoviedb.API.ServiceApi;

public class MoviesPresenter implements MoviesContract.UserActionsListener{
    private final ServiceApi serviceApi;
    private final MoviesContract.View mMoviesView;
    Context context;

    public MoviesPresenter(MoviesContract.View moviesView) {
        serviceApi = new RequestApi(context);
        mMoviesView = moviesView;
    }

    @Override
    public void loadMovies(String apiKey) {
        if (apiKey == null){
            apiKey = "38594c476985d7c2fad6093dc2ac98f7";
            serviceApi.getMovies(apiKey,movies -> {
                mMoviesView.showMovies(movies);
            });
        }
    }
}
