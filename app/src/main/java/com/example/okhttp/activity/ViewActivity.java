package com.example.okhttp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.okhttp.R;
import com.example.okhttp.model.ContentDetails;
import com.example.okhttp.model.High;
import com.example.okhttp.model.ResourceId;
import com.example.okhttp.model.Snippet;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class ViewActivity extends AppCompatActivity {
    private Snippet snippet;
    private High high;
    private ResourceId resource;
    private TextView title;
    private TextView descreption;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        final Bundle intent = getIntent().getExtras();
        if (intent != null) {
            snippet = intent.getParcelable("snippet");
            high = intent.getParcelable("high");
            resource = intent.getParcelable("resource");
        } else {
            finish();
        }

        imageView = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        descreption = (TextView) findViewById(R.id.description);
        Log.e("Tag", snippet.getTitle());
        Glide.with(this).load(high.getUrl()).into(imageView);
        title.setText(snippet.getTitle());
        descreption.setText(snippet.getDescription());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = YouTubeStandalonePlayer.createVideoIntent(ViewActivity.this, "AIzaSyCPDJEgH1UWxbrP4A5fQSzgabytRuxtpoo", resource.getVideoId());
//                startActivity(intent);
                Intent intent = new Intent(ViewActivity.this,PlayerActivity.class);
                intent.putExtra("login",resource.getVideoId());
                startActivity(intent);
            }
        });


    }


}
