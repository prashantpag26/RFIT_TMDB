package com.example.okhttp.interfaces;

import com.example.okhttp.MovieResponse;
import com.example.okhttp.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("v3/playlistItems")
    Call<Example> getTopRatedMovies(@Query("part") String part, @Query("playlistId") String playlist, @Query("key") String key);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}