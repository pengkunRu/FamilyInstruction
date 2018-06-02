package com.example.android.familyinstruction;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

import java.util.ArrayList;

public class BookShelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);


        // 获取数据源
        ArrayList<Book> books = getBooks();

        BookAdapter adapter = new BookAdapter(this,books);

        GridView gridView = (GridView)findViewById(R.id.book_shelf_list);

        gridView.setAdapter(adapter);
    }

    /**
     * TODO 辅助函数：从文本资源表中取出去重的书名，封面图片信息
     * @return
     */
    private ArrayList<Book> getBooks() {
        String[] projection = {
                "distinct " + TextResourceEntry.COLUMN_BOOK_TITLE,
                TextResourceEntry.COLUMN_BOOK_IMAGE
        };

        Cursor cursor = getContentResolver().query(TextResourceEntry.CONTENT_URI, projection, null, null, null);

        int bookTtileColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_TITLE);
        int bookImageColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_IMAGE);

        ArrayList<Book> books = new ArrayList<Book>();
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
