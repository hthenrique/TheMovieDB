package com.example.themoviedb.UI.Main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.Model.MovieDetails;
import com.example.themoviedb.R;
import com.example.themoviedb.UI.Adapters.MoviesPopularAdapter;
import com.example.themoviedb.UI.Adapters.MoviesSearchAdapter;
import com.example.themoviedb.UI.Adapters.MoviesTopRatedAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesContract.View {
    RecyclerView recyclerviewPopularMovies, recyclerviewMoviesTopRated, searchList;
    ScrollView scrollView;
    SearchView.OnQueryTextListener queryTextListener;
    MoviesPopularAdapter moviesPopularAdapter;
    MoviesTopRatedAdapter moviesTopRatedAdapter;
    MoviesSearchAdapter moviesSearchAdapter;
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

        //movieListener.loadMovies(apiKey);
        moviesPopularAdapter = new MoviesPopularAdapter(new ArrayList<MovieDetails>(0),getApplicationContext());
        moviesTopRatedAdapter = new MoviesTopRatedAdapter(new ArrayList<MovieDetails>(0),getApplicationContext());
        moviesSearchAdapter = new MoviesSearchAdapter(new ArrayList<MovieDetails>(0),getApplicationContext());

        movieListener = new MoviesPresenter(this);

        recyclerviewPopularMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerviewMoviesTopRated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        searchList.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerviewPopularMovies.setAdapter(moviesPopularAdapter);
        recyclerviewMoviesTopRated.setAdapter(moviesTopRatedAdapter);

        //Inicia as Listas
        //movieList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieListener.loadMovies(apiKey);
        movieListener.loadMoviesTopRated(apiKey);
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
                    movieListener.loadSearchMovies(apiKey,query);
                    searchList.setAdapter(moviesSearchAdapter);
                    scrollView.setVisibility(View.GONE);
                    searchList.setVisibility(View.VISIBLE);
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

    private void movieList() {
        scrollView.setVisibility(View.VISIBLE);
        searchList.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(List<MovieDetails> movies) {
        moviesPopularAdapter.replaceData(movies);
        scrollView.setVisibility(View.VISIBLE);
        searchList.setVisibility(View.GONE);
    }

    @Override
    public void showMoviesTopRated(List<MovieDetails> moviesTopRated) {
        moviesTopRatedAdapter.replaceData(moviesTopRated);
        scrollView.setVisibility(View.VISIBLE);
        searchList.setVisibility(View.GONE);
    }

    @Override
    public void showSearchMovies(List<MovieDetails> movies) {
        moviesSearchAdapter.replaceData(movies);
        scrollView.setVisibility(View.GONE);
        searchList.setVisibility(View.VISIBLE);
    }
}