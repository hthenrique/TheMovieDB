package com.example.themoviedb.UI.Main;

import com.example.themoviedb.Model.MovieDetails;

import java.util.List;

public interface MoviesContract {

    interface View{
        void showMovies(List<MovieDetails> movies);
        void showMoviesTopRated(List<MovieDetails> movies);
        void showSearchMovies(List<MovieDetails> movies);
    }

    interface UserActionsListener{
        void loadMovies(String apiKey);
        void loadMoviesTopRated(String apiKey);
        void loadSearchMovies(String apiKey, String query);
    }
}
