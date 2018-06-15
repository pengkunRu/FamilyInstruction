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
import android.widget.GridView;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

import java.util.ArrayList;

/**
 * Created by kun on 2018/6/13.
 */

public class BooksFragment extends Fragment{

    View view;
    //构造函数
    public BooksFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.books_fragment,container,false);

        // 获取数据源
        // 在创建书籍数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // onItemClick方法中引用books arraylist了
        final ArrayList<Book> books = getBooks();

        BookAdapter adapter = new BookAdapter(getActivity(),books);

        GridView gridView = (GridView) view.findViewById(R.id.book_shelf_list_test);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 通过position，我们可以从Book数组列表中获取Book对象
                Book currentBook = books.get(position);

                // 页面跳转到用户想要阅读的书籍（书籍目录界面）
                Intent intent = new Intent(getActivity(),CatalogActivity.class);
                intent.putExtra("bookTitle",currentBook.getBookTitle());
                intent.putExtra("bookImageResourceId",currentBook.getImageResourceId());

                startActivity(intent);
            }
        });

        return view;
    }

    // TODO 辅助函数：从文本资源表中取出去重的书名，封面图片信息
    private ArrayList<Book> getBooks() {
        String[] projection = {
                "distinct " + TextResourceEntry.COLUMN_BOOK_TITLE,
                TextResourceEntry.COLUMN_BOOK_IMAGE
        };

        Cursor cursor = getActivity().getContentResolver().query(TextResourceEntry.CONTENT_URI, projection, null, null, null);

        int bookTtileColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_TITLE);
        int bookImageColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_IMAGE);

        final ArrayList<Book> books = new ArrayList<Book>();
        while (cursor.moveToNext()){
            String currentbookTitle = cursor.getString(bookTtileColumnIndex);
            String currentbookImage = cursor.getString(bookImageColumnIndex);

            // 根据图片的名字获得drawable目录下的图片资源
            int imageResourceId = getResources().getIdentifier(currentbookImage,"drawable","com.example.android.familyinstruction");
            books.add(new Book(currentbookTitle,imageResourceId));
        }

        return books;
    }
}

