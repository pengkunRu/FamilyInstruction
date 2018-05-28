package com.example.android.familyinstruction.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by kun on 2018/5/28.
 */

public class InstructionProvider extends ContentProvider{

    // 日志消息的TAG
    public static final String LOG_TAG = InstructionProvider.class.getSimpleName();
    // 数据库助手对象
    private InstructionDbHelper mDbHelper;

    public InstructionProvider() {
        super();
    }

    @Override
    public boolean onCreate() {
        // 初始化数据库助手对象
        mDbHelper = new InstructionDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}
