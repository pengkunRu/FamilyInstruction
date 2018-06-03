package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

import java.util.ArrayList;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 获取用户想要浏览的书名
        Intent intent = getIntent();
        String bookTitle = intent.getExtras().getString("bookTitle");


        // 获取数据源
        // 在创建目录数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // onItemClick方法中引用catalogs arraylist了
        final ArrayList<Catalog> catalogs = getCataLog(bookTitle);


        // 设置书名和书籍简介
        TextView bookTitleTextView = (TextView)findViewById(R.id.book_title_text_view);
        TextView bookIntroTextView = (TextView)findViewById(R.id.book_intro_text_view);
        bookTitleTextView.setText(catalogs.get(0).getBookTitle());
        bookIntroTextView.setText("  " + catalogs.get(0).getBookInstruction());


        CatalogAdapter adapter = new CatalogAdapter(this,catalogs);
        GridView gridView = (GridView)findViewById(R.id.catalog_list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 由此进入文章的内容
                // 通过position，我们可以从Catalog数组列表中获取Catalog对象
                Catalog currentItem = catalogs.get(position);

                // 页面跳转到用户想要阅读的章节（书籍目录界面）
                Intent intent = new Intent(CatalogActivity.this,ContentActivity.class);
                intent.putExtra("id",currentItem.getRowId());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * TODO 辅助函数：从文本资源表取出所有bookTitle=？的行
     * @return
     */
    private ArrayList<Catalog> getCataLog(String bookTitle) {
        String[] projection = {
                TextResourceEntry._ID,
                TextResourceEntry.COLUMN_BOOK_TITLE,
                TextResourceEntry.COLUMN_BOOK_INTRODUCTION,
                TextResourceEntry.COLUMN_ARTICLE_TYPE
        };

        // 设置第2，3参数，来获取我们想要的数据
        String selection = TextResourceEntry.COLUMN_BOOK_TITLE + "=?";
        String[] selectionArgs = new String[]{bookTitle};

        Cursor cursor = getContentResolver().query(TextResourceEntry.CONTENT_URI, projection,selection,selectionArgs,null);


        int idColumnIndex = cursor.getColumnIndex(TextResourceEntry._ID);
        int bookTtileColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_TITLE);
        int bookIntroductionColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_INTRODUCTION);
        int articleTypeColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_ARTICLE_TYPE);

        ArrayList<Catalog> catalogs = new ArrayList<Catalog>();
        while (cursor.moveToNext()){
            int currentbookId = cursor.getInt(idColumnIndex);
            String currentbookTitle = cursor.getString(bookTtileColumnIndex);
            String currentbookIntroduction = cursor.getString(bookIntroductionColumnIndex);
            String currentArticleType = cursor.getString(articleTypeColumnIndex);

            catalogs.add(new Catalog(currentbookId,currentbookTitle,currentbookIntroduction,currentArticleType));
        }

        return catalogs;
    }
}
