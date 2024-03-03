package com.bookroles.controller.vo;

import com.bookroles.controller.valid.Group;
import com.bookroles.controller.valid.VoJudgeSQLInjectValid;
import com.bookroles.service.model.BookDownloadUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @Author: dlus91
 * @Date: 2023/10/3 0:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDownloadUrlVo {

    @Range(max = 1000,message = "id不正确",groups = {Group.Select.class})
    @Range(min = 1,max = 1000,message = "id区间1-1000",groups = {Group.Update.class})
    private int id = 0;

    @Range(max = 1000,message = "bookId不正确",groups = {Group.Select.class})
    @Range(min = 1,max = 1000,message = "book_id区间1-1000",groups = {Group.Add.class,Group.Update.class})
    private int bookId = 0;

    @VoJudgeSQLInjectValid(max = 15,message = "书籍名称长度区间0-15",groups = {Group.Select.class})
    private String bookName;

    @VoJudgeSQLInjectValid(max = 30,message = "下载路径长度区间0-30",groups = {Group.Select.class})
    @VoJudgeSQLInjectValid(min = 5,max = 30,message = "下载路径长度区间5-30",groups = {Group.Add.class,Group.Update.class})
    private String downloadUrl;

    //该值add/update时强制为0，因为有触发器存在
    @Range(max = 2,message = "是否上架只能是0/1/2",groups = {Group.Select.class})
    private int canUse = 2;

    //上传区间 - 开始时间  大于2020-01-01 小于2030-01-01
    @Range(max = 1893456000000L,message = "上传区间不正确,应小于2030-01-01",groups = {Group.Select.class,Group.Add.class})
    private long uploadTime = 0L;

    //上传区间 - 开始时间  大于2020-01-01 小于2030-01-01
    @Range(max = 1893456000000L,message = "上传区间不正确,应小于2030-01-01",groups = {Group.Select.class})
    private long lastTime = 0L;

    public BookDownloadUrl transformModel(Class groupClazz){
        BookDownloadUrl bookDownloadUrl = new BookDownloadUrl();
        if(Group.Select.class == groupClazz) {
            bookDownloadUrl.setId(this.getId());
            bookDownloadUrl.setBookId(this.getBookId());
            bookDownloadUrl.setBookName(this.getBookName());
            bookDownloadUrl.setUrl(this.getDownloadUrl());
            bookDownloadUrl.setCanUse(this.getCanUse());
            bookDownloadUrl.setCreateTime(this.getUploadTime());
            bookDownloadUrl.setUpdateTime(this.getLastTime());
        } else if (Group.Add.class == groupClazz) {
            bookDownloadUrl.setBookId(this.getBookId());
            bookDownloadUrl.setUrl(this.getDownloadUrl());
            bookDownloadUrl.setCreateTime(this.getUploadTime());
        } else if (Group.Update.class == groupClazz) {
            bookDownloadUrl.setId(this.getId());
            bookDownloadUrl.setBookId(this.getBookId());
            bookDownloadUrl.setUrl(this.getDownloadUrl());
        }
        return bookDownloadUrl;
    }


}
