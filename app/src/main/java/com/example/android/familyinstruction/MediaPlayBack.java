package com.example.android.familyinstruction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.android.familyinstruction.data.InstructionContract.UserMediaCollectionEntry;

public class MediaPlayBack extends AppCompatActivity {

    private VideoView mVedioView;
    private TextView mVedioPlot;

    private String headTitle;
    private String subTitle;
    private int thumbnail;
    private String vedioPlot;
    private String vedioUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail_information);

        // 接收传入的vedio url以及分剧情简介信息
        Intent intent = getIntent();
        headTitle = intent.getExtras().getString("headTitle");
        subTitle = intent.getExtras().getString("subTitle");
        thumbnail = intent.getExtras().getInt("thumbnail");
        vedioUrl = intent.getExtras().getString("vedioUrl");
        vedioPlot = intent.getExtras().getString("plot");

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
        MenuItem mediaCollection =  menu.findItem(R.id.media_collection);
        MenuItem mediaRemove = menu.findItem(R.id.media_remove);
        if(headTitle==null){
            mediaCollection.setVisible(false);
            mediaRemove.setVisible(true);
        }else{
            mediaCollection.setVisible(true);
            mediaRemove.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.media_collection:
                addVedioToCollection(subTitle);
                break;
            case R.id.media_remove:
                removeVedioFromCollection();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // TODO 收藏视频
    private void addVedioToCollection(String subTitle){
        // 判断要添加的视频是否在我的书架中
        String[] projection = {
                UserMediaCollectionEntry.COLUMN_SUB_TITLE,
        };
        Cursor cursor = getContentResolver().query(UserMediaCollectionEntry.CONTENT_URI, projection, null, null, null);
        int subTtileColumnIndex = cursor.getColumnIndex(UserMediaCollectionEntry.COLUMN_SUB_TITLE);
        int currentVedioInCollection = 0;
        while (cursor.moveToNext()){
            String currentSubTitle = cursor.getString(subTtileColumnIndex);
            if(currentSubTitle.equals(subTitle)){
                currentVedioInCollection = 1;
            }
        }
        if(currentVedioInCollection == 0){
            ContentValues values = new ContentValues();
            values.put(UserMediaCollectionEntry.COLUMN_HEAD_TITLE,headTitle);
            values.put(UserMediaCollectionEntry.COLUMN_SUB_TITLE,subTitle);
            values.put(UserMediaCollectionEntry.COLUMN_THUMBNAIL,thumbnail);
            values.put(UserMediaCollectionEntry.COLUMN_PLOT,vedioPlot);
            values.put(UserMediaCollectionEntry.COLUMN_MEDIA_DATA,vedioUrl);
            Uri newUri = getContentResolver().insert(UserMediaCollectionEntry.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.collection_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.collection_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, getString(R.string.collection_vedio_repeat),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // TODO 将视频移出视频视频收藏夹
    private void removeVedioFromCollection(){
        String selection = UserMediaCollectionEntry.COLUMN_SUB_TITLE + "=?";
        String[] selectionArgs = new String[]{subTitle};
        int rowsDeleted = getContentResolver().delete(UserMediaCollectionEntry.CONTENT_URI, selection, selectionArgs);
        if (rowsDeleted == 0) {
            Toast.makeText(this, getString(R.string.remove_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.remove_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
