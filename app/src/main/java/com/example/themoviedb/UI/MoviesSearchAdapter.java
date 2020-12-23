package com.example.themoviedb.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.Model.MovieDetails;
import com.example.themoviedb.R;
import com.example.themoviedb.UI.MovieDetails.MovieDatailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesSearchAdapter extends RecyclerView.Adapter<MoviesSearchAdapter.ViewHolder> {
    private List<MovieDetails> moviesList;
    private Context context;

    public MoviesSearchAdapter(List<MovieDetails> movies, Context context){
        this.moviesList = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie_list, parent, false);
        return new MoviesSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesSearchAdapter.ViewHolder holder, int position) {
        MovieDetails movieDetails = moviesList.get(position);
        String posterLink = "http://image.tmdb.org/t/p/original" + movieDetails.getPosterPath();

        Picasso.get()
                .load(posterLink)
                .fit().centerCrop()
                .into(holder.moviePoster);
        holder.movieTitle.setText(movieDetails.getTitle());
        holder.releaseDate.setText(movieDetails.getReleaseDate());

        holder.movieRatingBar.setMax(5);
        holder.movieRatingBar.setStepSize(0.01f);
        double rate = movieDetails.getVoteAverage();
        holder.movieRatingBar.setRating((float)rate/2);

        holder.itemView.setOnClickListener(v -> {
            Intent movieDetail = new Intent(context, MovieDatailsActivity.class);
            movieDetail.putExtra("idMovie", movieDetails.getId());
            movieDetail.putExtra("titleMovie", movieDetails.getTitle());
            movieDetail.putExtra("languageMovie", movieDetails.getOriginalLanguage());
            movieDetail.putExtra("overviewMovie", movieDetails.getOverview());
            movieDetail.putExtra("posterMovie", posterLink);
            movieDetail.putExtra("releaseDateMovie", movieDetails.getReleaseDate());
            movieDetail.putExtra("ratingBar", movieDetails.getVoteAverage());
            context.startActivity(movieDetail);
        });
    }

    @Override
    public int getItemCount() {
        if (moviesList != null){
            return moviesList.size();
        }else {
            Toast.makeText(context, "No Movies", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public void replaceData(List<MovieDetails> movies) {
        setList(movies);
        notifyDataSetChanged();
    }

    private void setList(List<MovieDetails> movies) {
        moviesList = movies;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle, releaseDate;
        RatingBar movieRatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            movieRatingBar = itemView.findViewById(R.id.movieRatingBar);
        }
    }

}
