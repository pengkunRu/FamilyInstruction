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

        // 构建了InstructionDbHelper类的实例
        InstructionDbHelper mDbHelper = new InstructionDbHelper(this);

        // 创建或者打开一个关联的数据库
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + InstructionEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.test_tv);
            displayView.setText("Number of rows in notes database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
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
