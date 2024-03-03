package com.bookroles.service.model;

/**
 * @Author: dlus91
 * @Date: 2023/9/5 21:27
 */
public class BookSearchRecord {

    private boolean pageActive = false;
    private int word;
    private String ipAddr;
    private int userId;
    private long timestamp;

//    public static String fileName(){
//        return "book_search_record";
//    }
//
//    public static String headerString(){
//        return "word,ip_addr,user_ud,timestamp";
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

    public int getWord() {
        return word;
    }

    public void setWord(int Word) {
        this.word = word;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
