package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/6/2.
 */

public class Book {

    // 书名
    private String mBookTitle;
    // 书籍封面图片id
    private int mImageResourceId;

    // 构造函数
    public Book(String bookTitle,int imageResourceId){
        mBookTitle = bookTitle;
        mImageResourceId = imageResourceId;
    }

    public String getBookTitle(){
        return mBookTitle;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }
}
