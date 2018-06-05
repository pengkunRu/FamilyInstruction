package com.example.android.familyinstruction.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kun on 2018/5/28.
 * 使这个类成为final类，即不能被扩展（继承），因为他只是用来提供常量.
 * 合约类通常包含与数据库相关的常量.
 */

public final class InstructionContract {

    private InstructionContract(){}

    // CONTENT_AUTHORITY(统一资源定位符的中间部分)
    public static final String CONTENT_AUTHORITY = "com.example.android.familyinstruction";

    // BASE_CONTENT_URI（统一资源定位符的第一部分，第二部分）
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // PATH_TableName（用户鉴赏表）
    public static final String PATH_NOTES = "notes";
    // PATH_TableName(文本资源表)
    public static final String PATH_TEXT_RESOURCE = "text_resource";
    // PATH_TableName(媒体资源表)
    public static final String PATH_MEDIA_RESOURCE = "media_resource";




    // 用户鉴赏表的合约内容
    public final static class InstructionEntry implements BaseColumns{

        // content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTES);

        // The MIME type of for a list of notes
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        // The MIME type for a single note.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        // 表名
        public final static String TABLE_NAME = "notes";
        // 唯一标识符
        public final static String _ID = BaseColumns._ID;
        // 家训的类型
        public final static String COLUMN_NOTE_TITLE ="type";
        // 家训的内容
        public final static String COLUMN_NOTE_INSTRUCTION = "instruction";
        // 家训的优先级
        public final static String COLUMN_NOTE_JUSTICE = "priority";
        // 家训的备注：赏析，描述，解释
        public final static String COLUMN_NOTE_DESCRIPTION = "description";
    }




    // 文本资源表的合约类内容
    public final static class TextResourceEntry implements BaseColumns{

        // content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TEXT_RESOURCE);

        // The MIME type of for a list of notes
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEXT_RESOURCE;

        // The MIME type for a single note.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEXT_RESOURCE;

        // 表名字
        public static final String TABLE_NAME = "text_resource";

        // 唯一标识符
        public final static String _ID = BaseColumns._ID;
        // 书籍名
        public final static String COLUMN_BOOK_TITLE ="bookTitle";
        // 书籍简介
        public final static String COLUMN_BOOK_INTRODUCTION ="bookIntroduction";
        // 书籍封面图片
        public final static String COLUMN_BOOK_IMAGE ="bookImage";
        // 作者名字
        public final static String COLUMN_WRITER_NAME ="writerName";
        // 作者简介
        public final static String COLUMN_WRITER_INTRODUCTION ="writerIntroduction";
        // 作者图片
        public final static String COLUMN_WRITER_IMAGE ="writerImage";
        // 家训文章类型
        public final static String COLUMN_ARTICLE_TYPE ="articleType";
        // 家训文章的文言文形式
        public final static String COLUMN_ARTICLE_ANCIENT_FORMAT ="articleAncientFormat";
        // 家训文章的白话文形式
        public final static String COLUMN_ARTICLE_VERNACULAR_FORMAT ="articleVernacularFormat";
    }

    // 媒体资源表的合约类内容
    public final static class MediaResourceEntry implements BaseColumns{

        // content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEDIA_RESOURCE);

        // The MIME type of for a list of notes
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDIA_RESOURCE;

        // The MIME type for a single note.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDIA_RESOURCE;



        // 媒体资源表的名字
        public static final String TABLE_NAME = "media_resource";


        // 唯一标识符
        public final static String _ID = BaseColumns._ID;
        // 剧集的总标题
        public final static String COLUMN_HEADTITLE = "headTitle";
        // 剧集简介
        public final static String COLUMN_INTRODUCTION = "introduction";
        // 剧集海报
        public final static String COLUMN_POSTER = "poster";
        // 分剧集标题
        public final static String COLUMN_SUBTITLE = "subTitle";
        // 分剧集缩略图
        public final static String COLUMN_THUMBNAIL = "thumbnail";
        // 媒体资源url
        public final static String COLUMN_MEDIA_DATA = "mediaData";
    }
}
