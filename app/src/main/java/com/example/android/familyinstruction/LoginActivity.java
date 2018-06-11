package com.example.android.familyinstruction;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.familyinstruction.data.InstructionContract.UserInfoEntry;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 隐藏ActionBar
        getSupportActionBar().hide();

        final TextView mUserNameTextView = (TextView)findViewById(R.id.user_name_login);
        final TextView mUserPasswordTextView= (TextView)findViewById(R.id.password_login);
        Button mLoginButton = (Button)findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mUserName = mUserNameTextView.getText().toString().trim();
                String mUserPassword = mUserPasswordTextView.getText().toString().trim();
                if(mUserName.equals(getUserName())&&mUserPassword.equals(getUserPassword())){
                    changeUserStatus();
                    finish();
                }
            }
        });
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
            Toast.makeText(this, getString(R.string.user_log_in_failed),
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.user_log_in_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
