package com.example.themoviedb;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.API.RetrofitClient;
import com.example.themoviedb.API.RetrofitEndPoint;
import com.example.themoviedb.Model.MovieDetails;
import com.example.themoviedb.Model.MovieResponse;
import com.example.themoviedb.UI.MoviesAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerviewMovies, recyclerviewMovies2;

    //API key fornecida pelo site
    String apiKey = "38594c476985d7c2fad6093dc2ac98f7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerviewMovies = findViewById(R.id.recyclerviewMovies);
        recyclerviewMovies2 = findViewById(R.id.recyclerviewMovies2);
        recyclerviewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerviewMovies2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        //Inicia a Lista
        movieList();
    }

    //Chama o Retrofit e envia a key para fazer o request na api, Retortando os filmes mais populares do site
    private void movieList() {
            RetrofitEndPoint retrofitEndPoint = RetrofitClient.getClient().create(RetrofitEndPoint.class);
            Call<MovieResponse> call = retrofitEndPoint.getMovies(apiKey, 1);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<MovieDetails> movieDetails = response.body().getResults();
                    recyclerviewMovies.setAdapter(new MoviesAdapter(movieDetails, getApplicationContext()));
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Request Fail", Toast.LENGTH_SHORT).show();
                }
            });

            Call<MovieResponse> call2 = retrofitEndPoint.getMovieTopRated(apiKey);
            call2.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<MovieDetails> movieDetails = response.body().getResults();
                    recyclerviewMovies2.setAdapter(new MoviesAdapter(movieDetails, getApplicationContext()));
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Request Fail", Toast.LENGTH_SHORT).show();
                }
            });

    }
}