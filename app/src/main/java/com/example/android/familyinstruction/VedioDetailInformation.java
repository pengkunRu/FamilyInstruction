package com.example.android.familyinstruction;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VedioDetailInformation extends AppCompatActivity {

    private VideoView mVedioView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail_information);

        mVedioView = (VideoView) findViewById(R.id.vedio_view);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVedioView.setVideoPath("http://p92t81y7d.bkt.clouddn.com/vedio_file1_1.mp4");
                MediaController mediaController = new MediaController(VedioDetailInformation.this);
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
        });
    }
}
