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

public class MediaListAdapter extends ArrayAdapter<MediaList> {

    // 构造函数
    public MediaListAdapter(Activity context, ArrayList<MediaList> mediaLists){
        super(context,0,mediaLists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        // 没有可利用的布局
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.media_list_item, parent, false);
        }

        // 获取list中position位置的 MediaList 对象
        MediaList currentItem = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.vedio_thumbnail_image_view);
        imageView.setImageResource(currentItem.getThumbnail());
        TextView headTitle = listItemView.findViewById(R.id.vedio_headline_text_view);
        headTitle.setText(currentItem.getHeadtitle());
        TextView subTitle = listItemView.findViewById(R.id.vedio_subtitle_text_view);
        subTitle.setText(currentItem.getSubtitle());
        return listItemView;
    }
}
