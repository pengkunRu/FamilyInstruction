package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/6/3.
 */

public class Episode {
    //剧集名
    private String mHeadTitle;
    //剧集海报图
    private int mPoster;
    //剧集简介
    private String mIntroduction;

    public Episode(String headTitle,int poster,String introduction){
        mHeadTitle = headTitle;
        mPoster = poster;
        mIntroduction = introduction;
    }
    public String getHeadTitle(){
        return mHeadTitle;
    }
    public int getPoster(){
        return mPoster;
    }
    public String getIntroduction(){
        return mIntroduction;
    }
}
