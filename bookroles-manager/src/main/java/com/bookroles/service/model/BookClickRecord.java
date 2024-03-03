package com.bookroles.service.model;

import com.bookroles.controller.vo.PageHelper;

/**
 * @Author: dlus91
 * @Date: 2023/9/4 10:02
 */
public class BookClickRecord extends PageHelper {

    private boolean pageActive = false;
    private int bookId;
    private String author;
    private int archiveTypeId;
    private String ipAddr;
    private int userId;
    private long currentTimestamp;

//    public static String fileName(){
//        return "book_click_record";
//    }
//
//    public static String headerString(){
//        return "book_id,author,archive_type_id,ip_addr,user_id,current_timestamp";
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getArchiveTypeId() {
        return archiveTypeId;
    }

    public void setArchiveTypeId(int archiveTypeId) {
        this.archiveTypeId = archiveTypeId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(long currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }
}
