package com.bookroles.service.model;

import com.bookroles.controller.vo.BookArchiveTypeVo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dlus91
 * @Date: 2023/10/7 20:02
 */
@Data
@NoArgsConstructor
public class BookArchiveType {

    public BookArchiveType(int bookId, int archiveTypeId) {
        this.bookId = bookId;
        this.archiveTypeId = archiveTypeId;
    }

    private int bookId;
    private String bookName;
    private int archiveTypeId;
    private String archiveTypeName;
    private int bookUse;


    private int newBookId;
    private int newArchiveTypeId;

    public BookArchiveTypeVo transformVo() {
        BookArchiveTypeVo bookArchiveTypeVo = new BookArchiveTypeVo();
        bookArchiveTypeVo.setBookId(this.getBookId());
        bookArchiveTypeVo.setBookName(this.getBookName());
        bookArchiveTypeVo.setArchiveTypeId(this.getArchiveTypeId());
        bookArchiveTypeVo.setArchiveTypeName(this.getArchiveTypeName());
        return bookArchiveTypeVo;
    }

}
