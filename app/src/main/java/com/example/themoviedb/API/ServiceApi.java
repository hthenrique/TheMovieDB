package com.example.themoviedb.API;

import com.example.themoviedb.Model.MovieDetails;
import com.example.themoviedb.Model.MovieResponse;

import java.util.List;

public interface ServiceApi {
    interface ServiceApiCallback<T>{
        void onLoaded(List<MovieDetails> movies);
    }

    void getMovies(String apiKey, ServiceApiCallback<MovieResponse> callback);

    void getSearch(String apiKey, ServiceApiCallback<MovieDetails> callback);

}
