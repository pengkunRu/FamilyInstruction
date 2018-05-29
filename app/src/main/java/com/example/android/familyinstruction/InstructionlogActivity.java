package com.example.android.familyinstruction;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.familyinstruction.data.InstructionContract;
import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;

public class InstructionlogActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    // 创建一个整数加载器常量
    private static final int NOTE_LOADER = 0;

    // ListView的适配器
    InstructionCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionlogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView noteListView = (ListView) findViewById(R.id.list);

        // 配置适配器
        mCursorAdapter = new InstructionCursorAdapter(this,null);
        noteListView.setAdapter(mCursorAdapter);

        // 为ListView容器里的每一项设置点击事件
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(InstructionlogActivity.this,EditorActivity.class);

                Uri currentNoteUri = ContentUris.withAppendedId(InstructionEntry.CONTENT_URI,id);
                intent.setData(currentNoteUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(NOTE_LOADER,null,this);
    }

    // 插入虚拟数据
    private void insertNote(){
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
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                InstructionEntry._ID,
                InstructionEntry.COLUMN_NOTE_TYPE,
                InstructionEntry.COLUMN_NOTE_INSTRUCTION};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                InstructionContract.InstructionEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
