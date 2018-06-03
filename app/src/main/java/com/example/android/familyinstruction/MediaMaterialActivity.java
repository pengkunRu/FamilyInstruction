package com.example.android.familyinstruction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.MediaResourceEntry;

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
                Intent intent = new Intent(MediaMaterialActivity.this, BookShelfActivity.class);
                startActivity(intent);
                finish();
            }
        });

        displayAllInfo();
    }

    /**
     * TODO 显示整张表的所有信息
     */
    private void displayAllInfo() {
        String[] projection = {
                MediaResourceEntry._ID,
                MediaResourceEntry.COLUMN_HEADTITLE,
                MediaResourceEntry.COLUMN_INTRODUCTION,
                MediaResourceEntry.COLUMN_POSTER,
                MediaResourceEntry.COLUMN_SUBTITLE,
                MediaResourceEntry.COLUMN_THUMBNAIL,
                MediaResourceEntry.COLUMN_MEDIA_DATA
        };

        Cursor cursor = getContentResolver().query(MediaResourceEntry.CONTENT_URI, projection, null, null, null);

        TextView displayView = (TextView) findViewById(R.id.media_resource_display);

        try {

            displayView.setText("The media_resource table contains " + cursor.getCount() + " content.\n\n");
            displayView.append(MediaResourceEntry._ID + " - " +
                    MediaResourceEntry.COLUMN_HEADTITLE + " - " +
                    MediaResourceEntry.COLUMN_INTRODUCTION + " - " +
                    MediaResourceEntry.COLUMN_POSTER + " - " +
                    MediaResourceEntry.COLUMN_SUBTITLE + " - " +
                    MediaResourceEntry.COLUMN_THUMBNAIL + " - " +
                    MediaResourceEntry.COLUMN_MEDIA_DATA);

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(MediaResourceEntry._ID);
            int headTitleColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_HEADTITLE);
            int IntroductionColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_INTRODUCTION);
            int posterColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_POSTER);
            int subTitleColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_SUBTITLE);
            int mediaDataColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_MEDIA_DATA);
            int thumbnailColumnIndex = cursor.getColumnIndex(MediaResourceEntry.COLUMN_THUMBNAIL);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentheadTitle = cursor.getString(headTitleColumnIndex);
                String currentIntroduction = cursor.getString(IntroductionColumnIndex);
                String currentposter = cursor.getString(posterColumnIndex);
                String currentsubTitle = cursor.getString(subTitleColumnIndex);
                String currentthumbnail = cursor.getString(thumbnailColumnIndex);
                String currentmediaData = cursor.getString(mediaDataColumnIndex);


                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentheadTitle + "\n" +
                        currentIntroduction + "\n" +
                        currentposter + " \n " +
                        currentsubTitle + " \n " +
                        currentthumbnail + " \n " +
                        currentmediaData));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
