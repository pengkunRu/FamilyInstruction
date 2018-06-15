package com.example.android.familyinstruction.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 使这个类成为final类，即不能被扩展（继承），因为他只是用来提供常量.
 * 合约类通常包含与数据库相关的常量.
 */

public final class InstructionContract {

    private InstructionContract(){}

    // CONTENT_AUTHORITY(统一资源定位符的中间部分)
    public static final String CONTENT_AUTHORITY = "com.example.android.familyinstruction";

    // BASE_CONTENT_URI（统一资源定位符的第一部分，第二部分）
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // PATH_TableName（用户家训表）
    public static final String PATH_NOTES = "notes";
    // PATH_TableName(文本资源表)
    public static final String PATH_TEXT_RESOURCE = "text_resource";
    // PATH_TableName(媒体资源表)
    public static final String PATH_MEDIA_RESOURCE = "media_resource";
    // PATH_TableName(用户信息表)
    public static final String PATH_USER_INFORMATION = "user_information";
    // PATH_TableName(用户书架表)
    public static final String PATH_BOOK_SHELF = "user_book_shelf";
    // PATH_TableName(用户视频收藏夹表)
    public static final String PATH_MEDIA_COLLECTION = "user_media_collection";

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
        // 分剧情简介
        public final static String COLUMN_PLOT = "plot";
        // 剧集海报
        public final static String COLUMN_POSTER = "poster";
        // 分剧集标题
        public final static String COLUMN_SUBTITLE = "subTitle";
        // 分剧集缩略图
        public final static String COLUMN_THUMBNAIL = "thumbnail";
        // 媒体资源url
        public final static String COLUMN_MEDIA_DATA = "mediaData";
    }

    // 用户信息表的合约类内容
    public final static class UserInfoEntry implements BaseColumns{

        // content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USER_INFORMATION);

        // The MIME type of for a list of users
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_INFORMATION;

        // The MIME type for a single user.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_INFORMATION;



        // 用户信息表的表名
        public static final String TABLE_NAME = "user_information";


        // 唯一标识符
        public final static String _ID = BaseColumns._ID;
        // 用户名
        public final static String COLUMN_USER_NAME = "userName";
        // 用户密码
        public final static String COLUMN_USER_PASSWORD = "userPassword";
        // 用户状态
        public final static String COLUMN_USER_STATUS = "userStatus";
    }

    //用户书架合约类
    public final static class UserBookShelfEntry implements BaseColumns{

        // content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOK_SHELF);

        // The MIME type of for a list of users
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOK_SHELF;

        // The MIME type for a single user.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOK_SHELF;



        // 用户信息表的表名
        public static final String TABLE_NAME = "user_book_shelf";


        // 唯一标识符
        public final static String _ID = BaseColumns._ID;
        // 图书名称
        public final static String COLUMN_BOOK_TITLE = "bookTitle";
        // 图书封面
        public final static String COLUMN_BOOK_IMAGE = "bookImage";
    }

    //视频收藏表合约类
    public final static class UserMediaCollectionEntry implements BaseColumns{

        // content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEDIA_COLLECTION);

        // The MIME type of for a list of users
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDIA_COLLECTION;

        // The MIME type for a single user.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDIA_COLLECTION;



        // 用户收藏表：表名
        public static final String TABLE_NAME = "user_media_collection";


        // 唯一标识符
        public final static String _ID = BaseColumns._ID;
        // 大标题
        public final static String COLUMN_HEAD_TITLE = "headTitle";
        // 小标题
        public final static String COLUMN_SUB_TITLE = "subTitle";
        // 视频缩略图
        public final static String COLUMN_THUMBNAIL = "thumbnail";
        // 分剧情简介
        public final static String COLUMN_PLOT = "plot";
        // 视频资源
        public final static String COLUMN_MEDIA_DATA = "mediaData";
    }
}
