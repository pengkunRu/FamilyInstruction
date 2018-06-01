package com.example.android.familyinstruction.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.familyinstruction.data.InstructionContract.InstructionEntry;

/**
 * Created by kun on 2018/5/28.
 */

public class InstructionDbHelper extends SQLiteOpenHelper{

    // 数据库名称
    private static final String DATABASE_NAME = "family_instruction.db";
    // 数据库版本
    private static final int DATABASE_VERSION = 2;

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
        //CREATE TABLE notes (_id INTEGER, type TEXT, instruction TEXT, mean TEXT, time INTEGER);
        String SQL_CREATE_NOTES_TABLE =
                "CREATE TABLE " + InstructionEntry.TABLE_NAME + "("
                + InstructionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InstructionEntry.COLUMN_NOTE_TITLE + " TEXT NOT NULL, "
                + InstructionEntry.COLUMN_NOTE_INSTRUCTION + " TEXT NOT NULL, "
                + InstructionEntry.COLUMN_NOTE_JUSTICE + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    // 当数据库版本发生变化时候，这个方法会被调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InstructionEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
