package com.example.android.familyinstruction;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        // 获取用户想要浏览的章节在数据库中的唯一标识id
        Intent intent = getIntent();
        long id = intent.getExtras().getInt("id");
        Log.i("Content","id: " + id);

        Content content = getContent(id);
        Log.i("ContentActivity","值: " +content.getArticleType()+content.getImageResourceId()+content.getWriterIntroduction()+content.getWriterName());
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
