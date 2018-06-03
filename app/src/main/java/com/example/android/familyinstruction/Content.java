package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/6/3.
 */

public class Content {

    // 作者名字
    private String mWriterName;
    // 作者简介
    private String mWriterIntroduction;
    // 作者图片
    private String mImageResourceId;
    // 文章类型
    private String mArticleType;
    // 文章文言文形式
    private String mArticleAncientFormat;
    // 文章白话文形式
    private String mArticleVernacularFormat;

    // 构造函数
    public Content(String writerName,String writerIntroduction,String imageResourceId,String articleType,String articleAncientFormat,String articleVernacularFormat){
        mWriterName = writerName;
        mWriterIntroduction = writerIntroduction;
        mImageResourceId = imageResourceId;
        mArticleType = articleType;
        mArticleAncientFormat = articleAncientFormat;
        mArticleVernacularFormat = articleVernacularFormat;
    }

    public String getWriterName(){
        return mWriterName;
    }
    public String getWriterIntroduction(){
        return mWriterIntroduction;
    }

    public String getImageResourceId(){
        return mImageResourceId;
    }

    public String getArticleType(){
        return mArticleType;
    }

    public String getArticleAncientFormat(){
        return mArticleAncientFormat;
    }

    public String getArticleVernacularFormat(){
        return mArticleVernacularFormat;
    }
}
