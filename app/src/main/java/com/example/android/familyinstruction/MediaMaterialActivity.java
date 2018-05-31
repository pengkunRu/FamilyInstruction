package com.example.android.familyinstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 媒体资料界面
 */
public class MediaMaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_material);

        /**
         * 初始化导航栏信息
         * 当前界面按钮为红色，其余为白色
         */
        Button mNoteMaterial = findViewById(R.id.note_material_button3);
        Button mTextMaterial = findViewById(R.id.text_material_button3);
        Button mMediaMaterial = findViewById(R.id.media_material_button3);
        mNoteMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mNoteMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTextMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mTextMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mMediaMaterial.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mMediaMaterial.setTextColor(getResources().getColor(R.color.navigationColor));



        // 界面导航到用户札记界面
        mNoteMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaMaterialActivity.this, NoteMaterialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 界面导航到文本资料界面
        mTextMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaMaterialActivity.this, TextMaterialActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
