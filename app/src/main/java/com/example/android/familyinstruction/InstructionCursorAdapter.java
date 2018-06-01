package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/5/29.
 */

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.familyinstruction.data.InstructionContract;


public class InstructionCursorAdapter extends CursorAdapter {


    public InstructionCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    // 绘制列表项布局
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // 使用id来获取两个TextView
        TextView typeTextView = (TextView)view.findViewById(R.id.type);
        TextView instructionTextView = (TextView)view.findViewById(R.id.instruction);

        // 获得我们想要数据的列索引
        int typeColumnIndex = cursor.getColumnIndex(InstructionContract.InstructionEntry.COLUMN_NOTE_TITLE);
        int instructionColumnIndex = cursor.getColumnIndex(InstructionContract.InstructionEntry.COLUMN_NOTE_INSTRUCTION);

        // 根据列索引获取数据
        String type = cursor.getString(typeColumnIndex);
        String instruction = cursor.getString(instructionColumnIndex);

        // 将获取到的数据绑定到视图控件中
        typeTextView.setText(type);
        instructionTextView.setText(instruction);
    }
}