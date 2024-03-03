package com.bookroles.mapper;

import com.bookroles.service.model.LogTotal;

import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/10/10 0:11
 */
public interface LogMapper {

    List<LogTotal> queryPageLog(LogTotal logTotal);

    int saveLog(LogTotal logTotal);

    int saveLogs(List<LogTotal> list);

    int updateLog(LogTotal logTotal);

    int deleteLog(LogTotal logTotal);

}
