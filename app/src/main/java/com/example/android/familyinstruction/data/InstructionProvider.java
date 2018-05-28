package com.example.android.familyinstruction.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // 获取数据库对象
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        // 将uri发送到UriMatcher,它会帮我们决定是哪种模式
        int match = sUriMatcher.match(uri);
        switch (match){
            case NOTES:
                cursor = database.query(InstructionContract.InstructionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case NOTE_ID:
                selection = InstructionContract.InstructionEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InstructionContract.InstructionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return insertNote(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    //辅助函数
    private Uri insertNote(Uri uri, ContentValues values) {
        // 获得数据库对象
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long newRowId = database.insert(InstructionContract.InstructionEntry.TABLE_NAME, null, values);

        //判断Insertc操作是否成功
        if (newRowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, newRowId);

    }

}
