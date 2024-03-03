package com.bookroles.tool;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

/**
 * @Author: dlus91
 * @Date: 2023/10/8 20:57
 */
@Data
public class BookClick {

    @CsvBindByName(column = "id")
    int id;
    @CsvBindByName(column = "name")
    int count;
    @CsvBindByName(column = "timestamp")
    long timestamp;


}
