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
import android.text.TextUtils;
import android.util.Log;
import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;

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
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_NOTES,NOTES);
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_NOTES+"/#",NOTE_ID);
    }

    // 日志消息的TAG
    public static final String LOG_TAG = InstructionProvider.class.getSimpleName();
    // 数据库助手对象
    private InstructionDbHelper mDbHelper;

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

    /**
     * 此方法的用途是返回描述输入URI中存储的数据类型的字符串。
     * 该字符串为 MIME 类型，也称为内容类型
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return InstructionEntry.CONTENT_LIST_TYPE;
            case NOTE_ID:
                return InstructionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
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
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // 获取数据库对象
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                // Delete all rows that match the selection and selection args
                return database.delete(InstructionEntry.TABLE_NAME, selection, selectionArgs);
            case NOTE_ID:
                // Delete a single row given by the ID in the URI
                selection = InstructionEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(InstructionEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case NOTE_ID:
                selection = InstructionEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    //辅助函数
    private Uri insertNote(Uri uri, ContentValues values) {

        //数据验证

        // Check that the type is not null
        String type = values.getAsString(InstructionEntry.COLUMN_NOTE_TYPE);
        if (TextUtils.isEmpty(type)) {
            return null;
        }

        // Check that the instruction is not null
        String instruction = values.getAsString(InstructionEntry.COLUMN_NOTE_INSTRUCTION);
        if (TextUtils.isEmpty(instruction)) {
            return null;
        }

        // Check that the mean is not null
        String mean = values.getAsString(InstructionEntry.COLUMN_NOTE_MEAN);
        if (TextUtils.isEmpty(mean)) {
            return null;
        }

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

    // 更新操作的辅助函数
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(InstructionEntry.COLUMN_NOTE_TYPE)) {
            String type = values.getAsString(InstructionEntry.COLUMN_NOTE_TYPE);
            if (type == null) {
                throw new IllegalArgumentException("Type is null");
            }
        }

        if (values.containsKey(InstructionEntry.COLUMN_NOTE_INSTRUCTION)) {
            String instruction = values.getAsString(InstructionEntry.COLUMN_NOTE_INSTRUCTION);
            if (instruction == null) {
                throw new IllegalArgumentException("Instruction is null");
            }
        }

        if (values.containsKey(InstructionEntry.COLUMN_NOTE_MEAN)) {
            String mean = values.getAsString(InstructionEntry.COLUMN_NOTE_TYPE);
            if (mean == null) {
                throw new IllegalArgumentException("Mean is null");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        return database.update(InstructionEntry.TABLE_NAME, values, selection, selectionArgs);
    }

}
