package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

;

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

//        // 转到颜氏家训
//        TextView mBook1 = (TextView) findViewById(R.id.book_1);
//        mBook1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TextMaterialActivity.this, FamilyInstructionText.class);
//                startActivity(intent);
//            }
//        });
//
//        // 转到中华家训
//        TextView mBook2 = (TextView) findViewById(R.id.book_2);
//        mBook2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TextMaterialActivity.this, FamilyInstructionVedio.class);
//                startActivity(intent);
//            }
//        });

//        theNumberOfBooks();
        displayAllInfo();
    }


    /**
     * TODO 显示我们的文本资源表中有几本书
     */
    private void theNumberOfBooks(){
        String[] projection = {
                TextResourceEntry.COLUMN_BOOK_TITLE,
        };

        Cursor cursor = getContentResolver().query(TextResourceEntry.CONTENT_URI,projection,null,null,null);

        HashSet<String> bookTitle = new HashSet<String>();

        while(cursor.moveToNext()){
            bookTitle.add(cursor.getString(0));;
        }

        Iterator<String> it = bookTitle.iterator();
        ArrayList<String> titles = new ArrayList<String>();
        while (it.hasNext()){
            String title = it.next();
            titles.add(title);
        }
//        Log.i("Text","元素个数："+titles.size());
    }
    /**
     * TODO 显示整张表的所有信息
     */
    private void displayAllInfo(){
        String[] projection = {
                TextResourceEntry._ID,
                TextResourceEntry.COLUMN_BOOK_TITLE,
                TextResourceEntry.COLUMN_BOOK_INTRODUCTION,
                TextResourceEntry.COLUMN_BOOK_IMAGE,
                TextResourceEntry.COLUMN_WRITER_NAME,
                TextResourceEntry.COLUMN_WRITER_INTRODUCTION,
                TextResourceEntry.COLUMN_WRITER_IMAGE,
                TextResourceEntry.COLUMN_ARTICLE_TYPE,
                TextResourceEntry.COLUMN_ARTICLE_ANCIENT_FORMAT,
                TextResourceEntry.COLUMN_ARTICLE_VERNACULAR_FORMAT
        };

        Cursor cursor = getContentResolver().query(TextResourceEntry.CONTENT_URI,projection,null,null,null);

        TextView displayView = (TextView) findViewById(R.id.text_resource_display);

        try {

            displayView.setText("The text_resource table contains " + cursor.getCount() + " content.\n\n");
            displayView.append(TextResourceEntry._ID + " - " +
                    TextResourceEntry.COLUMN_BOOK_TITLE + " - " +
                    TextResourceEntry.COLUMN_BOOK_INTRODUCTION + " - " +
                    TextResourceEntry.COLUMN_BOOK_IMAGE + " - " +
                    TextResourceEntry.COLUMN_WRITER_NAME + " - " +
                    TextResourceEntry.COLUMN_WRITER_INTRODUCTION + " - " +
                    TextResourceEntry.COLUMN_WRITER_IMAGE + " - " +
                    TextResourceEntry.COLUMN_ARTICLE_TYPE + " - " +
                    TextResourceEntry.COLUMN_ARTICLE_ANCIENT_FORMAT + " - " +
                    TextResourceEntry.COLUMN_ARTICLE_VERNACULAR_FORMAT + " \n "
            );

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(TextResourceEntry._ID);
            int bookTtileColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_TITLE);
            int bookIntroductionColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_INTRODUCTION);
            int bookImageColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_IMAGE);
            int writerNameColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_NAME);
            int writerIntroductionColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_INTRODUCTION);
            int writerImageColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_WRITER_IMAGE);
            int articleTypeColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_ARTICLE_TYPE);
            int articleAncientFormatColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_ARTICLE_ANCIENT_FORMAT);
            int articleVernacularFormatColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_ARTICLE_VERNACULAR_FORMAT);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentbookTitle = cursor.getString(bookTtileColumnIndex);
                String currentbookIntroduction = cursor.getString(bookIntroductionColumnIndex);
                String currentbookImage = cursor.getString(bookImageColumnIndex);
                String currentwriterName = cursor.getString(writerNameColumnIndex);
                String currentwriterIntroduction = cursor.getString(writerIntroductionColumnIndex);
                String currentwriterImage = cursor.getString(writerImageColumnIndex);
                String currentarticleType = cursor.getString(articleTypeColumnIndex);
                String currentarticleAncientFormat = cursor.getString(articleAncientFormatColumnIndex);
                String currentarticleVernacularFormat = cursor.getString(articleVernacularFormatColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentbookTitle + "\n" +
                        currentbookIntroduction + "\n" +
                        currentbookImage + " \n " +
                        currentwriterName + " \n " +
                        currentwriterIntroduction + " \n " +
                        currentwriterImage + " \n " +
                        currentarticleType + " \n " +
                        currentarticleAncientFormat + " \n " +
                        currentarticleVernacularFormat));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}

