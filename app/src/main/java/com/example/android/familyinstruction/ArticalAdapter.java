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
 * Created by kun on 2018/5/29.
 */

public class ArticalAdapter extends ArrayAdapter<ArticalMaterial> {

    public ArticalAdapter(Activity context, ArrayList<ArticalMaterial> articles) {
        super(context,0,articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        // 没有可利用的布局
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_instruction, parent, false);
        }

        // 获取list中position位置的 ArticalMaterial 对象
        ArticalMaterial currentArticle = getItem(position);

        TextView titleTextView = (TextView)listItemView.findViewById(R.id.headline_text_view);
        titleTextView.setText(currentArticle.getTitle());

        TextView typeTextView = (TextView)listItemView.findViewById(R.id.subtitle_text_view);
        typeTextView.setText(currentArticle.getType());

        return listItemView;
    }
}
