package com.example.android.familyinstruction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.familyinstruction.data.InstructionContract.UserInfoEntry;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    // NavigationView Header Image
    private ImageView mHeaderImageView;
    // NavigationView Header Textview
    private TextView mHeaderUserName;
    private Menu mMenu;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_test);
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

        tabLayout = (TabLayout)findViewById(R.id.tablayout_id);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new BooksFragment(),"家训书籍");
        adapter.AddFragment(new EpisodeFragment(),"家训视频");
        adapter.AddFragment(new NewsFragment(),"家训资讯");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
                Intent intentBookShelf = new Intent(MainActivity.this,UserBookShelfActivity.class);
                startActivity(intentBookShelf);
                break;
            case R.id.vedio_collection:
                Intent intentMediaCollection = new Intent(MainActivity.this,UserMediaCollectionActivity.class);
                startActivity(intentMediaCollection);
                break;
            case R.id.instruction_notes:
                if(getUserStatus()==0){
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    // 用户想要登陆家训应用
                    Intent intent = new Intent(MainActivity.this,NoteMaterialActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.search:
                // 用户想要登陆家训应用
                Intent intentSearch = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.action_review:
                Intent intentReview = new Intent(MainActivity.this,ReviewActivity.class);
                startActivity(intentReview);
                break;
            case R.id.settings:
                Intent intentTest = new Intent(MainActivity.this,NoteMaterialActivity.class);
                startActivity(intentTest);
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
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
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
    // TODO 辅助函数 文言文在线查询
    private void openWebPage(String url){
        // 将网页Url解析为Uri
        Uri webPage = Uri.parse(url);
        // 参数 Intent.ACTION_VIEW 会告诉anroid系统家训软件想要查询的内容
        Intent intent = new Intent(Intent.ACTION_VIEW,webPage);
        // 判断Android系统是否可以处理我们的请求
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
}
