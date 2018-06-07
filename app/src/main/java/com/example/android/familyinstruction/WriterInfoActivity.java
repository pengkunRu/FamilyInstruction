package com.example.android.familyinstruction;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

public class WriterInfoActivity extends AppCompatActivity {

    // 用户需要获取的作者信息
    private Content writerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer_info);

        // 获取用户想要浏览的作者信息在数据库中的唯一标识id
        Intent intent = getIntent();
        long id = intent.getExtras().getLong("id");

        writerInfo = getWriterInfo(id);

        ImageView writerImageView = (ImageView)findViewById(R.id.writer_image_view);
        writerImageView.setImageResource(getResources().getIdentifier(writerInfo.getImageResourceId(),"drawable","com.example.android.familyinstruction"));

        TextView writerNameTextView = (TextView)findViewById(R.id.writer_name);
        writerNameTextView.setText(writerInfo.getWriterName());

        TextView writerIntroTextView = (TextView)findViewById(R.id.writer_introduction);
        writerIntroTextView.setText(writerInfo.getWriterIntroduction());
    }

    /**
     * TODO 辅助函数：从文本资源表取出id=？的作者信息
     * @return
     */
    private Content getWriterInfo(long id) {
        String[] projection = {
                TextResourceEntry.COLUMN_WRITER_NAME,
                TextResourceEntry.COLUMN_WRITER_IMAGE,
                TextResourceEntry.COLUMN_WRITER_INTRODUCTION
        };

        //构造新的uri
        Uri newUri = ContentUris.withAppendedId(TextResourceEntry.CONTENT_URI,id);
        Cursor cursor = getContentResolver().query(newUri, projection,null,null,null);


        int writerNameColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_NAME);
        int writerImageColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_IMAGE);
        int writerIntroColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_INTRODUCTION);

        String currentWriterName = "";
        String currentWriterImage = "";
        String currentWriterIntro = "";
        while (cursor.moveToNext()){

            currentWriterName = cursor.getString(writerNameColumnIndex);
            currentWriterImage = cursor.getString(writerImageColumnIndex);
            currentWriterIntro = cursor.getString(writerIntroColumnIndex);
        }
        Content data = new Content(currentWriterName,currentWriterIntro,currentWriterImage,null,null,null);
        return data;
    }
}
