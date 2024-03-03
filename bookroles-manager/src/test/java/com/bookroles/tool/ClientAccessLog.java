package com.bookroles.tool;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: dlus91
 * @Date: 2023/10/9 1:00
 */
@Data
public class ClientAccessLog {

    String ip;
    String logDateTime;
    int httpStatus;
    String api;
    String params;
    String httpReferrer;
    BigDecimal runTime;
    int dataSize;
    String userAgent;

}
