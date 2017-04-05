package com.example.okhttp.interfaces;

import com.example.okhttp.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("v3/playlistItems")
    Call<Example> getFirstMovies(@Query("part") String part, @Query("playlistId") String playlist, @Query("key") String key, @Query("maxResults") String maxResults);

    @GET("v3/playlistItems")
    Call<Example> getNextMovies(@Query("part") String part, @Query("playlistId") String playlist, @Query("key") String key, @Query("maxResults") String maxResults, @Query("pageToken") String pageToken);
}