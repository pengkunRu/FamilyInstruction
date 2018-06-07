package com.example.android.familyinstruction.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;
import com.example.android.familyinstruction.data.InstructionContract.MediaResourceEntry;
import com.example.android.familyinstruction.data.InstructionContract.TextResourceEntry;
/**
 * Created by kun on 2018/5/28.
 */

public class InstructionDbHelper extends SQLiteOpenHelper{

    // 数据库名称
    private static final String DATABASE_NAME = "family_instruction.db";
    // 数据库版本
    private static final int DATABASE_VERSION = 8;

    // 构造函数
    public InstructionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 当数据库第一次创建时，系统会调用onCreate()方法,进行表格创建，初始填充操作
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户鉴赏家训表
        final String SQL_CREATE_NOTES_TABLE =
                "CREATE TABLE " + InstructionEntry.TABLE_NAME + "("
                + InstructionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InstructionEntry.COLUMN_NOTE_TITLE + " TEXT NOT NULL, "
                + InstructionEntry.COLUMN_NOTE_INSTRUCTION + " TEXT NOT NULL, "
                + InstructionEntry.COLUMN_NOTE_JUSTICE + " INTEGER NOT NULL, "
                + InstructionEntry.COLUMN_NOTE_DESCRIPTION + " TEXT);";

        final String SQL_CREATE_TEXT_RESOURCE_TABLE =
                "CREATE TABLE " + TextResourceEntry.TABLE_NAME + "("
                        + TextResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TextResourceEntry.COLUMN_BOOK_TITLE + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_BOOK_INTRODUCTION + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_BOOK_IMAGE + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_WRITER_NAME + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_WRITER_INTRODUCTION + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_WRITER_IMAGE + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_ARTICLE_TYPE + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_ARTICLE_ANCIENT_FORMAT + " TEXT NOT NULL, "
                        + TextResourceEntry.COLUMN_ARTICLE_VERNACULAR_FORMAT + " TEXT NOT NULL);";

        final String SQL_CREATE_MEDIA_RESOURCE_TABLE =
                "CREATE TABLE " + MediaResourceEntry.TABLE_NAME + "("
                        + MediaResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + MediaResourceEntry.COLUMN_HEADTITLE + " TEXT NOT NULL, "
                        + MediaResourceEntry.COLUMN_INTRODUCTION + " TEXT NOT NULL, "
                        + MediaResourceEntry.COLUMN_PLOT + " TEXT NOT NULL, "
                        + MediaResourceEntry.COLUMN_POSTER + " TEXT NOT NULL, "
                        + MediaResourceEntry.COLUMN_SUBTITLE + " TEXT NOT NULL, "
                        + MediaResourceEntry.COLUMN_THUMBNAIL + " TEXT NOT NULL, "
                        + MediaResourceEntry.COLUMN_MEDIA_DATA + " TEXT NOT NULL);";

        // 执行创建表sql语句
        db.execSQL(SQL_CREATE_NOTES_TABLE);
        db.execSQL(SQL_CREATE_TEXT_RESOURCE_TABLE);
        db.execSQL(SQL_CREATE_MEDIA_RESOURCE_TABLE);
    }

    // 当数据库版本发生变化时候，这个方法会被调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InstructionEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TextResourceEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MediaResourceEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
