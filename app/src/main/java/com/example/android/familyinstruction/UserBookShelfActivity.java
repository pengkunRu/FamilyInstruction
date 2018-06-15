package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.familyinstruction.data.InstructionContract.UserBookShelfEntry;

import java.util.ArrayList;

public class UserBookShelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_shelf);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获取数据源
        // 在创建书籍数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // onItemClick方法中引用books arraylist了
        final ArrayList<Book> books = getBooks();

        BookAdapter adapter = new BookAdapter(this,books);

        GridView gridView = (GridView)findViewById(R.id.user_book_shelf);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 通过position，我们可以从Book数组列表中获取Book对象
                Book currentBook = books.get(position);

                // 页面跳转到用户想要阅读的书籍（书籍目录界面）
                Intent intent = new Intent(UserBookShelfActivity.this,CatalogActivity.class);
                intent.putExtra("bookTitle",currentBook.getBookTitle());
                startActivity(intent);
            }
        });
    }

    // TODO 辅助函数：从用户书架表中取出去书名，封面图片信息
    private ArrayList<Book> getBooks() {
        String[] projection = {
                UserBookShelfEntry.COLUMN_BOOK_TITLE,
                UserBookShelfEntry.COLUMN_BOOK_IMAGE
        };

        Cursor cursor = getContentResolver().query(UserBookShelfEntry.CONTENT_URI, projection, null, null, null);

        int bookTtileColumnIndex = cursor.getColumnIndex(UserBookShelfEntry.COLUMN_BOOK_TITLE);
        int bookImageColumnIndex = cursor.getColumnIndex(UserBookShelfEntry.COLUMN_BOOK_IMAGE);

        final ArrayList<Book> books = new ArrayList<Book>();
        while (cursor.moveToNext()){
            String currentbookTitle = cursor.getString(bookTtileColumnIndex);
            int imageResourceId = cursor.getInt(bookImageColumnIndex);
            books.add(new Book(currentbookTitle,imageResourceId));
        }
        cursor.close();
        return books;
    }
}
