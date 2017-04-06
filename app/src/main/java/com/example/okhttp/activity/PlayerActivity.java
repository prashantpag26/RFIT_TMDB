package com.example.okhttp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.okhttp.R;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private String videoUrl;
    private MediaController mediaControls;
    private VideoView myVideoView;
    private int position;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        myVideoView = (VideoView) findViewById(R.id.surface_video);
//        fullSizeView();
        Intent bundle = getIntent();
        if (bundle != null) {
            videoUrl = bundle.getStringExtra("login");
        } else {
            finish();
        }
        if (this.mediaControls == null) {
            this.mediaControls = new MediaController(this);
        }
        progressDialog = createProgressDialog();
        executeVideo();
        myVideoView.requestFocus();
        myVideoView.setOnPreparedListener(this);

    }

    private void executeVideo() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        String youtubeLink = "http://youtube.com/watch?v=" + videoUrl;
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    int itag = 22;
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    try {
                        myVideoView.setMediaController(mediaControls);
                        myVideoView.setVideoURI(Uri.parse(downloadUrl));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }.extract(youtubeLink, true, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void fullSizeView() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myVideoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        this.myVideoView.setLayoutParams(params);

    }

    public ProgressDialog createProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(PlayerActivity.this);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.prograss_dialog);
        // dialog.setMessage(Message);
        return dialog;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        myVideoView.seekTo(position);
        progressDialog.dismiss();
        if (position == 0) {
            myVideoView.start();
        } else {
            myVideoView.pause();
        }
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", this.myVideoView.getCurrentPosition());
        this.myVideoView.pause();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.position = savedInstanceState.getInt("Position");
        this.myVideoView.seekTo(this.position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
