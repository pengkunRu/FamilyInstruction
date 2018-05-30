package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/5/30.
 */

public class VedioMaterial {

    // 大标题
    private String mHeadline;
    // 小标题
    private String mSubtitle;
    // 主讲人
    private String mSpeaker;
    // 媒体资料的资源ID
    private int mVedioResourceId;

    // 构造函数
    public VedioMaterial(String headline,String subtitle, String speaker,int vedioResourceId){
        mHeadline = headline;
        mSubtitle = subtitle;
        mSpeaker = speaker;
        mVedioResourceId = vedioResourceId;
    }

    // 获取大标题
    public String getHeadline(){
        return mHeadline;
    }
    // 获取小标题
    public String getSubtitle(){
        return mSubtitle;
    }

    // 获取主讲人信息
    public String getSpeaker(){
        return mSpeaker;
    }

    // 获取媒体资料的资源ID
    public int getVedioResourceId(){
        return  mVedioResourceId;
    }
}