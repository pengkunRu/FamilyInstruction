package com.example.android.familyinstruction;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;
import com.example.android.familyinstruction.data.InstructionDbHelper;

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

//        DateFormat datum = new SimpleDateFormat("MMM dd yyyy, h:mm");
//        String date = datum.format(Calendar.getInstance().getTime());
//        int mTime = Integer.parseInt(date);

        InstructionDbHelper mDbHelper = new InstructionDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(InstructionEntry.COLUMN_NOTE_TYPE,mTypeString);
        values.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,mInstructionString);
        values.put(InstructionEntry.COLUMN_NOTE_MEAN,mMeanString);
        values.put(InstructionEntry.COLUMN_NOTE_TIME,"1533");

        long newRowId = db.insert(InstructionEntry.TABLE_NAME,null,values);

        if(newRowId == -1) {
            // 数据插入失败
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        }else{
            // 数据插入成功
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
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
