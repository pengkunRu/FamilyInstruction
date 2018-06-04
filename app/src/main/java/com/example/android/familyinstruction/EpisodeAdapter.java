package com.example.android.familyinstruction;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kun on 2018/6/4.
 */

public class EpisodeAdapter extends ArrayAdapter<Episode>{
    // 构造函数
    public EpisodeAdapter(Activity context, ArrayList<Episode> episodes){
        super(context,0,episodes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        // 没有可利用的布局
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.episode_list_item, parent, false);
        }

        // 获取list中position位置的 Book 对象
        Episode currentItem = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.episode_poster_image_view);
        imageView.setImageResource(currentItem.getPoster());
//        TextView introductionTextView = listItemView.findViewById(R.id.episode_intro_text_view);
//        introductionTextView.setText(currentItem.getIntroduction());
        TextView headTitleTextView = listItemView.findViewById(R.id.episode_headline_text_view);
        headTitleTextView.setText(currentItem.getHeadTitle());

        return listItemView;
    }
}
