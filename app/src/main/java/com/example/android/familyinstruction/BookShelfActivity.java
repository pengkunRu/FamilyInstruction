package com.example.android.familyinstruction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.familyinstruction.data.InstructionContract.UserInfoEntry;
import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;

import java.util.ArrayList;

public class BookShelfActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    // NavigationView Header Image
    private ImageView mHeaderImageView;
    // NavigationView Header Textview
    private TextView mHeaderUserName;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

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
        mMenu = mNavigationView.getMenu();

        mHeaderImageView = (ImageView)NavHeaderView.findViewById(R.id.user_photo_header);
        mHeaderUserName = (TextView)NavHeaderView.findViewById(R.id.user_name_header);


        /**
         * 初始化导航栏信息
         * 当前界面按钮为红色，其余为白色
         * 当前界面按钮文字为白色，其余为红色
         */
        Button mTextMaterial = findViewById(R.id.text_material_button2);
        Button mMediaMaterial = findViewById(R.id.media_material_button2);
        mTextMaterial.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mTextMaterial.setTextColor(getResources().getColor(R.color.white));
        mMediaMaterial.setBackgroundColor(getResources().getColor(R.color.white));
        mMediaMaterial.setTextColor(getResources().getColor(R.color.colorAccent));


        // 界面导航到媒体资料界面
        mMediaMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookShelfActivity.this, EpisodeActivity.class);
                startActivity(intent);
                finish();
            }
        });




        // 获取数据源
        // 在创建书籍数组列表的时候，我们需要在前面加上final修饰符，这样我们在
        // onItemClick方法中引用books arraylist了
        final ArrayList<Book> books = getBooks();

        BookAdapter adapter = new BookAdapter(this,books);

        GridView gridView = (GridView)findViewById(R.id.book_shelf_list);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 通过position，我们可以从Book数组列表中获取Book对象
                Book currentBook = books.get(position);

                // 页面跳转到用户想要阅读的书籍（书籍目录界面）
                Intent intent = new Intent(BookShelfActivity.this,CatalogActivity.class);
                intent.putExtra("bookTitle",currentBook.getBookTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getUserStatus()==0){
            mHeaderImageView.setImageResource(R.drawable.ic_logout_photo);
            mHeaderUserName.setText("");
            mMenu.findItem(R.id.logout).setTitle("登陆");
        }else {
            mHeaderImageView.setImageResource(R.drawable.ic_login_photo);
            mHeaderUserName.setText(getUserName());
            mMenu.findItem(R.id.logout).setTitle("退出登录");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * TODO 辅助函数：从文本资源表中取出去重的书名，封面图片信息
     * @return
     */
    private ArrayList<Book> getBooks() {
        String[] projection = {
                "distinct " + TextResourceEntry.COLUMN_BOOK_TITLE,
                TextResourceEntry.COLUMN_BOOK_IMAGE
        };

        Cursor cursor = getContentResolver().query(TextResourceEntry.CONTENT_URI, projection, null, null, null);

        int bookTtileColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_TITLE);
        int bookImageColumnIndex = cursor.getColumnIndex(TextResourceEntry.COLUMN_BOOK_IMAGE);

        final ArrayList<Book> books = new ArrayList<Book>();
        while (cursor.moveToNext()){
            String currentbookTitle = cursor.getString(bookTtileColumnIndex);
            String currentbookImage = cursor.getString(bookImageColumnIndex);

            // 根据图片的名字获得drawable目录下的图片资源
            int imageResourceId = getResources().getIdentifier(currentbookImage,"drawable","com.example.android.familyinstruction");
            books.add(new Book(currentbookTitle,imageResourceId));
        }

        return books;
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
            case R.id.bookshelf:
                if(getUserStatus()==0){
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(BookShelfActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.vedio_collection:
                if(getUserStatus()==0){
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(BookShelfActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.instruction_notes:
                if(getUserStatus()==0){
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(BookShelfActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(BookShelfActivity.this,NoteMaterialActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.search:
                if(getUserStatus()==0){
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(BookShelfActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.action_review:
                if(getUserStatus()==0){
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(BookShelfActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.settings:
                if(getUserStatus()==0){
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(BookShelfActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
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
    // TODO 辅助函数 更新用户用户状态
    private void changeUserStatus(){
        ContentValues values = new ContentValues();
        if(getUserStatus()==0){
            values.put(UserInfoEntry.COLUMN_USER_STATUS,1);
        }else{
            values.put(UserInfoEntry.COLUMN_USER_STATUS,0);
        }

        int rowsAffected = getContentResolver().update(UserInfoEntry.CONTENT_URI,values,null,null);
        if (rowsAffected == 0) {
            Toast.makeText(this, getString(R.string.user_log_out_failed),
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.user_log_out_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }
    // TODO 管理用户的登录-退出（辅助函数）
    private void logout(){
        if(getUserStatus()==0){
            // 用户想要登陆家训应用
            Intent intent = new Intent(BookShelfActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            changeUserStatus();
            if(getUserStatus()==0){
                mHeaderImageView.setImageResource(R.drawable.ic_logout_photo);
                mHeaderUserName.setText("");
                mMenu.findItem(R.id.logout).setTitle("登陆");
            }else {
                mHeaderImageView.setImageResource(R.drawable.ic_login_photo);
                mHeaderUserName.setText(getUserName());
                mMenu.findItem(R.id.logout).setTitle("退出登录");
            }
        }
    }
}
