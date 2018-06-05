package com.example.android.familyinstruction;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // 用户添加的家训的重要程度
    private int mPriority;

    // 定义加载器
    private static final int EXISTING_NOTE_LOADER = 0;

    private Uri mCurrentNoteUri;

    private EditText mTitleEditText;
    private EditText mInstructionContentEditText;
    private EditText mJusticeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // 获取Intent
        Intent intent = getIntent();
        mCurrentNoteUri = intent.getData();

        if(mCurrentNoteUri == null){
            setTitle("添加家训");
        }else{
            setTitle("编辑家训");
            getLoaderManager().initLoader(EXISTING_NOTE_LOADER, null, this);
        }

        // 用户编辑的家训的重要程度默认设置为1
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;

        mTitleEditText = (EditText)findViewById(R.id.instruction_title_edit_text);
        mInstructionContentEditText = (EditText)findViewById(R.id.instruction_content_edit_text);
        mJusticeEditText = (EditText)findViewById(R.id.instruction_justice_edit_text);
    }

    private void insertNote(){
        // 读取三个EditText字段中的数据
        String mTitleString = mTitleEditText.getText().toString().trim();
        String mInstructionString = mInstructionContentEditText.getText().toString().trim();
        String mDescription = mJusticeEditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(InstructionEntry.COLUMN_NOTE_TITLE,mTitleString);
        values.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,mInstructionString);
        values.put(InstructionEntry.COLUMN_NOTE_JUSTICE,mPriority);
        values.put(InstructionEntry.COLUMN_NOTE_DESCRIPTION,mDescription);


        // Insert a new note into the provider
        // returning the content URI for the new note.
        Uri newUri = getContentResolver().insert(InstructionEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_save:
                // 插入数据
                insertNote();
                // 关闭当前Activity,并返回之前的Activity
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据用户的选择，改变用户想要添加的家训的优先级
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                InstructionEntry._ID,
                InstructionEntry.COLUMN_NOTE_TITLE,
                InstructionEntry.COLUMN_NOTE_INSTRUCTION,
                InstructionEntry.COLUMN_NOTE_JUSTICE,
                InstructionEntry.COLUMN_NOTE_DESCRIPTION
        };

        return new CursorLoader(this,
                mCurrentNoteUri,
                projection,
                null,
                null,
                null);
    }

    /**
     * 当数据加载到游标后，onLoadFinished() 将被调用。
     * 在这里，我首先要将游标移到第一个项的位置。尽管它只有一个项，并从位置 -1 开始。
     * @param loader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            // 获得每个数据项的索引
            int typeColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_TITLE);
            int instructionColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_INSTRUCTION);
            int meanColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_DESCRIPTION);
            int priorityColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_JUSTICE);

            String type = cursor.getString(typeColumnIndex);
            String instruction = cursor.getString(instructionColumnIndex);
            int priority = cursor.getInt(priorityColumnIndex);
            String description = cursor.getString(meanColumnIndex);

            mTitleEditText.setText(type);
            mInstructionContentEditText.setText(instruction);
            mJusticeEditText.setText(description);
            if(priority == 1){
                ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
            } else if(priority == 2){
                ((RadioButton) findViewById(R.id.radButton2)).setChecked(true);
            }else if(priority == 3){
                ((RadioButton) findViewById(R.id.radButton3)).setChecked(true);
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTitleEditText.setText("");
        mInstructionContentEditText.setText("");
        mJusticeEditText.setText("");
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
    }
}
