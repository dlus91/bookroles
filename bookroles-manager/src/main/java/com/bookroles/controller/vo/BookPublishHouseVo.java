package com.bookroles.controller.vo;

import com.bookroles.controller.valid.Group;
import com.bookroles.controller.valid.VoJudgeSQLInjectValid;
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
public class BookPublishHouseVo {

    @Range(max = 300,message = "bookId的区间0-300",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "bookId的区间1-300",groups = {Group.Add.class,Group.Delete.class,Group.Update1.class,Group.Update2.class})
    private int bookId = 0;

    @VoJudgeSQLInjectValid(max = 15,message = "书籍名称长度区间0-15",groups = {Group.Select.class})
    private String bookName;

    @Range(max = 300,message = "publishHouseId的区间0-300",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "publishHouseId的区间1-300",groups = {Group.Add.class,Group.Delete.class,Group.Update1.class,Group.Update2.class})
    private int publishHouseId = 0;

    @VoJudgeSQLInjectValid(max = 20,message = "出版社名称长度区间0-20",groups = {Group.Select.class})
    private String publishHouseName;

    @Range(min = 1,max = 300,message = "newBookId的区间1-300",groups = {Group.Update1.class})
    private int newBookId = 0;

    @Range(min = 1,max = 300,message = "newPublishHouseId的区间1-300",groups = {Group.Update2.class})
    private int newPublishHouseId = 0;

    public BookPublishHouse transformModel(Class groupClazz){
        BookPublishHouse bookPublishHouse = new BookPublishHouse(bookId, publishHouseId);
        if (Group.Select.class == groupClazz) {
            bookPublishHouse.setBookName(bookName);
            bookPublishHouse.setPublishHouseName(publishHouseName);
        } else if(Group.Update1.class == groupClazz){
            bookPublishHouse.setNewBookId(newBookId);
        }else if(Group.Update2.class == groupClazz){
            bookPublishHouse.setNewPublishHouseId(newPublishHouseId);
        }
        return bookPublishHouse;
    }


}
