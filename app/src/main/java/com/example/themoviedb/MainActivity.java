package com.example.themoviedb;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
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
    RecyclerView recyclerviewMovies, recyclerviewMovies2, searchList;
    ScrollView scrollView;
    SearchView.OnQueryTextListener queryTextListener;

    //API key fornecida pelo site
    String apiKey = "38594c476985d7c2fad6093dc2ac98f7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerviewMovies = findViewById(R.id.recyclerviewMovies);
        recyclerviewMovies2 = findViewById(R.id.recyclerviewMovies2);
        searchList = findViewById(R.id.searchList);
        scrollView = findViewById(R.id.mainScrollView);

        recyclerviewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerviewMovies2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        searchList.setLayoutManager(new GridLayoutManager(this, 2));

        //Inicia as Listas
        movieList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.movie_serach).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        if (searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //chama o metodo de procurar filmes
                    searchMovie(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    movieList();
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void searchMovie(String query) {
        //Callback de pesquisa de filmes
        RetrofitEndPoint retrofitEndPoint = RetrofitClient.getClient().create(RetrofitEndPoint.class);
        Call<MovieResponse> call = retrofitEndPoint.getSearchMovie(apiKey, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieDetails> movieDetails = response.body().getResults();
                searchList.setAdapter(new MoviesAdapter(movieDetails, getApplicationContext()));
                scrollView.setVisibility(View.GONE);
                searchList.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Chama o Retrofit e envia a key para fazer o request na api, Retortando os filmes mais populares do site
    private void movieList() {
        //callback de filmes populares
        RetrofitEndPoint retrofitEndPoint = RetrofitClient.getClient().create(RetrofitEndPoint.class);
        Call<MovieResponse> call = retrofitEndPoint.getMovies(apiKey, 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieDetails> movieDetails = response.body().getResults();
                recyclerviewMovies.setAdapter(new MoviesAdapter(movieDetails, getApplicationContext()));
                scrollView.setVisibility(View.VISIBLE);
                searchList.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request Fail", Toast.LENGTH_SHORT).show();
            }
        });

        //callback de filmes melhores avaliados
        Call<MovieResponse> call2 = retrofitEndPoint.getMovieTopRated(apiKey);
        call2.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieDetails> movieDetails = response.body().getResults();
                recyclerviewMovies2.setAdapter(new MoviesAdapter(movieDetails, getApplicationContext()));
                scrollView.setVisibility(View.VISIBLE);
                searchList.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
}