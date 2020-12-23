package com.example.themoviedb.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovieDetailsTest {

    @Test
    public void modelTest() {
        MovieDetails movieDetails = new MovieDetails(
                155,
                "Dark Kinght",
                "en",
                "/kkjTbwV1Xnj8wBL52PjOcXzTbnb.jpg",
                null,
                null,
                null,
                null,
                null,
                "2008-07-16");
        System.out.println(movieDetails.getId());
        System.out.println(movieDetails.getOriginalTitle());
        System.out.println(movieDetails.getReleaseDate());
        System.out.println(movieDetails.getId());
        System.out.println(movieDetails.getPosterPath());
    }


}