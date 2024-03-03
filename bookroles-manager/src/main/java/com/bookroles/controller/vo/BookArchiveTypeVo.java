package com.bookroles.controller.vo;

import com.bookroles.controller.valid.Group;
import com.bookroles.controller.valid.VoJudgeSQLInjectValid;
import com.bookroles.service.model.BookArchiveType;
import com.bookroles.service.model.BookPublishHouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @Author: dlus91
 * @Date: 2023/10/7 19:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookArchiveTypeVo {

    @Range(max = 300,message = "bookId的区间0-300",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "bookId的区间1-300",groups = {Group.Add.class,Group.Delete.class,Group.Update1.class,Group.Update2.class})
    private int bookId;

    @VoJudgeSQLInjectValid(max = 15,message = "书籍名称长度区间0-15",groups = {Group.Select.class})
    private String bookName;

    @Range(max = 300,message = "archiveTypeId的区间0-300",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "archiveTypeId的区间1-300",groups = {Group.Add.class,Group.Delete.class,Group.Update1.class,Group.Update2.class})
    private int archiveTypeId;

    @VoJudgeSQLInjectValid(max = 15,message = "分类名称长度区间0-15",groups = {Group.Select.class})
    private String archiveTypeName;

    @Range(min = 1,max = 300,message = "newBookId的区间1-300",groups = {Group.Update1.class})
    private int newBookId;

    @Range(min = 1,max = 300,message = "newArchiveTypeId的区间1-300",groups = {Group.Update2.class})
    private int newArchiveTypeId;

    @Range(max = 2,message = "是否使用只能是0/1/2",groups = {Group.Select.class})
    private int bookUse = 2;

    public BookArchiveType transformModel(Class groupClazz){
        BookArchiveType bookArchiveType = new BookArchiveType(bookId, archiveTypeId);
        if(Group.Select.class == groupClazz){
            bookArchiveType.setBookName(bookName);
            bookArchiveType.setArchiveTypeName(archiveTypeName);
            bookArchiveType.setBookUse(bookUse);
        }else if(Group.Update1.class == groupClazz){
            bookArchiveType.setNewBookId(newBookId);
        }else if(Group.Update2.class == groupClazz){
            bookArchiveType.setNewArchiveTypeId(newArchiveTypeId);
        }
        return bookArchiveType;
    }


}
