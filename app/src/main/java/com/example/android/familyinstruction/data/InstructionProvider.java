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
import com.example.android.familyinstruction.data.InstructionContract.MediaResourceEntry;
import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;
import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;
import com.example.android.familyinstruction.data.InstructionContract.UserInfoEntry;
import com.example.android.familyinstruction.data.InstructionContract.UserBookShelfEntry;



public class InstructionProvider extends ContentProvider{

    // 访问整个用户鉴赏表的uri匹配编码
    private static final int NOTES = 100;
    // 访问某一行用户鉴赏表的uri匹配编码
    private static final int NOTE_ID = 101;
    // 访问整个文本资源表的uri匹配编码
    private static final int TEXT_RESOOURCE = 200;
    // 访问某一行文本资源表的uri匹配编码
    private static final int TEXT_RESOOURCE_ID = 201;
    // 访问整个媒体资源表的uri匹配编码
    private static final int MEDIA_RESOOURCE = 300;
    // 访问某一行媒体资源表的uri匹配编码
    private static final int MEDIA_RESOOURCE_ID = 301;
    // 访问整个用户信息表
    private static final int USER_INFORMATION = 400;
    // 访问具体一行用户信息表
    private static final int USER_INFORMATION_ID = 401;
    // 访问整个用户书架表
    private static final int USER_BOOK_SHELF = 500;
    // 访问具体一行用户书架表
    private static final int USER_BOOK_SHELF_ID = 501;



    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        // 将访问用户鉴赏表的两种模式添加和每种模式的uri编码添加到uri匹配器中
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_NOTES,NOTES);
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_NOTES+"/#",NOTE_ID);

        // 将访问文本资源表的两种模式添加和每种模式的uri编码添加到uri匹配器中
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_TEXT_RESOURCE,TEXT_RESOOURCE);
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_TEXT_RESOURCE+"/#",TEXT_RESOOURCE_ID);

        // 将访问媒体资源表的两种模式添加和每种模式的uri编码添加到uri匹配器中
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_MEDIA_RESOURCE,MEDIA_RESOOURCE);
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_MEDIA_RESOURCE+"/#",MEDIA_RESOOURCE_ID);

        // 将访问用户信息表的两种模式添加和每种模式的uri编码添加到uri匹配器中
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_USER_INFORMATION,USER_INFORMATION);
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_USER_INFORMATION+"/#",USER_INFORMATION_ID);

        // 将访问用户书架表的两种模式添加和每种模式的uri编码添加到uri匹配器中
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_BOOK_SHELF,USER_BOOK_SHELF);
        sUriMatcher.addURI(InstructionContract.CONTENT_AUTHORITY,InstructionContract.PATH_BOOK_SHELF+"/#",USER_BOOK_SHELF_ID);
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


    /**
     * TODO 查询函数,uri如果指向整张表，我们其实可以在参数2，参数3，参数4的作用下
     * TODO 将范围精确到我们感兴趣的行，列
     *
     * @param uri 统一资源定位符
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
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
                cursor = database.query(InstructionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case NOTE_ID:
                selection = InstructionEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InstructionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case TEXT_RESOOURCE:
                cursor = database.query(TextResourceEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case TEXT_RESOOURCE_ID:
                selection = TextResourceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TextResourceEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEDIA_RESOOURCE:
                cursor = database.query(MediaResourceEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEDIA_RESOOURCE_ID:
                selection = MediaResourceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(MediaResourceEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case USER_INFORMATION:
                cursor = database.query(UserInfoEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case USER_BOOK_SHELF:
                cursor = database.query(UserBookShelfEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        /**
         * Set notification URI on the Cursor,
         * so we know what content URI the Cursor was created for.
         * If the data at this URI changes, then we know we need to update the Cursor.
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

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




    /**
     * @param uri 将数据插入uri定义的地方（哪张表）
     * @param contentValues 将要插入的内容
     * @return uri 包含新插入行id的content uri
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return insertNote(uri, contentValues);
            case TEXT_RESOOURCE:
                return  insertTextResource(uri,contentValues);
            case MEDIA_RESOOURCE:
                return  insertMediaResource(uri,contentValues);
            case USER_INFORMATION:
                return  insertUserInformation(uri,contentValues);
            case USER_BOOK_SHELF:
                return insertUserBookShelf(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowsDeleted;

        // 获取数据库对象
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(InstructionEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case NOTE_ID:
                // Delete a single row given by the ID in the URI
                selection = InstructionEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(InstructionEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_BOOK_SHELF:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(UserBookShelfEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_BOOK_SHELF_ID:
                // Delete a single row given by the ID in the URI
                selection = UserBookShelfEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UserBookShelfEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return updateNotes(uri, contentValues, selection, selectionArgs);
            case NOTE_ID:
                selection = InstructionEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateNotes(uri, contentValues, selection, selectionArgs);
            case USER_INFORMATION:
                return updateUserInfomation(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    //插入新数据到用户鉴赏表的的辅助函数
    private Uri insertNote(Uri uri, ContentValues values) {

        //数据验证

        // Check that the type is not null
        String type = values.getAsString(InstructionEntry.COLUMN_NOTE_TITLE);
        if (TextUtils.isEmpty(type)) {
            return null;
        }

        // Check that the instruction is not null
        String instruction = values.getAsString(InstructionEntry.COLUMN_NOTE_INSTRUCTION);
        if (TextUtils.isEmpty(instruction)) {
            return null;
        }

        // Check that the mean is not null
        String mean = values.getAsString(InstructionEntry.COLUMN_NOTE_JUSTICE);
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

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, newRowId);
    }

    /**
     * TODO 插入新数据到媒体资源表的辅助函数
     * @param uri
     * @param values
     * @return
     */
    private Uri insertMediaResource(Uri uri, ContentValues values) {

        // 获得数据库对象
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long newRowId = database.insert(InstructionContract.MediaResourceEntry.TABLE_NAME, null, values);

        //判断Insertc操作是否成功
        if (newRowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, newRowId);
    }


    /**
     * TODO 插入新数据到文本资源表的辅助函数
     * @param uri
     * @param values
     * @return
     */
    private Uri insertTextResource(Uri uri, ContentValues values){
        // 首先获得一个数据库对象
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // 数据库插入
        long id = database.insert(TextResourceEntry.TABLE_NAME, null, values);
        // 如果 ID 等于 -1，那我们就知道插入失败了。否则，插入将是成功的。
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    // TODO 插入新数据到用户信息表的辅助函数
    private Uri insertUserInformation(Uri uri, ContentValues values){
        // 首先获得一个数据库对象
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // 数据库插入
        long id = database.insert(UserInfoEntry.TABLE_NAME, null, values);
        // 如果 ID 等于 -1，那我们就知道插入失败了。否则，插入将是成功的。
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    // TODO 更新操作的辅助函数（用户家训表）
    private int updateNotes(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(InstructionEntry.COLUMN_NOTE_TITLE)) {
            String type = values.getAsString(InstructionEntry.COLUMN_NOTE_TITLE);
            if (type == null) {
                throw new IllegalArgumentException("title is null");
            }
        }

        if (values.containsKey(InstructionEntry.COLUMN_NOTE_INSTRUCTION)) {
            String instruction = values.getAsString(InstructionEntry.COLUMN_NOTE_INSTRUCTION);
            if (instruction == null) {
                throw new IllegalArgumentException("Instruction is null");
            }
        }

        if (values.containsKey(InstructionEntry.COLUMN_NOTE_JUSTICE)) {
            String mean = values.getAsString(InstructionEntry.COLUMN_NOTE_JUSTICE);
            if (mean == null) {
                throw new IllegalArgumentException("justice is null");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        int rowsUpdated = database.update(InstructionEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    // TODO 更新操作的辅助函数（用户信息表）
    private int updateUserInfomation(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(UserInfoEntry.TABLE_NAME, values, selection, selectionArgs);
        return rowsUpdated;
    }

    // TODO 辅助函数：向用户书架表中添加数据
    private Uri insertUserBookShelf(Uri uri, ContentValues values){
        // 首先获得一个数据库对象
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // 数据库插入
        long id = database.insert(UserBookShelfEntry.TABLE_NAME, null, values);
        // 如果 ID 等于 -1，那我们就知道插入失败了。否则，插入将是成功的。
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }
    // TODO 辅助函数: 从用户书架表中删除数据
}
