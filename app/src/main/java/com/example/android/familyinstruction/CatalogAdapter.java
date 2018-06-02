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
 * Created by kun on 2018/6/3.
 */

public class CatalogAdapter extends ArrayAdapter<Catalog> {
    // 构造函数
    public CatalogAdapter(Activity context, ArrayList<Catalog> catalogs){
        super(context,0,catalogs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        // 没有可利用的布局
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.catalog_list_item, parent, false);
        }

        // 获取list中position位置的 Book 对象
        Catalog currentItem = getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.catalog_text_view);
        textView.setText("· " + currentItem.getArticleType());
        return listItemView;
    }
}
