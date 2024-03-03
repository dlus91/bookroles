package com.bookroles.model;

import com.bookroles.vo.PageHelper;

/**
 * @Author: dlus91
 * @Date: 2023/9/4 11:02
 */
public class ArchiveType extends PageHelper {

    private boolean pageActive = false;
    private int id;
    private String name;

    public static String fileName(){
        return "archive_type";
    }

    public static String headerString(){
        return "id,name";
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
