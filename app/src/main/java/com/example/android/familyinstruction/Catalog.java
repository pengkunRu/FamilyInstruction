package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/6/3.
 */

public class Catalog {

    // 数据库中的唯一标识符id
    private int mId;
    // 书名
    private String mBookTitle;
    // 书籍简介
    private String mBookInstruction;
    // 文章类型
    private String mArticleType;

    // 构造函数
    public Catalog(int id,String bookTitle,String bookInstruction,String articleType){
        mId = id;
        mBookTitle = bookTitle;
        mBookInstruction = bookInstruction;
        mArticleType = articleType;
    }

    public int getRowId(){
        return mId;
    }
    public String getBookTitle(){
        return mBookTitle;
    }

    public String getBookInstruction(){
        return mBookInstruction;
    }

    public String getArticleType(){
        return mArticleType;
    }
}
