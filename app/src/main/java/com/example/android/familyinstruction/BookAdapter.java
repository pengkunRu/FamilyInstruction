package com.example.android.familyinstruction;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kun on 2018/6/2.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    // 构造函数
    public BookAdapter(Activity context, ArrayList<Book> books){
        super(context,0,books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        // 没有可利用的布局
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        // 获取list中position位置的 Book 对象
        Book currentItem = getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.book_image_view);
        imageView.setImageResource(currentItem.getImageResourceId());
        return listItemView;
    }
}
