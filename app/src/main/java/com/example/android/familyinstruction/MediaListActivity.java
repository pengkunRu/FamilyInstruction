package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.MediaResourceEntry;

import java.util.ArrayList;

public class MediaListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);

        // 获取用户想要观看的视频集标题
        Intent intent = getIntent();
        String headTitle = intent.getExtras().getString("headTitle");
        String introduction = intent.getExtras().getString("introduction");

        // 获取数据源
        // 在创建目录数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // onItemClick方法中引用medialist arraylist了
        final ArrayList<MediaList> mediaList = getMediaList(headTitle);

        // 设置视频集的简介
        TextView introductionTextView = findViewById(R.id.vedio_intro_text_view);
        introductionTextView.setText("简介:   " + introduction);


        MediaListAdapter adapter = new MediaListAdapter(this,mediaList);
        ListView listView = (ListView)findViewById(R.id.vedio_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 由此进入视频的内容
                // 通过position，我们可以从medialist数组列表中获取medialist对象
                MediaList currentItem = mediaList.get(position);

                // 页面跳转至视频播放界面,并传入视频链接
                Intent intent = new Intent(MediaListActivity.this,MediaPlayBack.class);
                intent.putExtra("vedioUrl",currentItem.getMediaData());
                intent.putExtra("plot",currentItem.getPlot());
                startActivity(intent);
            }
        });
    }


    /**
     * TODO 辅助函数：从文本资源表取出所有headTitle=？的行
     * @return
     */
    private ArrayList<MediaList> getMediaList(String headTitle) {
        String[] projection = {
                MediaResourceEntry.COLUMN_HEADTITLE,
                MediaResourceEntry.COLUMN_SUBTITLE,
                MediaResourceEntry.COLUMN_THUMBNAIL,
                MediaResourceEntry.COLUMN_MEDIA_DATA,
                MediaResourceEntry.COLUMN_PLOT

        };

        // 设置第2，3参数，来获取我们想要的数据
        String selection = MediaResourceEntry.COLUMN_HEADTITLE + "=?";
        String[] selectionArgs = new String[]{headTitle};

        Cursor cursor = getContentResolver().query(MediaResourceEntry.CONTENT_URI, projection,selection,selectionArgs,null);



        int headTitleColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_HEADTITLE);
        int subTitleColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_SUBTITLE);
        int thumbnailColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_THUMBNAIL);
        int mediaDataColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_MEDIA_DATA);
        int plotColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_PLOT);

        ArrayList<MediaList> mediaList = new ArrayList<MediaList>();
        while (cursor.moveToNext()){
            String currentheadTitle = cursor.getString(headTitleColumnIndex);
            String currentsubTitle = cursor.getString(subTitleColumnIndex);
            String currentThumbnail = cursor.getString(thumbnailColumnIndex);
            String currentmediaData = cursor.getString(mediaDataColumnIndex);
            String currentplot = cursor.getString(plotColumnIndex);

            // 根据图片的名字获得drawable目录下的图片资源
            int imageResourceId = getResources().getIdentifier(currentThumbnail,"drawable","com.example.android.familyinstruction");

            mediaList.add(new MediaList(currentheadTitle,currentsubTitle,imageResourceId,currentmediaData,currentplot));
        }

        return mediaList;
    }
}
