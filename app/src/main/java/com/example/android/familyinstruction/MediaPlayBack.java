package com.example.android.familyinstruction;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MediaPlayBack extends AppCompatActivity {

    private VideoView mVedioView;
    private TextView mVedioPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail_information);

        // 接收传入的vedio url以及分剧情简介信息
        Intent intent = getIntent();
        final String vedioUrl = intent.getExtras().getString("vedioUrl");
        final String vedioPlot = intent.getExtras().getString("plot");

        mVedioView = (VideoView) findViewById(R.id.vedio_view);
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

        mVedioPlot = (TextView)findViewById(R.id.plot_text_view);
        mVedioPlot.setText(vedioPlot);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_media_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.vedio_collection:
                addVedioToCollection();
                break;
            case R.id.media_remove:
                removeVedioFromCollection();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // TODO 收藏视频
    private void addVedioToCollection(){}
    // TODO 将视频移出视频视频收藏夹
    private void removeVedioFromCollection(){}
}
