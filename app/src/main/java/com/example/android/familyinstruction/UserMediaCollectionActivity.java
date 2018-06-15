package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.familyinstruction.data.InstructionContract.UserMediaCollectionEntry;

import java.util.ArrayList;

public class UserMediaCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_media_collection);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayList<MediaList> mediaList = getMediaList();

        MediaListAdapter adapter = new MediaListAdapter(this,mediaList);
        ListView listView = (ListView)findViewById(R.id.user_media_collection);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 由此进入视频的内容
                // 通过position，我们可以从medialist数组列表中获取medialist对象
                MediaList currentItem = mediaList.get(position);

                // 页面跳转至视频播放界面,并传入视频链接
                Intent intent = new Intent(UserMediaCollectionActivity.this,MediaPlayBack.class);
                intent.putExtra("subTitle",currentItem.getSubtitle());
                intent.putExtra("vedioUrl",currentItem.getMediaData());
                intent.putExtra("plot",currentItem.getPlot());
                startActivity(intent);
            }
        });
    }

    // TODO 辅助函数：从文本资源表取出所有的行
    private ArrayList<MediaList> getMediaList() {
        String[] projection = {
                UserMediaCollectionEntry.COLUMN_HEAD_TITLE,
                UserMediaCollectionEntry.COLUMN_SUB_TITLE,
                UserMediaCollectionEntry.COLUMN_THUMBNAIL,
                UserMediaCollectionEntry.COLUMN_PLOT,
                UserMediaCollectionEntry.COLUMN_MEDIA_DATA
        };

        Cursor cursor = getContentResolver().query(UserMediaCollectionEntry.CONTENT_URI, projection,null,null,null);



        int headTitleColumnIndex = cursor.getColumnIndex(UserMediaCollectionEntry.COLUMN_HEAD_TITLE);
        int subTitleColumnIndex = cursor.getColumnIndex(UserMediaCollectionEntry.COLUMN_SUB_TITLE);
        int thumbnailColumnIndex = cursor.getColumnIndex(UserMediaCollectionEntry.COLUMN_THUMBNAIL);
        int mediaDataColumnIndex = cursor.getColumnIndex(UserMediaCollectionEntry.COLUMN_MEDIA_DATA);
        int plotColumnIndex = cursor.getColumnIndex(UserMediaCollectionEntry.COLUMN_PLOT);

        ArrayList<MediaList> mediaList = new ArrayList<MediaList>();
        while (cursor.moveToNext()){
            String currentheadTitle = cursor.getString(headTitleColumnIndex);
            String currentsubTitle = cursor.getString(subTitleColumnIndex);
            int currentThumbnail = cursor.getInt(thumbnailColumnIndex);
            String currentmediaData = cursor.getString(mediaDataColumnIndex);
            String currentplot = cursor.getString(plotColumnIndex);

            mediaList.add(new MediaList(currentheadTitle,currentsubTitle,currentThumbnail,currentmediaData,currentplot));
        }
        return mediaList;
    }
}
