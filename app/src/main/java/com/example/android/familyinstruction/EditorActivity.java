package com.example.android.familyinstruction;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;

public class EditorActivity extends AppCompatActivity {

    private EditText mTypeEditText;
    private EditText mInstructionEditText;
    private EditText mMeanEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mTypeEditText = (EditText)findViewById(R.id.instruction_type_et);
        mInstructionEditText = (EditText)findViewById(R.id.instruction_et);
        mMeanEditText = (EditText)findViewById(R.id.instruction_mean_tv);

    }

    private void insertNote(){
        // 读取三个EditText字段中的数据
        String mTypeString = mTypeEditText.getText().toString().trim();
        String mInstructionString = mInstructionEditText.getText().toString().trim();
        String mMeanString = mMeanEditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(InstructionEntry.COLUMN_NOTE_TYPE,mTypeString);
        values.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,mInstructionString);
        values.put(InstructionEntry.COLUMN_NOTE_MEAN,mMeanString);
        values.put(InstructionEntry.COLUMN_NOTE_TIME,0);

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
}
