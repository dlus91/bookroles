package com.bookroles.service.model;


import com.bookroles.controller.vo.BookVo;
import lombok.*;
import java.math.BigDecimal;

/**
 * @Author: dlus91
 * @Date: 2023/9/4 10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private boolean pageActive = true;
    //id
    private int id = 0;
    //名称
    private String name;
    //图片
    private String image;
    //评分区间 - 开始评分
    private BigDecimal beginScore = BigDecimal.ZERO;
    //评分区间 - 结束评分
    private BigDecimal endScore = BigDecimal.ZERO;
    //评分 0-5
    private BigDecimal score = BigDecimal.ZERO;
    //下载格式
    private String downloadFormat;
    //出版区间 - 开始时间 大于1990-01-01 小于2030-01-01
    private long beginPublishTimestamp = 0L;
    //出版区间 - 结束时间 大于1990-01-01 小于2030-01-01
    private long endPublishTimestamp = 0L;
    //出版月份 大于1990-01 小于2030-01
    private String publishMonth;
    //初始关键字
    private String keyWord;
    //初始关键字
//    private Set<String> keyWords;
    //上传区间 - 开始时间  大于2020-01-01 小于2030-01-01
    private long beginUploadTime = 0L;
    //上传区间 - 结束时间  大于2020-01-01 小于2030-01-01
    private long endUploadTime = 0L;
    //上传时间
    private long createTime;
    //最后更新时间
    private long updateTime;

    //存储类型-分类 id
    private int archiveTypeId = 0;
    //默认全部 2
    private int open = 2;
    private int downloadUrlId = 0;
    //detail的出参
    private String downloadUrl;
    private int introId = 0;
    //detail的出参
    private String intro;

    private String authorName;
    private String authors;
    private String publishHouseName;
    private String publishHouses;

    public BookVo transformVo(){
        BookVo bookVo = new BookVo();
        bookVo.setId(this.getId());
        bookVo.setName(this.getName());
        bookVo.setImage(this.getImage());
        bookVo.setBeginScore(this.getBeginScore());
        bookVo.setEndScore(this.getEndScore());
        bookVo.setScore(this.getScore());
        bookVo.setDownloadFormat(this.getDownloadFormat());
        bookVo.setBeginPublishTimestamp(this.getBeginPublishTimestamp());
        bookVo.setEndPublishTimestamp(this.getEndPublishTimestamp());
        bookVo.setPublishMonth(this.getPublishMonth());
        bookVo.setKeyWord(this.getKeyWord());
        bookVo.setBeginUploadTime(this.getBeginUploadTime());
        bookVo.setEndUploadTime(this.getEndUploadTime());
        bookVo.setUploadTime(this.getCreateTime());
        bookVo.setLastTime(this.getUpdateTime());
        bookVo.setArchiveTypeId(this.getArchiveTypeId());
        bookVo.setOpen(this.getOpen());
        bookVo.setDownloadUrlId(this.getDownloadUrlId());
        bookVo.setDownloadUrl(this.getDownloadUrl());
        bookVo.setIntroId(this.getIntroId());
        bookVo.setIntro(this.getIntro());

        bookVo.setAuthorName(this.getAuthorName());
        bookVo.setAuthors(this.getAuthors());
        bookVo.setPublishHouseName(this.getPublishHouseName());
        bookVo.setPublishHouses(this.getPublishHouses());
        return bookVo;
    }





}
