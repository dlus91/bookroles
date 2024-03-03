package com.bookroles.controller.vo;

import com.bookroles.controller.valid.Group;
import com.bookroles.service.model.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author: dlus91
 * @Date: 2023/10/5 23:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryVo {

    @Range(max = 1000,message = "id不正确",groups = {Group.Select.class})
    @Range(min = 1,max = 1000,message = "id区间1-1000",groups = {Group.Update.class})
    private int id = 0;
    @Size(max = 10,message = "名称格式不正确",groups = {Group.Select.class})
    @Size(min = 3,max = 10,message = "名称格式只允许长度3-10",groups = {Group.Add.class,Group.Update.class})
    private String name;
    @Pattern(regexp = "^|(ARCHIVE_TYPE|AUTHOR|PUBLISH_HOUSE|DICTIONARY|DOWNLOAD_FORMAT)$",message = "字典格式不正确,ARCHIVE_TYPE|AUTHOR|PUBLISH_HOUSE|DICTIONARY|DOWNLOAD_FORMAT",groups = {Group.Select.class})
    @Pattern(regexp = "^(ARCHIVE_TYPE|AUTHOR|PUBLISH_HOUSE|DICTIONARY|DOWNLOAD_FORMAT)$",message = "字典格式不正确,ARCHIVE_TYPE|AUTHOR|PUBLISH_HOUSE|DICTIONARY|DOWNLOAD_FORMAT",groups = {Group.Add.class,Group.Update.class})
    private String type;

    public Dictionary transformModel(){
        return new Dictionary(this.getId(),this.getName(),this.getType());
    }

}
