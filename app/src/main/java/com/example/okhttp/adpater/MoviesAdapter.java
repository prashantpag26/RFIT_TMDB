package com.example.okhttp.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.okhttp.R;
import com.example.okhttp.Result;
import com.example.okhttp.activity.MainActivity;
import com.example.okhttp.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * MoviesAdapter class created.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final Integer totalPageResult;
    private List<Item> results;
    private int rowLayout;
    private int more = 2;
    private Context context;
    private Activity activity;


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.movieTitle.setText(results.get(position).getSnippet().getTitle());
//        holder.data.setText(results.get(position).getSnippet().ge);
        holder.movieDescription.setText(results.get(position).getSnippet().getDescription());
//        holder.rating.setText(results.get(position).getSnippet().toString());
        if (totalPageResult != getItemCount()) {
            if (position + more == getItemCount()) {
                ((MainActivity) activity).nextCall();
            }
        }

    }

    public MoviesAdapter(List<Item> movies, int rowLayout, Activity activity, Integer totalPageResult) {
        this.results = movies;
        this.rowLayout = rowLayout;
        this.activity = activity;
        this.totalPageResult = totalPageResult;
    }

    @Override
    public int getItemCount() {
        return results.size();

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;

        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
    }
}
