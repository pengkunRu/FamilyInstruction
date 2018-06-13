package com.example.android.familyinstruction;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;
import com.example.android.familyinstruction.data.InstructionContract.UserInfoEntry;

public class ReviewActivity extends AppCompatActivity {

    // 数据源
    private Cursor mData;

    // 家训应用当前状态
    private int mCurrentState;

    // 家训和家训注译在cursor(数据源)中的索引
    private int mTranslationCol, mInstructionCol;

    private TextView mInstructionTextView, mTranslationTextView;
    private Button mButton;

    // 隐藏家训注译的标志
    private final int STATE_HIDDEN = 0;

    // 显示家训注译的标志
    private final int STATE_SHOWN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // 判断用户是否登陆
        if(getUserStatus()==0){
            Intent intent = new Intent(ReviewActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        mInstructionTextView = (TextView) findViewById(R.id.text_view_instruction);
        mTranslationTextView = (TextView) findViewById(R.id.text_view_translation);
        mButton = (Button) findViewById(R.id.button_next);

        //Run the database operation to get the cursor off of the main thread
        new WordFetchTask().execute();
    }

    // 按钮的点击事件
    public void onButtonClick(View view) {
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    public void nextWord() {
        // COMPLETED (3) Go to the next word in the Cursor, show the next word and hide the definition
        // Note that you shouldn't try to do this if the cursor hasn't been set yet.
        // If you reach the end of the list of words, you should start at the beginning again.

        if (mData != null) {
            // Move to the next position in the cursor, if there isn't one, move to the first
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }
            // Hide the definition TextView
            mTranslationTextView.setVisibility(View.INVISIBLE);

            // Change button text
            mButton.setText(getString(R.string.action_show_translation));

            // Get the next word
            mInstructionTextView.setText(mData.getString(mInstructionCol));
            mTranslationTextView.setText(mData.getString(mTranslationCol));

            mCurrentState = STATE_HIDDEN;
        }
    }

    public void showDefinition() {
        if (mData != null) {
            mTranslationTextView.setVisibility(View.VISIBLE);
            mButton.setText(getString(R.string.next_instruction));
            mCurrentState = STATE_SHOWN;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mData.close();
    }

    // 异步线程操作加载数据
    public class WordFetchTask extends AsyncTask<Void, Void, Cursor> {

        // Invoked on a background thread
        @Override
        protected Cursor doInBackground(Void... params) {
            ContentResolver resolver = getContentResolver();

            String[] projection = {
                    InstructionEntry.COLUMN_NOTE_INSTRUCTION,
                    InstructionEntry.COLUMN_NOTE_DESCRIPTION
            };
            Cursor cursor = resolver.query(InstructionEntry.CONTENT_URI, projection, null, null, null);
            return cursor;
        }


        // Invoked on UI thread
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            // Initialize anything that you need the cursor for, such as setting up
            // the screen with the first word and setting any other instance variables

            //Set up a bunch of instance variables based off of the data

            // Set the data for MainActivity
            mData = cursor;
            // Get the column index, in the Cursor, of each piece of data
            mTranslationCol = mData.getColumnIndex(InstructionEntry.COLUMN_NOTE_DESCRIPTION);
            mInstructionCol = mData.getColumnIndex(InstructionEntry.COLUMN_NOTE_INSTRUCTION);

            // Set the initial state
            nextWord();
        }
    }

    // TODO 辅助函数 获取用户登陆状态
    private int getUserStatus(){
        String[] projection = {
                UserInfoEntry.COLUMN_USER_STATUS
        };

        Cursor cursor = getContentResolver().query(UserInfoEntry.CONTENT_URI, projection,null,null,null);

        int userStatus = -1;
        int userStatusColumnIndex = cursor.getColumnIndex(UserInfoEntry.COLUMN_USER_STATUS);
        while (cursor.moveToNext()){
            userStatus = cursor.getInt(userStatusColumnIndex);
        }
        Log.i("Note","用户登录状态："+userStatus);
        return userStatus;
    }
}
