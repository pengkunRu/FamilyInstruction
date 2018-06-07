package com.example.android.familyinstruction;

/**
 * Created by kun on 2018/6/4.
 */

public class MediaList {

    private String mHeadtitle;
    // 小标题
    private String mSubtitle;
    // 分剧集缩略图的ID
    private int mThumbnail;
    // 媒体资源链接
    private String mMediaData;
    // 分剧情简介
    private String mPlot;

    // 构造函数
    public MediaList(String headtitle, String subtitle, int thumbnail, String mediaData, String plot){
        mHeadtitle = headtitle;
        mSubtitle = subtitle;
        mThumbnail = thumbnail;
        mMediaData = mediaData;
        mPlot = plot;
    }

    // 获取大标题
    public String getHeadtitle(){
        return mHeadtitle;
    }
    // 获取小标题
    public String getSubtitle(){
        return mSubtitle;
    }

    // 分剧集缩略图的ID
    public int getThumbnail(){
        return mThumbnail;
    }

    // 获取媒体资源链接
    public String getMediaData(){
        return  mMediaData;
    }

    //获取分剧情简介
    public String getPlot(){
        return mPlot;
    }
}
