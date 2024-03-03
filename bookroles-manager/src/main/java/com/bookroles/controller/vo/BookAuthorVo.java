package com.bookroles.controller.vo;

import com.bookroles.controller.valid.Group;
import com.bookroles.controller.valid.VoJudgeSQLInjectValid;
import com.bookroles.service.model.BookAuthor;
import com.bookroles.service.model.BookPublishHouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @Author: dlus91
 * @Date: 2023/10/6 22:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorVo {

    @Range(max = 300,message = "bookId的区间0-300",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "bookId的区间1-300",groups = {Group.Add.class,Group.Delete.class,Group.Update1.class,Group.Update2.class})
    private int bookId;

    @VoJudgeSQLInjectValid(max = 15,message = "书籍名称长度区间0-15",groups = {Group.Select.class})
    private String bookName;

    @Range(max = 300,message = "authorId的区间0-300",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "authorId的区间1-300",groups = {Group.Add.class,Group.Delete.class,Group.Update1.class,Group.Update2.class})
    private int authorId;

    @VoJudgeSQLInjectValid(max = 10,message = "作者名称长度区间0-10",groups = {Group.Select.class})
    private String authorName;

    @Range(min = 1,max = 300,message = "newBookId的区间1-300",groups = {Group.Update1.class})
    private int newBookId;

    @Range(min = 1,max = 300,message = "newAuthorId的区间1-300",groups = {Group.Update2.class})
    private int newAuthorId;

    public BookAuthor transformModel(Class groupClazz){
        BookAuthor bookAuthor = new BookAuthor(bookId,authorId);
        if(Group.Select.class == groupClazz){
            bookAuthor.setBookName(bookName);
            bookAuthor.setAuthorName(authorName);
        }else if(Group.Update1.class == groupClazz){
            bookAuthor.setNewBookId(newBookId);
        }else if(Group.Update2.class == groupClazz){
            bookAuthor.setNewAuthorId(newAuthorId);
        }
        return bookAuthor;
    }

}
