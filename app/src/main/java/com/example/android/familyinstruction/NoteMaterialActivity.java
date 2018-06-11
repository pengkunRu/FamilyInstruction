package com.example.android.familyinstruction;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.familyinstruction.data.InstructionContract;
import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;
import com.example.android.familyinstruction.data.InstructionContract.MediaResourceEntry;
import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;
import com.example.android.familyinstruction.data.InstructionContract.UserInfoEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

;

/**
 * 用户家训收藏界面
 */

public class NoteMaterialActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> , NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private static final String INSTRUCTION_SHARE_HASHTAG = " #FamilyInstructionApp";

    private StringBuilder mFamilyInstruction;

    // 创建一个整数加载器常量
    private static final int NOTE_LOADER = 0;

    // ListView的适配器
    InstructionCursorAdapter mCursorAdapter;

    // 用户名
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_material);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
        // 引入头布局和菜单布局
        mNavigationView.inflateHeaderView(R.layout.header);
        mNavigationView.inflateMenu(R.menu.menu_drawer);
        // 获取头部布局
        View NavHeaderView = mNavigationView.getHeaderView(0);
        // 获取菜单布局
        Menu menu = mNavigationView.getMenu();

        ImageView headerImageView = (ImageView)NavHeaderView.findViewById(R.id.user_photo_header);
        TextView headerUserName = (TextView)NavHeaderView.findViewById(R.id.user_name_header);
        if(getUserStatus()==0){
            headerImageView.setImageResource(R.drawable.ic_logout_photo);
            headerUserName.setText("");
            menu.findItem(R.id.logout).setTitle("登陆");
        }else {
            headerImageView.setImageResource(R.drawable.ic_login_photo);
            headerUserName.setText(getUserName());
            menu.findItem(R.id.logout).setTitle("退出登录");
        }

        /**
         * 初始化导航栏信息
         * 当前界面按钮为红色，其余为白色
         * 当前界面按钮文字为白色，其余为红色
         */
        Button mNoteMaterial = findViewById(R.id.note_material_button1);
        Button mTextMaterial = findViewById(R.id.text_material_button1);
        Button mMediaMaterial = findViewById(R.id.media_material_button1);
        mNoteMaterial.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mNoteMaterial.setTextColor(getResources().getColor(R.color.white));
        mTextMaterial.setBackgroundColor(getResources().getColor(R.color.white));
        mTextMaterial.setTextColor(getResources().getColor(R.color.colorPrimary));
        mMediaMaterial.setBackgroundColor(getResources().getColor(R.color.white));
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
                Intent intent = new Intent(NoteMaterialActivity.this,BookShelfActivity.class);
                startActivity(intent) ;
                finish();
            }
        });

        // 界面跳转到媒体资料界面
        mMediaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteMaterialActivity.this,EpisodeActivity.class);
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
        MenuItem item=menu.findItem(R.id.action_share);
        ShareActionProvider sap= (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,mFamilyInstruction.toString());
        if(sap!=null){
            sap.setShareIntent(intent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.test_text_resource:

                // 解析文本资源数据，并插入文本资源表中
                try{
                    JSONObject obj = new JSONObject(loadJSONFromAsset());
                    JSONArray m_jArry = obj.getJSONArray("text");

                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        String bookTitle_value = jo_inside.getString("bookTitle").trim();
                        String bookIntroduction_value = jo_inside.getString("bookIntroduction").trim();

                        String bookImage_value = jo_inside.getString("bookImage").trim();
                        String writerName_value = jo_inside.getString("writerName").trim();
                        String writerIntroduction_value = jo_inside.getString("writerIntroduction").trim();
                        String writerImage_value = jo_inside.getString("writerImage").trim();
                        String articleType_value = jo_inside.getString("articleType").trim();
                        String articleAncientFormat_value = jo_inside.getString("articleAncientFormat").trim();
                        String articleVernacularFormat_Value = jo_inside.getString("articleVernacularFormat").trim();

                        ContentValues values = new ContentValues();
                        values.put(TextResourceEntry.COLUMN_BOOK_TITLE,bookTitle_value);
                        values.put(TextResourceEntry.COLUMN_BOOK_INTRODUCTION,bookIntroduction_value);
                        values.put(TextResourceEntry.COLUMN_BOOK_IMAGE,bookImage_value);
                        values.put(TextResourceEntry.COLUMN_WRITER_NAME,writerName_value);
                        values.put(TextResourceEntry.COLUMN_WRITER_INTRODUCTION,writerIntroduction_value);
                        values.put(TextResourceEntry.COLUMN_WRITER_IMAGE,writerImage_value);
                        values.put(TextResourceEntry.COLUMN_ARTICLE_TYPE,articleType_value);
                        values.put(TextResourceEntry.COLUMN_ARTICLE_ANCIENT_FORMAT,articleAncientFormat_value);
                        values.put(TextResourceEntry.COLUMN_ARTICLE_VERNACULAR_FORMAT,articleVernacularFormat_Value);

                        //插入操作
                        Uri newUri = getContentResolver().insert(TextResourceEntry.CONTENT_URI, values);
                        if (newUri == null) {
                            Toast.makeText(this, getString(R.string.editor_insert_note_failed),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, getString(R.string.editor_insert_note_successful),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return true;
            case R.id.test_media_resource:
                // 解析媒体资源json数据，并插入媒体资源表中
                try{
                    JSONObject obj = new JSONObject(loadJSONFromAsset());
                    JSONArray m_jArry = obj.getJSONArray("media");

                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        String headTitle_value = jo_inside.getString("headTitle").trim();
                        String Introduction_value = jo_inside.getString("Introduction").trim();
                        String plot_value = jo_inside.getString("plot").trim();
                        String poster_value = jo_inside.getString("poster").trim();
                        String subTitle_value = jo_inside.getString("subTitle").trim();
                        String thumbnail_value = jo_inside.getString("thumbnail").trim();
                        String mediaData_value = jo_inside.getString("mediaData").trim();


                        ContentValues values = new ContentValues();
                        values.put(MediaResourceEntry.COLUMN_HEADTITLE,headTitle_value);
                        values.put(MediaResourceEntry.COLUMN_INTRODUCTION,Introduction_value);
                        values.put(MediaResourceEntry.COLUMN_PLOT,plot_value);
                        values.put(MediaResourceEntry.COLUMN_POSTER,poster_value);
                        values.put(MediaResourceEntry.COLUMN_SUBTITLE,subTitle_value);
                        values.put(MediaResourceEntry.COLUMN_THUMBNAIL,thumbnail_value);
                        values.put(MediaResourceEntry.COLUMN_MEDIA_DATA,mediaData_value);


                        //插入操作
                        Uri newUri = getContentResolver().insert(MediaResourceEntry.CONTENT_URI, values);
                        if (newUri == null) {
                            Toast.makeText(this, getString(R.string.editor_insert_note_failed),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, getString(R.string.editor_insert_note_successful),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return true;

            //用户家训测试
            case R.id.test_user_instruction:
                insertUserInstruction();
                return true;
            //用户信息测试
            case R.id.test_user_information:
                insertUserInformation();
                return true;
            case R.id.test_user_status:
                getUserStatus();
                return true;
        }

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            // 读取assets目录里的test.json文件，获取字节输入流
            InputStream is = getResources().getAssets().open("material.json");
            //  获取字节输入流长度
            int size = is.available();
            // 定义字节缓冲区
            byte[] buffer = new byte[size];
            // 读取字节输入流，存放到字节缓冲区里
            is.read(buffer);
            is.close();
            // 将字节缓冲区里的数据转换成utf-8字符串
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * TODO 测试函数
     */
    private void insertUserInstruction(){
        // 第一条记录
        ContentValues value1 = new ContentValues();
        value1.put(InstructionEntry.COLUMN_NOTE_TITLE,"慕贤");
        value1.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,"是以与善人居，如入芝兰之室，久而自芳也；与恶人居，如入鲍鱼之肆，久而自臭也。");
        value1.put(InstructionEntry.COLUMN_NOTE_JUSTICE,1);
        value1.put(InstructionEntry.COLUMN_NOTE_DESCRIPTION,"因此和善人在一起，如同进入养育芝兰的花房，时间一久自然就芬芳；若是和恶人在一起，如同进入卖鲍鱼的店铺，时间一久自然就腥臭。");

        // 第二条记录
        ContentValues value2 = new ContentValues();
        value2.put(InstructionEntry.COLUMN_NOTE_TITLE,"勉学");
        value2.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,"夫所以读书学问，本欲开心明目，利於行耳。");
        value2.put(InstructionEntry.COLUMN_NOTE_JUSTICE,1);
        value2.put(InstructionEntry.COLUMN_NOTE_DESCRIPTION,"所以要读书做学问，本意在於使心胸开阔使眼睛明亮，以有利於做实事。");

        // 第三条记录
        ContentValues value3 = new ContentValues();
        value3.put(InstructionEntry.COLUMN_NOTE_TITLE,"勉学");
        value3.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,"世人但知跨马被甲，长槊强弓，便云我能为将；不知明乎天道，辩乎地利，比量逆顺，鉴达兴亡之妙也。");
        value3.put(InstructionEntry.COLUMN_NOTE_JUSTICE,2);
        value3.put(InstructionEntry.COLUMN_NOTE_DESCRIPTION,"世人只知道骑马披甲，长矛强弓，就说我能为将，却不知道要有明察天道，辨识地利，考虑是否顺乎时势人心、审察通晓兴亡的能耐。");

        // 第三条记录
        ContentValues value4 = new ContentValues();
        value4.put(InstructionEntry.COLUMN_NOTE_TITLE,"名实");
        value4.put(InstructionEntry.COLUMN_NOTE_INSTRUCTION,"巧伪不如拙诚");
        value4.put(InstructionEntry.COLUMN_NOTE_JUSTICE,2);
        value4.put(InstructionEntry.COLUMN_NOTE_DESCRIPTION,"那种巧於作伪就还不如拙而诚实");

        Uri newUri1 = getContentResolver().insert(InstructionEntry.CONTENT_URI, value1);
        Uri newUri2 = getContentResolver().insert(InstructionEntry.CONTENT_URI, value2);
        Uri newUri3 = getContentResolver().insert(InstructionEntry.CONTENT_URI, value3);
        Uri newUri4 = getContentResolver().insert(InstructionEntry.CONTENT_URI, value4);
    }

    /**
     * TODO 测试用户信息函数
     */
    private void insertUserInformation(){

        ContentValues value = new ContentValues();
        value.put(UserInfoEntry.COLUMN_USER_NAME,"茹鹏锟");
        value.put(UserInfoEntry.COLUMN_USER_PASSWORD,"000000");
        value.put(UserInfoEntry.COLUMN_USER_STATUS,"1");

        Uri newUri1 = getContentResolver().insert(UserInfoEntry.CONTENT_URI, value);
        Log.i("NoteMaterialActivity",newUri1.toString());
    }

    /**
     * TODO 管理用户的登录-退出（辅助函数）
     */
    private void logout(){
        if(getUserStatus()==0){
            // 用户想要登陆家训应用
        }

        // 更新用户信息表中的用户状态
        ContentValues values = new ContentValues();
        values.put(UserInfoEntry.COLUMN_USER_STATUS,0);

        int rowsAffected = getContentResolver().update(UserInfoEntry.CONTENT_URI,values,null,null);
        if (rowsAffected == 0) {
            Toast.makeText(this, getString(R.string.user_log_out_failed),
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.user_log_out_successful),
                    Toast.LENGTH_SHORT).show();
        }
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
        // 构建要分享的字符串
        int typeColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_TITLE);
        int instrctionColumnIndex = cursor.getColumnIndex(InstructionEntry.COLUMN_NOTE_INSTRUCTION);

        mFamilyInstruction = new StringBuilder();
        while (cursor.moveToNext()) {
            String InstructionType = cursor.getString(typeColumnIndex);
            String Instruction = cursor.getString(instrctionColumnIndex);
            mFamilyInstruction.append(InstructionType).append("\n").append(Instruction);
        }

        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }


    /**
     * TODO 侧滑抽屉布局中菜单项的点击处理
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                break;
        }

        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }


    // TODO 辅助函数 获取用户名
    private String getUserName(){
        String[] projection = {
                UserInfoEntry.COLUMN_USER_NAME
        };

        Cursor cursor = getContentResolver().query(UserInfoEntry.CONTENT_URI, projection,null,null,null);

        String userName = "";
        int userNameColumnIndex = cursor.getColumnIndex(UserInfoEntry.COLUMN_USER_NAME);
        while (cursor.moveToNext()){
            userName = cursor.getString(userNameColumnIndex);
        }
        return userName;
    }
    // TODO 辅助函数 获取用户密码
    private String getUserPassword(){
        String[] projection = {
                UserInfoEntry.COLUMN_USER_PASSWORD
        };

        Cursor cursor = getContentResolver().query(UserInfoEntry.CONTENT_URI, projection,null,null,null);

        String userPassword = "";
        int userPasswordColumnIndex = cursor.getColumnIndex(UserInfoEntry.COLUMN_USER_PASSWORD);
        while (cursor.moveToNext()){
            userPassword = cursor.getString(userPasswordColumnIndex);
        }
        return userPassword;
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
