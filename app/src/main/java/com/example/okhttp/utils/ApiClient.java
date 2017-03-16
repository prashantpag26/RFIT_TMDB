package com.example.okhttp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiClient class created.
 */

public class ApiClient {
//    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String BASE_URL = "https://www.googleapis.com/youtube/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
