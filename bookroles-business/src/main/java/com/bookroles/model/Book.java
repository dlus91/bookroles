package com.bookroles.model;

import com.bookroles.vo.PageHelper;

/**
 * @Author: dlus91
 * @Date: 2023/9/4 10:00
 */
public class Book extends PageHelper {
    //todo 百度云盘字段未添加
    //todo model里的对象都没有用到 要怎么优化？
    //分页启动项
    private boolean pageActive = true;
    //id
    private int id;
    //作者
    private String author;
    //名称
    private String name;
    //存储类型-分类 id
    private int archiveTypeId;
    //存储类型-分类 名称
    private String archiveTypeName;
    //图片
    private String image;
    //评分 0-5
    private int score;
    //下载格式
    private String downloadFormat;
    //上传时间 - 时间戳
    private long uploadTime;
    //出版社
    private String publishHouse;
    //出版月份
    private String publishMonth;
    //内容简介
    private String contentIntro;
    //下载地址
    private String downloadUrl;

//    public static String fileName(){
//        return "book";
//    }
//
//    public static String headerString(){
//        return "id,author,name" +
//                ",archive_type_id,archive_type_name,image" +
//                ",score,download_format,upload_time" +
//                ",publish_house,publish_month,content_intro";
//    }

}
