package com.example.themoviedb.API;

import com.example.themoviedb.Model.MovieDetails;
import com.example.themoviedb.Model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitEndPoint {
    @GET("movie/popular/")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieResponse> getMovieTopRated(@Query("api_key") String apiKey);
}
