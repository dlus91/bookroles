package com.bookroles.tool.dLog;

import com.bookroles.service.model.LogTotal;

import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 15:25
 */
public class GrokLogCallableImpl implements IGrokLogCallable {
    @Override
    public void pageCallAfter(List list) {
        LogTotal.parse(list);
    }
}
