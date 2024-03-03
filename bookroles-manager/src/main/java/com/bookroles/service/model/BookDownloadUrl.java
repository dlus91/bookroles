package com.bookroles.service.model;

import com.bookroles.controller.vo.BookDownloadUrlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dlus91
 * @Date: 2023/10/3 0:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDownloadUrl {

    private int id;
    private int bookId;
    private String bookName;
    private String url;
    private int canUse = 2;
    private long createTime;
    private long updateTime;

    public BookDownloadUrlVo transformVo() {
        BookDownloadUrlVo bookDownloadUrlVo = new BookDownloadUrlVo();
        bookDownloadUrlVo.setId(this.getId());
        bookDownloadUrlVo.setBookId(this.getBookId());
        bookDownloadUrlVo.setBookName(this.getBookName());
        bookDownloadUrlVo.setDownloadUrl(this.getUrl());
        bookDownloadUrlVo.setCanUse(this.getCanUse());
        return bookDownloadUrlVo;
    }

}
