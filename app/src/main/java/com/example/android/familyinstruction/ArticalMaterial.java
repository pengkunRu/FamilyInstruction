package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/5/29.
 *
 * ArticalMaterial用来表示一篇文章
 *
 * 文章标题
 * 类型
 * 文言文
 * 白话文
 *
 * 我们不希望用户修改家训文章的状态，所以我们不提供方法set方法
 * 使外部类用来修改文章的状态
 *
 */

public class ArticalMaterial {

    // 文章的文言文形式
    private String mClassicTranslation;
    // 文章的白话文形式
    private String mVernacularTranslation;
    // 文章标题
    private String mTitle;
    // 文章类型
    private String mType;
    // 文章作者
    private String mWriter;

    // 构造函数
    public ArticalMaterial(String title,String writer, String type,String classicTranslation,String vernacularTranslation){
        mTitle = title;
        mType = type;
        mWriter = writer;
        mClassicTranslation = classicTranslation;
        mVernacularTranslation = vernacularTranslation;
    }

    // 获取文章的标题
    public String getTitle(){
        return mTitle;
    }

    // 获取文章的类型
    public String getType(){
        return  mType;
    }

    // 获取文章的文言文形式
    public String getClassicTranslation(){
        return mClassicTranslation;
    }

    // 获取文章的白话文形式
    public String getVernacularTranslation(){
        return mVernacularTranslation;
    }

    // 获取文章的作者
    public String getWriter(){
        return  mWriter;
    }
}
