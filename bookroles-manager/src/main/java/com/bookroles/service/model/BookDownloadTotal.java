package com.bookroles.service.model;

import com.bookroles.controller.vo.PageHelper;

/**
 * @Author: dlus91
 * @Date: 2023/9/7 22:47
 */
public class BookDownloadTotal extends PageHelper {

    private boolean pageActive = false;
    private int id;
    private int count;
    private long timestamp;

//    public static String fileName(){
//        return "book_download_total";
//    }
//
//    public static String headerString(){
//        return "id,count,timestamp";
//    }

    public void openPage(){
        this.pageActive = true;
    }

    public void closePage(){
        this.pageActive = false;
    }

    public boolean isPageActive() {
        return pageActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
