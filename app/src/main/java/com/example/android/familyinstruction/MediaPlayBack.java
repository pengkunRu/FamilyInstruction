package com.example.android.familyinstruction;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class MediaPlayBack extends AppCompatActivity {

    private VideoView mVedioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail_information);

        // 接收传入的vedio url
        Intent intent = getIntent();
        final String vedioUrl = intent.getExtras().getString("vedioUrl");

        mVedioView = (VideoView) findViewById(R.id.vedio_view);
//        mButton = (Button) findViewById(R.id.button);


        mVedioView.setVideoPath(vedioUrl);
        MediaController mediaController = new MediaController(MediaPlayBack.this);
        mediaController.setAnchorView(mVedioView);
        mVedioView.setMediaController(mediaController);
        mVedioView.requestFocus();
        mVedioView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                mVedioView.start();
            }
        });

    }
}
