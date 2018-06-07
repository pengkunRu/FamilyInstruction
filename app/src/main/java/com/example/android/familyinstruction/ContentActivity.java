package com.example.android.familyinstruction;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        // 默认只显示文言文，不现实译文
        TextView vernacularTextView = (TextView)findViewById(R.id.article_vernacular_tv);
        vernacularTextView.setVisibility(View.GONE);


        // 获取用户想要浏览的章节在数据库中的唯一标识id
        Intent intent = getIntent();
        long id = intent.getExtras().getInt("id");


        // 根据唯一标识符获取了对应的所有数据
        Content content = getContent(id);

        // 绑定视图，设置资源
        TextView typeTextView = (TextView)findViewById(R.id.article_type_text_view);
        TextView writerNameTextView = (TextView)findViewById(R.id.writer_name_text_view);
        TextView ancientTextView = (TextView)findViewById(R.id.article_ancient_tv);
        vernacularTextView = (TextView)findViewById(R.id.article_vernacular_tv);

        typeTextView.setText(content.getArticleType());
        writerNameTextView.setText(content.getWriterName());
        ancientTextView.setText(content.getArticleAncientFormat());
        vernacularTextView.setText(content.getArticleVernacularFormat());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_translate:
                if(item.getTitle() == getResources().getString(R.string.action_translate)){
                    //显示原文，译文
                    TextView vernacularTextView = (TextView)findViewById(R.id.article_vernacular_tv);
                    vernacularTextView.setVisibility(View.VISIBLE);
                    item.setTitle(getResources().getString(R.string.action_original_text));
                }else{
                    //显示原文
                    TextView vernacularTextView = (TextView)findViewById(R.id.article_vernacular_tv);
                    vernacularTextView.setVisibility(View.GONE);
                    item.setTitle(getResources().getString(R.string.action_translate));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * TODO 辅助函数：从文本资源表取出满足id=？的行
     * @return
     */
    private Content getContent(long id) {
        String[] projection = {
                TextResourceEntry.COLUMN_ARTICLE_TYPE,
                TextResourceEntry.COLUMN_WRITER_NAME,
                TextResourceEntry.COLUMN_WRITER_IMAGE,
                TextResourceEntry.COLUMN_WRITER_INTRODUCTION,
                TextResourceEntry.COLUMN_ARTICLE_ANCIENT_FORMAT,
                TextResourceEntry.COLUMN_ARTICLE_VERNACULAR_FORMAT
        };

        //构造新的uri
        Uri newUri = ContentUris.withAppendedId(TextResourceEntry.CONTENT_URI,id);

        Cursor cursor = getContentResolver().query(newUri, projection,null,null,null);


        int writerNameColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_NAME);
        int writerIntroductionColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_INTRODUCTION);
        int writerImageColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_IMAGE);
        int articleTypeColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_ARTICLE_TYPE);
        int articleAncientFormatColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_ARTICLE_ANCIENT_FORMAT);
        int articleVernacularFormatColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_ARTICLE_VERNACULAR_FORMAT);


        String writerName = "";
        String writerIntroduction = "";
        String writerImage = "";
        String articleType = "";
        String articleAncientFormat = "";
        String articleVernacularFormat = "";

        while (cursor.moveToNext()){
            writerName = cursor.getString(writerNameColumnIndex);
            writerIntroduction = cursor.getString(writerIntroductionColumnIndex);
            writerImage = cursor.getString(writerImageColumnIndex);
            articleType = cursor.getString(articleTypeColumnIndex);
            articleAncientFormat = cursor.getString(articleAncientFormatColumnIndex);
            articleVernacularFormat = cursor.getString(articleVernacularFormatColumnIndex);
        }
        return new Content(writerName,writerIntroduction,writerImage,articleType,articleAncientFormat,articleVernacularFormat);
    }
}
