package com.example.android.familyinstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        // 转到(札记)
        TextView mNoteTextView = (TextView) findViewById(R.id.note);
        mNoteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MaterialActivity.this, InstructionlogActivity.class);
                startActivity(intent);
            }
        });

        // 转到颜氏家训
        TextView mBook1 = (TextView) findViewById(R.id.book_1);
        mBook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MaterialActivity.this, FamilyInstructionText.class);
                startActivity(intent);
            }
        });
    }
}

