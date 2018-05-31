package com.example.android.familyinstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 文本资料界面
 */
public class TextMaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_material);

        /**
         * 初始化导航栏信息
         * 当前界面按钮为红色，其余为白色
         * 当前界面按钮文字为白色，其余为红色
         */
        Button mNoteMaterial = findViewById(R.id.note_material_button2);
        Button mTextMaterial = findViewById(R.id.text_material_button2);
        Button mMediaMaterial = findViewById(R.id.media_material_button2);
        mNoteMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mNoteMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTextMaterial.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTextMaterial.setTextColor(getResources().getColor(R.color.navigationColor));
        mMediaMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mMediaMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));

        // 界面导航到用户札记界面
        mNoteMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TextMaterialActivity.this, NoteMaterialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 界面导航到媒体资料界面
        mMediaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TextMaterialActivity.this, MediaMaterialActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // 转到颜氏家训
        TextView mBook1 = (TextView) findViewById(R.id.book_1);
        mBook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TextMaterialActivity.this, FamilyInstructionText.class);
                startActivity(intent);
            }
        });

        // 转到中华家训
        TextView mBook2 = (TextView) findViewById(R.id.book_2);
        mBook2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TextMaterialActivity.this, FamilyInstructionVedio.class);
                startActivity(intent);
            }
        });
    }
}

