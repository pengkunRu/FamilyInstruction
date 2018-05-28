package com.example.android.familyinstruction.data;

import android.provider.BaseColumns;

/**
 * Created by kun on 2018/5/28.
 * 使这个类成为final类，即不能被扩展（继承），因为他只是用来提供常量.
 * 合约类通常包含与数据库相关的常量.
 */

public final class InstructionContract {

    private InstructionContract(){}

    public final static class InstructionEntry implements BaseColumns{

        /** Name of database table for notes */
        public final static String TABLE_NAME = "notes";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NOTE_TYPE ="type";
        public final static String COLUMN_NOTE_INSTRUCTION = "instruction";
        public final static String COLUMN_NOTE_MEAN = "mean";
        public final static String COLUMN_NOTE_TIME = "time";
    }
}
