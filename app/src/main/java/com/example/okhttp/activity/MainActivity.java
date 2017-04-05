package com.example.okhttp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.okhttp.model.Example;
import com.example.okhttp.model.Item;
import com.example.okhttp.model.Snippet;
import com.example.okhttp.utils.ApiClient;
import com.example.okhttp.adpater.MoviesAdapter;
import com.example.okhttp.R;
import com.example.okhttp.interfaces.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final static String API_KEY = "AIzaSyCPDJEgH1UWxbrP4A5fQSzgabytRuxtpoo";
    private final static String KEY = "PL6gx4Cwl9DGBsvRxJJOzG4r4k_zLKrnxl";
    private final static String PART = "snippet";
    private static String NEXTTOCKEN = "";
    private final static String MAXRESULT = "10";
    private MoviesAdapter moviesAdapter;

    //    private final static String API_KEY = "43be33c47b037d9bec821dc389588e3c";
    private MoviesAdapter mAdapter;
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private ArrayList<Item> movies;
    private ApiInterface nextApiInterface;
    private Integer totalPageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        movies = new ArrayList<>();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
//        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(mLayoutManager);
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        nextApiInterface = ApiClient.getClient().create(ApiInterface.class);

        final Call<Example> movieCall = apiInterface.getFirstMovies(PART, KEY, API_KEY, MAXRESULT);
        movieCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                for (int i = 0; i < 10; i++) {
                    Log.e("LOLOL", response.body().getItems().get(i).getSnippet().getTitle() + "");
                }
                Log.e("LOLOL", response.body().getPageInfo().getTotalResults() + "");
                NEXTTOCKEN = response.body().getNextPageToken();
                totalPageResult = response.body().getPageInfo().getTotalResults();
                movies = response.body().getItems();
                moviesAdapter = new MoviesAdapter(movies, R.layout.list_item_movie, MainActivity.this,totalPageResult);
                recyclerView.setAdapter(moviesAdapter);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    public void nextCall() {
        final Call<Example> movieNextCall = nextApiInterface.getNextMovies(PART, KEY, API_KEY, MAXRESULT, NEXTTOCKEN);
        Log.e("LOLOL", movieNextCall.request().url() + "");
        movieNextCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.e("LOLOL", response.body().getItems().get(0).getSnippet().getTitle() + "");
                for (int i = 0; i < 10; i++) {
//                    Log.e("LOLOL", response.body().getItems().get(i).getSnippet().getTitle() + "");
                }
                NEXTTOCKEN = response.body().getNextPageToken();
                movies.addAll(response.body().getItems());
                moviesAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendData(Item item){
        Intent intent = new Intent(MainActivity.this,ViewActivity.class);
        intent.putExtra("resource",item.getSnippet().getResourceId());
        intent.putExtra("snippet",item.getSnippet());
        intent.putExtra("high",item.getSnippet().getThumbnails().getHigh());

        startActivity(intent);
    }
}
