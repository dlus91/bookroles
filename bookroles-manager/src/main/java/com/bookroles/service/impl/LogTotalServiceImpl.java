package com.bookroles.service.impl;

import com.bookroles.mapper.DictionaryMapper;
import com.bookroles.mapper.LogMapper;
import com.bookroles.service.ILogTotalService;
import com.bookroles.service.model.LogTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: dlus91
 * @Date: 2023/10/10 0:57
 */
@Service
public class LogTotalServiceImpl implements ILogTotalService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public int addLogTotal(LogTotal logTotal) {
        return logMapper.saveLog(logTotal);
    }
}
