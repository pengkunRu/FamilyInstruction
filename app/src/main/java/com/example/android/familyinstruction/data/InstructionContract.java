package com.example.android.familyinstruction.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kun on 2018/5/28.
 * 使这个类成为final类，即不能被扩展（继承），因为他只是用来提供常量.
 * 合约类通常包含与数据库相关的常量.
 */

public final class InstructionContract {

    private InstructionContract(){}

    // CONTENT_AUTHORITY
    public static final String CONTENT_AUTHORITY = "com.example.android.familyinstruction";

    /**
     *  Base Content Uri
     * 它将 URI 字符串作为输入，然后返回一个 URI
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // PATH_TableName
    public static final String PATH_NOTES = "notes";


    public final static class InstructionEntry implements BaseColumns{

        /**
         * The content URI to access the notes data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTES);

        // The MIME type of for a list of notes
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        // The MIME type for a single note.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        /** Name of database table for notes */
        public final static String TABLE_NAME = "notes";

        public final static String _ID = BaseColumns._ID;
        // 标题：这条家训选自
        public final static String COLUMN_NOTE_TITLE ="title";
        // 家训的内容
        public final static String COLUMN_NOTE_INSTRUCTION = "instruction";
        // 用户对这条家训的赏析
        public final static String COLUMN_NOTE_JUSTICE = "justice";
    }
}
