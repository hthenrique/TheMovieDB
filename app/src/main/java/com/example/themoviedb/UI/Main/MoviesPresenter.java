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
        serviceApi.getMovies(apiKey,movies -> {
            mMoviesView.showMovies(movies);
        });
    }

    @Override
    public void loadMoviesTopRated(String apiKey) {
        serviceApi.getMoviesTopRated(apiKey,movies -> {
            mMoviesView.showMoviesTopRated(movies);
        });
    }

    @Override
    public void loadSearchMovies(String apiKey, String query) {
        serviceApi.getSearch(apiKey, query, movies -> {
            mMoviesView.showSearchMovies(movies);
        });
    }
}
