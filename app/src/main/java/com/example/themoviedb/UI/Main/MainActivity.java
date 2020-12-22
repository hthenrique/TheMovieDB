package com.example.themoviedb.UI.Main;

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

import com.example.themoviedb.API.RequestApi;
import com.example.themoviedb.API.RetrofitClient;
import com.example.themoviedb.API.RetrofitEndPoint;
import com.example.themoviedb.Model.MovieDetails;
import com.example.themoviedb.Model.MovieResponse;
import com.example.themoviedb.R;
import com.example.themoviedb.UI.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesContract.View {
    RecyclerView recyclerviewPopularMovies, recyclerviewMoviesTopRated, searchList;
    ScrollView scrollView;
    SearchView.OnQueryTextListener queryTextListener;
    MoviesAdapter moviesAdapter;
    MoviesContract.UserActionsListener movieListener;
    //API key fornecida pelo site
    String apiKey = "38594c476985d7c2fad6093dc2ac98f7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerviewPopularMovies = findViewById(R.id.recyclerviewPopularMovies);
        recyclerviewMoviesTopRated = findViewById(R.id.recyclerviewMoviesTopRated);
        searchList = findViewById(R.id.searchList);
        scrollView = findViewById(R.id.mainScrollView);

        movieListener.loadMovies(apiKey);

        moviesAdapter = new MoviesAdapter(new ArrayList<MovieDetails>(0),getApplicationContext());
        movieListener = new MoviesPresenter(this);
        recyclerviewPopularMovies.setAdapter(moviesAdapter);

        recyclerviewPopularMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerviewMoviesTopRated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        searchList.setLayoutManager(new GridLayoutManager(this, 2));

        //Inicia as Listas
        //movieList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieListener.loadMovies(apiKey);
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

                    /*recyclerviewMoviesTopRated.setAdapter(new MoviesAdapter(movieDetails, getApplicationContext()));
                    scrollView.setVisibility(View.VISIBLE);
                    searchList.setVisibility(View.GONE);*/
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

    private void movieList() {
        scrollView.setVisibility(View.VISIBLE);
        searchList.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(List<MovieDetails> movies) {
        moviesAdapter.replaceData(movies);
    }
}