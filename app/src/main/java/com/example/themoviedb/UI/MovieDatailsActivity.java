package com.example.themoviedb.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.themoviedb.R;
import com.squareup.picasso.Picasso;

public class MovieDatailsActivity extends AppCompatActivity {
    String title;
    String language;
    String overview;
    String poster;
    String releaseDate;
    double ratingValue;
    ImageView moviePoster;
    TextView titleMovie, releaseDateTv, originalLanguage, overviewTv;
    RatingBar movieRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_datails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        moviePoster = findViewById(R.id.moviePoster2);
        titleMovie = findViewById(R.id.titleMovie);
        releaseDateTv = findViewById(R.id.releaseDate2);
        originalLanguage = findViewById(R.id.originalLanguage);
        overviewTv = findViewById(R.id.overview);
        movieRatingBar = findViewById(R.id.movieRatingBar2);

        title = getIntent().getStringExtra("titleMovie");
        language = getIntent().getStringExtra("languageMovie");
        overview = getIntent().getStringExtra("overviewMovie");
        poster = getIntent().getStringExtra("posterMovie");
        releaseDate = getIntent().getStringExtra("releaseDateMovie");
        ratingValue = getIntent().getExtras().getDouble("ratingBar");

        setTitle(title);
        titleMovie.setText(getIntent().getStringExtra("titleMovie"));
        if (releaseDate == null){
            CharSequence text = "Don't have";
            releaseDateTv.setText("Release date: " + text);
        }else {
            releaseDateTv.setText("Release date: " + releaseDate);
        }

        originalLanguage.setText("Original Language: " + language);
        if (overview.equals("")){
            CharSequence text = "This movie don't have overview";
            overviewTv.setText(text);
        }else {
            overviewTv.setText(overview);
        }

        Picasso.get().load(poster).into(moviePoster);
        movieRatingBar.setMax(5);
        movieRatingBar.setStepSize(0.01f);
        movieRatingBar.setRating((float)ratingValue/2);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}