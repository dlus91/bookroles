package com.bookroles.service.model;

import com.bookroles.controller.vo.BookAuthorVo;
import com.bookroles.controller.vo.BookPublishHouseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dlus91
 * @Date: 2023/10/6 21:53
 */
@Data
@NoArgsConstructor
public class BookPublishHouse {

    public BookPublishHouse(int bookId, int publishHouseId) {
        this.bookId = bookId;
        this.publishHouseId = publishHouseId;
    }

    private int bookId;
    private String bookName;
    private int publishHouseId;
    private String publishHouseName;

    private int newBookId;
    private int newPublishHouseId;

    public BookPublishHouseVo transformVo() {
        BookPublishHouseVo bookPublishHouseVo = new BookPublishHouseVo();
        bookPublishHouseVo.setBookId(this.getBookId());
        bookPublishHouseVo.setBookName(this.getBookName());
        bookPublishHouseVo.setPublishHouseId(this.getPublishHouseId());
        bookPublishHouseVo.setPublishHouseName(this.getPublishHouseName());
        return bookPublishHouseVo;
    }


}
