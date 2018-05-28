package com.example.android.familyinstruction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;
import com.example.android.familyinstruction.data.InstructionDbHelper;

public class InstructionlogActivity extends AppCompatActivity {

    private InstructionDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionlogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new InstructionDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of the family_instruction database.
     */
    private void displayDatabaseInfo() {

        // 创建或者打开一个关联的数据库
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 定义projection
        String[] projection = {
                InstructionEntry._ID,
                InstructionEntry.COLUMN_NOTE_TYPE,
                InstructionEntry.COLUMN_NOTE_INSTRUCTION,
                InstructionEntry.COLUMN_NOTE_MEAN,
                InstructionEntry.COLUMN_NOTE_TIME
        };

        Cursor cursor = db.query(InstructionEntry.TABLE_NAME,
                projection,
                null,null,null,null,null);

        TextView displayView = (TextView) findViewById(R.id.test_tv);

        try {

            displayView.setText("The notes table contains " + cursor.getCount() + " notes.\n\n");
            displayView.append(InstructionEntry._ID + " - "  +
            InstructionEntry.COLUMN_NOTE_TYPE + " - " +
            InstructionEntry.COLUMN_NOTE_INSTRUCTION + " - " +
            InstructionEntry.COLUMN_NOTE_MEAN + " - " +
            InstructionEntry.COLUMN_NOTE_TIME + "\n");

            int idColumnIndex = cursor.getColumnIndex(InstructionEntry._ID);
            int typeColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_TYPE);
            int instructionColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_INSTRUCTION);
            int meanColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_MEAN);
            int timeColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_TIME);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentType = cursor.getString(typeColumnIndex);
                String currentInstruction = cursor.getString(instructionColumnIndex);
                String currentMean = cursor.getString(meanColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                currentType + " - " + currentInstruction + " - " +
                currentMean + " - " + currentTime));
            }
        } finally {
            // 关闭cursor,释放所有资源，使其无效
            cursor.close();
        }
    }

    // 插入虚拟数据
    private void insertNote(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(InstructionEntry.COLUMN_NOTE_TYPE,"治家");
        values.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,"夫风化者，自上而行于下者也，自先而施于后者也。");
        values.put(InstructionEntry.COLUMN_NOTE_MEAN,"教育感化这件事，是从上向下推行的，是从先向后施行影响的。");
        values.put(InstructionEntry.COLUMN_NOTE_TIME,"1533");

        long newRowId = db.insert(InstructionEntry.TABLE_NAME,null,values);
        Log.v("InstructionlogActivity","New row ID "+ newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertNote();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
