package com.bookroles.vo;

import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/9/3 19:32
 */
//默认启动 第一页 一页12条 总页数为1页
public class PageHelper {

    private int pageNum = 1;
    private int pageSize = 12;
    private int totalPageCount = 1;
    private List list = null;

//    public PageHelper(int pageNum, int pageSize, int totalPageCount, List list) {
//        this.pageNum = pageNum;
//        this.pageSize = pageSize;
//        this.totalPageCount = totalPageCount;
//        this.list = list;
//    }

    public int getPageNum() {
        return pageNum;
    }

    /**
     * 从1开始
     * @param pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
