package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

import java.util.ArrayList;

public class BookShelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);


        /**
         * 初始化导航栏信息
         * 当前界面按钮为红色，其余为白色
         * 当前界面按钮文字为白色，其余为红色
         */
        Button mNoteMaterial = findViewById(R.id.note_material_button2);
        Button mTextMaterial = findViewById(R.id.text_material_button2);
        Button mMediaMaterial = findViewById(R.id.media_material_button2);
        mNoteMaterial.setBackgroundColor(getResources().getColor(R.color.white));
        mNoteMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTextMaterial.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTextMaterial.setTextColor(getResources().getColor(R.color.white));
        mMediaMaterial.setBackgroundColor(getResources().getColor(R.color.white));
        mMediaMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));

        // 界面导航到用户札记界面
        mNoteMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookShelfActivity.this, NoteMaterialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 界面导航到媒体资料界面
        mMediaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookShelfActivity.this, EpisodeActivity.class);
                startActivity(intent);
                finish();
            }
        });




        // 获取数据源
        // 在创建书籍数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // onItemClick方法中引用books arraylist了
        final ArrayList<Book> books = getBooks();

        BookAdapter adapter = new BookAdapter(this,books);

        GridView gridView = (GridView)findViewById(R.id.book_shelf_list);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 通过position，我们可以从Book数组列表中获取Book对象
                Book currentBook = books.get(position);

                // 页面跳转到用户想要阅读的书籍（书籍目录界面）
                Intent intent = new Intent(BookShelfActivity.this,CatalogActivity.class);
                intent.putExtra("bookTitle",currentBook.getBookTitle());
                startActivity(intent);
            }
        });
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
