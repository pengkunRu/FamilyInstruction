package com.example.android.familyinstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        // 获取用户想要浏览的章节在数据库中的唯一标识id
        Intent intent = getIntent();
        int id = intent.getExtras().getInt("id");
        Log.i("Content","id: " + id);
    }
}
