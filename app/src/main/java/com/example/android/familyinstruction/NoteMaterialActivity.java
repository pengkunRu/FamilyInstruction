package com.example.android.familyinstruction;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.familyinstruction.data.InstructionContract;
import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;
import com.example.android.familyinstruction.data.InstructionDbHelper;

/**
 * 用户札记界面
 */

public class NoteMaterialActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    // 创建一个整数加载器常量
    private static final int NOTE_LOADER = 0;

    // ListView的适配器
    InstructionCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_material);

        /**
         * 初始化导航栏信息
         * 当前界面按钮为红色，其余为白色
         * 当前界面按钮文字为白色，其余为红色
         */
        Button mNoteMaterial = findViewById(R.id.note_material_button1);
        Button mTextMaterial = findViewById(R.id.text_material_button1);
        Button mMediaMaterial = findViewById(R.id.media_material_button1);
        mNoteMaterial.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mNoteMaterial.setTextColor(getResources().getColor(R.color.navigationColor));
        mTextMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mTextMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mMediaMaterial.setBackgroundColor(getResources().getColor(R.color.navigationColor));
        mMediaMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));


        
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteMaterialActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // 界面跳转到文本资料界面
        mTextMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteMaterialActivity.this,TextMaterialActivity.class);
                startActivity(intent) ;
                finish();
            }
        });

        // 界面跳转到媒体资料界面
        mMediaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteMaterialActivity.this,MediaMaterialActivity.class);
                startActivity(intent) ;
                finish();
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
                Intent intent = new Intent(NoteMaterialActivity.this,EditorActivity.class);

                Uri currentNoteUri = ContentUris.withAppendedId(InstructionEntry.CONTENT_URI,id);
                intent.setData(currentNoteUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(NOTE_LOADER,null,this);
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
                InstructionDbHelper mDbHelper = new InstructionDbHelper(this);
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + InstructionContract.TextResourceEntry.TABLE_NAME, null);
                try {
                    Log.i("NoteMaterialActivity","Number of rows in pets database table: " + cursor.getColumnCount());
                } finally {
                    cursor.close();
                }
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
                InstructionEntry.COLUMN_NOTE_TITLE,
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
