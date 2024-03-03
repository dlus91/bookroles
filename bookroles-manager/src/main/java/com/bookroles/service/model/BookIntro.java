package com.bookroles.service.model;

import com.bookroles.controller.vo.BookIntroVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dlus91
 * @Date: 2023/10/3 0:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookIntro {

    private int id;
    private int bookId;
    private String bookName;
    private String intro;
    private int canUse = 2;
    private long createTime;
    private long updateTime;

    public BookIntroVo transformVo() {
        BookIntroVo bookIntroVo = new BookIntroVo();
        bookIntroVo.setId(this.getId());
        bookIntroVo.setBookId(this.getBookId());
        bookIntroVo.setBookName(this.getBookName());
        bookIntroVo.setIntro(this.getIntro());
        bookIntroVo.setCanUse(this.getCanUse());
        return bookIntroVo;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());

    }

}
