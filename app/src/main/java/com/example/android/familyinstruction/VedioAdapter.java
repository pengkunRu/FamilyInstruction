package com.example.android.familyinstruction;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kun on 2018/5/30.
 */

public class VedioAdapter extends ArrayAdapter<VedioMaterial>{

    public VedioAdapter(Activity context, ArrayList<VedioMaterial> vedios) {
        super(context,0,vedios);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        // 没有可利用的布局
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_instruction, parent, false);
        }

        // 获取list中position位置的 VedioMaterial 对象
        VedioMaterial currentItem = getItem(position);

        TextView headline_tv = (TextView)listItemView.findViewById(R.id.headline_text_view);
        headline_tv.setText(currentItem.getHeadline());

        TextView subtitle_tv = (TextView)listItemView.findViewById(R.id.subtitle_text_view);
        subtitle_tv.setText(currentItem.getSubtitle());

        return listItemView;
    }
}
