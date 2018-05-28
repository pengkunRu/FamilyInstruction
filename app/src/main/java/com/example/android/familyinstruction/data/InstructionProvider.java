package com.example.android.familyinstruction.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by kun on 2018/5/28.
 */

public class InstructionProvider extends ContentProvider{

    /** URI matcher code for the content URI for the notes table */
    private static final int NOTES = 100;
    /** URI matcher code for the content URI for a single note in the notes table */
    private static final int NOTE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // This is run the first time anything is called from this class.
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_PETS,NOTES);
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_PETS+"/#",NOTE_ID);
    }

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
