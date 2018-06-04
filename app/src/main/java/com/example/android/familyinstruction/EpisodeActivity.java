package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.familyinstruction.data.InstructionContract.MediaResourceEntry;

import java.util.ArrayList;

/**
 * 媒体资料界面
 */
public class EpisodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        /**
         * 初始化导航栏信息
         * 当前界面按钮为红色，其余为白色
         */
        Button mNoteMaterial = findViewById(R.id.note_material_button3);
        Button mTextMaterial = findViewById(R.id.text_material_button3);
        Button mMediaMaterial = findViewById(R.id.media_material_button3);
        mNoteMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mNoteMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTextMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mTextMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mMediaMaterial.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mMediaMaterial.setTextColor(getResources().getColor(R.color.navigationColor));


        // 界面导航到用户札记界面
        mNoteMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EpisodeActivity.this, NoteMaterialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 界面导航到文本资料界面
        mTextMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EpisodeActivity.this, BookShelfActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        displayAllInfo();

        // 获取数据源
        // 在创建视频集数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // episode arraylist了
        final ArrayList<Episode> episodes = getEpisode();

        EpisodeAdapter adapter = new EpisodeAdapter(this,episodes);

        ListView listView = (ListView)findViewById(R.id.episode_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 通过position，我们可以从Episode数组列表中获取episode对象
                Episode currentEpisode = episodes.get(position);
                Intent intent = new Intent(EpisodeActivity.this,MediaListActivity.class);
                intent.putExtra("headTitle",currentEpisode.getHeadTitle());
                intent.putExtra("introduction",currentEpisode.getIntroduction());
                startActivity(intent);
            }
        });
    }

    /**
     * TODO 辅助函数：从文本资源表中取出去重的视频集名，封面图片信息,剧集简介
     * @return
     */
    private ArrayList<Episode> getEpisode() {
        String[] projection = {
                "distinct " + MediaResourceEntry.COLUMN_HEADTITLE,
                MediaResourceEntry.COLUMN_INTRODUCTION,
                MediaResourceEntry.COLUMN_POSTER
        };

        Cursor cursor = getContentResolver().query(MediaResourceEntry.CONTENT_URI, projection, null, null, null);

        int headTitleColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_HEADTITLE);
        int IntroductionColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_INTRODUCTION);
        int posterColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_POSTER);


        final ArrayList<Episode> episodes = new ArrayList<Episode>();
        while (cursor.moveToNext()){
            String currentheadTitle = cursor.getString(headTitleColumnIndex);
            String currentIntroduction = cursor.getString(IntroductionColumnIndex);
            String currentPoster = cursor.getString(posterColumnIndex);

            // 根据图片的名字获得drawable目录下的图片资源
            int imageResourceId = getResources().getIdentifier(currentPoster,"drawable","com.example.android.familyinstruction");
            episodes.add(new Episode(currentheadTitle,imageResourceId,currentIntroduction));
        }
        return episodes;
    }
}
