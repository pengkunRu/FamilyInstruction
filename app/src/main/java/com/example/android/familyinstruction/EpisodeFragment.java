package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.familyinstruction.data.InstructionContract.MediaResourceEntry;

import java.util.ArrayList;

/**
 * Created by kun on 2018/6/13.
 */

public class EpisodeFragment extends Fragment {
    View view;
    //构造函数
    public EpisodeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.explore_fragment,container,false);

        // 获取数据源
        // 在创建视频集数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // episode arraylist了
        final ArrayList<Episode> episodes = getEpisode();

        EpisodeAdapter adapter = new EpisodeAdapter(getActivity(),episodes);

        ListView listView = (ListView)view.findViewById(R.id.episode_list_test);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 通过position，我们可以从Episode数组列表中获取episode对象
                Episode currentEpisode = episodes.get(position);
                Intent intent = new Intent(getActivity(),MediaListActivity.class);
                intent.putExtra("headTitle",currentEpisode.getHeadTitle());
                intent.putExtra("introduction",currentEpisode.getIntroduction());
                startActivity(intent);
            }
        });
        return view;
    }

    // TODO 辅助函数：从文本资源表中取出去重的视频集名，封面图片信息,剧集简介
    private ArrayList<Episode> getEpisode() {
        String[] projection = {
                "distinct " + MediaResourceEntry.COLUMN_HEADTITLE,
                MediaResourceEntry.COLUMN_INTRODUCTION,
                MediaResourceEntry.COLUMN_POSTER
        };

        Cursor cursor = getActivity().getContentResolver().query(MediaResourceEntry.CONTENT_URI, projection, null, null, null);

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
