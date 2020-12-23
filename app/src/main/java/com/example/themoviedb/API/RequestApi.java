package com.example.themoviedb.API;

import android.content.Context;
import android.widget.Toast;

import com.example.themoviedb.Model.MovieDetails;
import com.example.themoviedb.Model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestApi implements ServiceApi {

    private RetrofitEndPoint retrofitEndPoint;
    private Context context;

    public RequestApi(Context context){
        this.context = context;
        retrofitEndPoint = RetrofitClient.getClient().create(RetrofitEndPoint.class);
    }

    @Override
    public void getMovies(String apiKey, ServiceApiCallback<MovieResponse> callback) {
        //callback de filmes populares
        Call<MovieResponse> call = retrofitEndPoint.getMovies(apiKey, 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.code()==200){
                    List<MovieDetails> movieDetails = response.body().getResults();
                    callback.onLoaded(movieDetails);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getMoviesTopRated(String apiKey, ServiceApiCallback<MovieResponse> callback) {
        //callback de filmes melhores avaliados
        Call<MovieResponse> call2 = retrofitEndPoint.getMovieTopRated(apiKey);
        call2.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.code()==200){
                    List<MovieDetails> movieDetails = response.body().getResults();
                    callback.onLoaded(movieDetails);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void getSearch(String apiKey, String query, ServiceApiCallback<MovieDetails> callback) {
        //Callback de pesquisa de filmes
        RetrofitEndPoint retrofitEndPoint = RetrofitClient.getClient().create(RetrofitEndPoint.class);
        Call<MovieResponse> call = retrofitEndPoint.getSearchMovie(apiKey, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.code()==200){
                    List<MovieDetails> movieDetails = response.body().getResults();
                    callback.onLoaded(movieDetails);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
