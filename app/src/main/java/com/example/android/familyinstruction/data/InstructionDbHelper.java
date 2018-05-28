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
    private static final int DATABASE_VERSION = 1;

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
                + InstructionEntry.COLUMN_NOTE_TYPE + " TEXT, "
                + InstructionEntry.COLUMN_NOTE_INSTRUCTION + " TEXT NOT NULL, "
                + InstructionEntry.COLUMN_NOTE_MEAN + " TEXT NOT NULL, "
                + InstructionEntry.COLUMN_NOTE_TIME + " INTEGER NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    // 因为我们的数据库只有一个版本，所以这个方法可以保持不变为空
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
