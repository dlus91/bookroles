package com.bookroles.controller.vo;
import com.bookroles.controller.valid.Group;
import com.bookroles.controller.valid.VoJudgeSQLInjectValid;
import com.bookroles.service.model.Book;
import com.bookroles.service.model.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @Author: dlus91
 * @Date: 2023/9/4 10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVo {

    //id
    @Range(max = 1000,message = "id不正确",groups = {Group.Select.class})
    @Range(min = 1,max = 1000,message = "id区间只允许1-1000",groups = {Group.SelectOne.class,Group.Update.class})
    private int id = 0;
    //名称
    @Size(max = 10,message = "名称格式不正确",groups = {Group.Select.class})
    @Size(min = 2,max = 10,message = "名称格式只允许长度2-10",groups = {Group.Add.class,Group.Update.class})
    private String name;
    //图片
    @Pattern(regexp = "^\\d{12,14}.(png|jpg|jpeg|svg)$",message = "图片前缀12-14位数字,后缀为png|jpg|jpeg|svg",groups = {Group.Add.class,Group.Update.class})
    private String image;
    //评分区间 - 开始评分
    @Digits(integer = 1,fraction = 1,message = "评分区间只允许1位小数",groups = {Group.Select.class})
    @DecimalMin(value = "0.0",message = "评分区间从0.0开始",groups = {Group.Select.class})
    @DecimalMax(value = "5.0",message = "评分区间到5.0终止",groups = {Group.Select.class})
    private BigDecimal beginScore = BigDecimal.ZERO;
    //评分区间 - 结束评分
    @Digits(integer = 1,fraction = 1,message = "评分区间只允许1位小数",groups = {Group.Select.class})
    @DecimalMin(value = "0.0",message = "评分区间从0.0开始",groups = {Group.Select.class})
    @DecimalMax(value = "5.0",message = "评分区间到5.0终止",groups = {Group.Select.class})
    private BigDecimal endScore = BigDecimal.ZERO;
    //评分 0-5
    @Digits(integer = 1,fraction = 1,message = "评分区间只允许1位小数",groups = {Group.Add.class,Group.Update.class})
    @DecimalMin(value = "0.0",message = "评分区间从0.0开始",groups = {Group.Add.class,Group.Update.class})
    @DecimalMax(value = "5.0",message = "评分区间到5.0终止",groups = {Group.Add.class,Group.Update.class})
    private BigDecimal score = BigDecimal.ZERO;
    //下载格式
    @Pattern(regexp = "^(|EPUB|AZW3|MOBI|EPUB/AZW3|EPUB/MOBI|AZW3/MOBI|EPUB/AZW3/MOBI)$",message = "下载格式不正确,空或EPUB|AZW3|MOBI|EPUB/AZW3|EPUB/MOBI|AZW3/MOBI|EPUB/AZW3/MOBI",groups = {Group.Select.class})
    @Pattern(regexp = "^(EPUB|AZW3|MOBI|EPUB/AZW3|EPUB/MOBI|AZW3/MOBI|EPUB/AZW3/MOBI)$",message = "下载格式不正确,EPUB|AZW3|MOBI|EPUB/AZW3|EPUB/MOBI|AZW3/MOBI|EPUB/AZW3/MOBI",groups = {Group.Add.class,Group.Update.class})
    private String downloadFormat;
    //出版区间 - 开始时间 大于1990-01-01 小于2030-01-01
    @Range(max = 1893456000000L,message = "出版时间不正确,应小于2030-01-01",groups = {Group.Select.class})
    private long beginPublishTimestamp = 0L;
    //出版区间 - 结束时间 大于1990-01-01 小于2030-01-01
    @Range(max = 1893456000000L,message = "出版时间不正确,应小于2030-01-01",groups = {Group.Select.class})
    private long endPublishTimestamp = 0L;
    //出版月份 大于1990-01 小于2030-01
    @Pattern(regexp = "^|((199|20[0-2])\\d{1}/(0[1-9]|1[012]))$",message = "出版月份格式不正确,应大于1990/01 小于2030/01",groups = {Group.Select.class})
    @Pattern(regexp = "^((199|20[0-2])\\d{1}/(0[1-9]|1[012]))$",message = "出版月份格式不正确,应大于1990/01 小于2030/01",groups = {Group.Add.class,Group.Update.class})
    private String publishMonth;
    //初始关键字
    @VoJudgeSQLInjectValid(max = 10,message = "关键字长度区间0-10",groups = {Group.Add.class,Group.Update.class})
    private String keyWord;
    //上传区间 - 开始时间  大于2020-01-01 小于2030-01-01
    @Range(max = 1893456000000L,message = "上传区间不正确,应小于2030-01-01",groups = {Group.Select.class})
    private long beginUploadTime = 0L;
    //上传区间 - 结束时间  大于2020-01-01 小于2030-01-01
    @Range(max = 1893456000000L,message = "上传区间不正确,应小于2030-01-01",groups = {Group.Select.class})
    private long endUploadTime = 0L;
    //上传时间  大于2020-01-01 小于2030-01-01
    @Range(min = 1577836800000L,max = 1893456000000L,message = "上传区间不正确,应大于2020-01-01 小于2030-01-01",groups = {Group.Add.class,Group.Update.class})
    private long uploadTime;
    //最后更新时间
    private long lastTime;
    //存储类型-分类 id
    @Range(max = 15,message = "分类不存在",groups = {Group.Select.class})
    @Range(min = 1,max = 15,message = "分类区间1-15",groups = {Group.Add.class,Group.Update.class})
    private int archiveTypeId = 0;
    //需填充
    private String archiveTypeName;
    //默认全部 2
    @Range(max = 2,message = "上架字段只允许,0下架1上架2全部",groups = {Group.Select.class})
    @Range(min = 0,max = 1,message = "上架字段只允许,0下架1上架",groups = {Group.Add.class,Group.Update.class})
    private int open = 2;
    //作者
    //暂定最大300
    @Range(max = 300,message = "下载地址不存在",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "下载地址id区间1-300",groups = {Group.Add.class,Group.Update.class})
    private int downloadUrlId = 0;
    //detail的出参
    private String downloadUrl;
    //作者
    //暂定最大300
    @Range(max = 300,message = "内容简介不存在",groups = {Group.Select.class})
    @Range(min = 1,max = 300,message = "内容简介id区间1-300",groups = {Group.Add.class,Group.Update.class})
    private int introId = 0;
    //detail的出参
    private String intro;

    //入参
    @VoJudgeSQLInjectValid(max = 15,message = "作者名称长度区间0-10",groups = {Group.Select.class})
    private String authorName;
    //出参
    private String authors;
    //入参
    @VoJudgeSQLInjectValid(max = 15,message = "出版社名称长度区间0-10",groups = {Group.Select.class})
    private String publishHouseName;
    //出参
    private String publishHouses;

    public Book transformModel(Class groupClazz){
        Book book = new Book();
        if (Group.Select.class == groupClazz || Group.Add.class == groupClazz || Group.Update.class == groupClazz){
            if(Group.Select.class == groupClazz){
                book.setId(this.getId());
                book.setBeginScore(this.getBeginScore());
                book.setEndScore(this.getEndScore());
                book.setBeginPublishTimestamp(this.getBeginPublishTimestamp());
                book.setEndPublishTimestamp(this.getEndPublishTimestamp());
                book.setBeginUploadTime(this.getBeginUploadTime());
                book.setEndUploadTime(this.getEndUploadTime());
//                bookModel.setAuthorIds(this.getAuthorIds());
//                bookModel.setPublishHouseIds(this.getPublishHouseIds());
                book.setAuthorName(this.getAuthorName());
                book.setPublishHouseName(this.getPublishHouseName());

//                bookModel.setContentIntros(this.getContentIntros());
//                bookModel.setKeyWords(this.getKeyWords());
            }
            book.setName(this.getName());
            book.setImage(this.getImage());
            book.setDownloadFormat(this.getDownloadFormat());
            book.setPublishMonth(this.getPublishMonth());
            book.setKeyWord(this.getKeyWord());
            book.setArchiveTypeId(this.getArchiveTypeId());
            book.setOpen(this.getOpen());
//            bookModel.setAuthorId(this.getAuthorId());
//            bookModel.setPublishHouseId(this.getPublishHouseId());
            book.setDownloadUrlId(this.getDownloadUrlId());
            book.setIntroId(this.getIntroId());
            if(Group.Add.class == groupClazz || Group.Update.class == groupClazz){
                book.setScore(this.getScore());
                if(Group.Add.class == groupClazz){
                    book.setCreateTime(this.getUploadTime());
                }
                if(Group.Update.class == groupClazz){
                    book.setId(this.getId());
                }
            }

        } else if(Group.SelectOne.class == groupClazz){
            book.setId(this.getId());
        }
        return book;
    }

    public BookVo transformViewObject(){
        if(this.getArchiveTypeId() > 0){
            for (Dictionary archiveTypeDictionary : Dictionary.getDictionary().get(Dictionary.DictionaryEnum.ARCHIVE_TYPE.getName())){
                if(this.getArchiveTypeId() == archiveTypeDictionary.id()){
                    this.setArchiveTypeName(archiveTypeDictionary.name());
                }
            }
        }
//        if(this.getAuthorIdArray() != null && !this.getAuthorIdArray().isEmpty()){
//            StringBuilder authorName = new StringBuilder();
//            String[] authorArray = this.getAuthorIdArray().split(" ");
//            for (String author : authorArray){
//                if(author != null && !author.isEmpty()){
//                    int authorId = Integer.parseInt(author);
//                    for (Dictionary authorDictionary : Dictionary.getMap(Dictionary.DictionaryEnum.AUTHOR.getName())) {
//                        if (authorId == authorDictionary.id()) {
//                            authorName.append(author).append(":").append(authorDictionary.name()).append(" ");
//                            break;
//                        }
//                    }
//                }
//            }
//            this.setAuthorName(authorName.delete(authorName.length()-1, authorName.length()).toString());
//
//        }
//        if(this.getPublishHouseIdArray() != null && !this.getPublishHouseIdArray().isEmpty()){
//            StringBuilder publishHouseName = new StringBuilder();
//            String[] publishHouseArray = this.getPublishHouseIdArray().split(" ");
//            for (String publishHouse : publishHouseArray){
//                if(publishHouse != null && !publishHouse.isEmpty()){
//                    int publishHouseId = Integer.parseInt(publishHouse);
//                    for (Dictionary publishHouseDictionary : Dictionary.getMap(Dictionary.DictionaryEnum.PUBLISH_HOUSE.getName())) {
//                        if (publishHouseId == publishHouseDictionary.id()) {
//                            publishHouseName.append(publishHouse).append(":").append(publishHouseDictionary.name()).append(" ");
//                            break;
//                        }
//                    }
//                }
//            }
//            this.setPublishHouseName(publishHouseName.delete(publishHouseName.length()-1, publishHouseName.length()).toString());
//
//        }
        return this;
    }

    public static void main(String[] args) {
        String a = "2029/12";
        System.out.println(a);
        System.out.println(a.matches("^((199|20[0-2])[0-9]/(0[1-9]|1[012]))$"));
    }

}
