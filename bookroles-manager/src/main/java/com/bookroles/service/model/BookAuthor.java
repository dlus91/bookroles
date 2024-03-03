package com.bookroles.service.model;

import com.bookroles.controller.vo.BookAuthorVo;
import com.bookroles.controller.vo.BookIntroVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dlus91
 * @Date: 2023/10/6 21:51
 */
@Data
@NoArgsConstructor
public class BookAuthor {

    public BookAuthor(int bookId, int authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    private int bookId;
    private String bookName;
    private int authorId;
    private String authorName;

    private int newBookId;
    private int newAuthorId;

    public BookAuthorVo transformVo() {
        BookAuthorVo bookAuthorVo = new BookAuthorVo();
        bookAuthorVo.setBookId(this.getBookId());
        bookAuthorVo.setBookName(this.getBookName());
        bookAuthorVo.setAuthorId(this.getAuthorId());
        bookAuthorVo.setAuthorName(this.getAuthorName());
        return bookAuthorVo;
    }

}
