package com.bookroles.service.model;

import com.bookroles.tool.LogEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: dlus91
 * @Date: 2023/10/10 0:07
 */
@Data
@NoArgsConstructor
public class LogTotal {

    private final static int overTime = 1000;
    public static Map<String, LogTotal> logTotalMap = new ConcurrentHashMap<>();

    private String day;
    private String logName;
    private int totalCount = 0;
    private int getCount = 0;
    private int putCount = 0;
    private int postCount = 0;
    private int deleteCount = 0;
    private int successCount = 0;
    private int foundCount = 0;
    private int failCount = 0;
    private int errorCount = 0;
    private int done = 0;
    private int doneCount = 0;
    private int ipCount = 0;
    private int useTime = 0;
    private int maxUseTime = 0;
    private int maxUseTimeId = 0;

    private int overCount = 0;
    private int overUseTime = 0;
    private String overIdArray;

    private long beginCreateTime = 0L;
    private long endCreateTime = 0L;
    private long createTime;
    private long beginUpdateTime = 0L;
    private long endCUpdateTime = 0L;
    private long updateTime;

    public static void flush(){
        logTotalMap = null;
    }


    /**
     * 合并list的数据 同一天同个文件的数据！！！
     * @param list 需合并的List
     * @return
     */
    public static LogTotal mergeList(List<LogTotal> list){
        LogTotal logTotal = new LogTotal();
        if(list != null && !list.isEmpty()){
            for (LogTotal total : list) {
                logTotal.setTotalCount(logTotal.getTotalCount() + total.getTotalCount());
                logTotal.setGetCount(logTotal.getGetCount() + total.getGetCount());
                logTotal.setPutCount(logTotal.getPutCount() + total.getPutCount());
                logTotal.setPostCount(logTotal.getPostCount() + total.getPostCount());
                logTotal.setDeleteCount(logTotal.getDeleteCount() + total.getDeleteCount());
                logTotal.setSuccessCount(logTotal.getSuccessCount() + total.getSuccessCount());
                logTotal.setFoundCount(logTotal.getFoundCount() + total.getFoundCount());
                logTotal.setFailCount(logTotal.getFailCount() + total.getFailCount());
                logTotal.setErrorCount(logTotal.getErrorCount() + total.getErrorCount());
                logTotal.setDoneCount(logTotal.getDoneCount() + total.getDoneCount());
                logTotal.setIpCount(logTotal.getIpCount() + total.getIpCount());
                logTotal.setUseTime(logTotal.getUseTime() + total.getUseTime());
            }
            logTotal.setCreateTime(System.currentTimeMillis());
            return logTotal;
        }
        return null;
    }

    private static LogTotal convertLogTotal(LogTotal logTotal, Map<String, Object> map, String dateStr, String logName, int ipCount){
        logTotal.setDay(dateStr);
        logTotal.setLogName(logName);
        logTotal.setTotalCount(logTotal.getTotalCount() + 1);
        logTotal.setDoneCount(logTotal.getDoneCount() + 1);
        logTotal.setIpCount(ipCount);
        int logRunTime = Integer.parseInt(map.get("runTime").toString());
        logTotal.setUseTime(logTotal.getUseTime() + logRunTime);
        int num = Integer.parseInt(map.get("num").toString()) + 1;
        if(logRunTime > logTotal.getMaxUseTime()){
            logTotal.setMaxUseTime(logRunTime);
            logTotal.setMaxUseTimeId(num);
        }
        if(logRunTime > overTime){
            logTotal.setOverCount(logTotal.getOverCount() + 1);
            logTotal.setOverUseTime(logTotal.getOverUseTime() + logRunTime);
            if(logTotal.getOverIdArray() == null){
                logTotal.setOverIdArray(String.valueOf(num));
            }else{
                logTotal.setOverIdArray(logTotal.getOverIdArray() +" "+ num);
            }
        }
        switch (String.valueOf(map.get("method"))){
            case "GET" -> logTotal.setGetCount(logTotal.getGetCount() + 1);
            case "PUT" -> logTotal.setPutCount(logTotal.getPutCount() + 1);
            case "POST" -> logTotal.setPostCount(logTotal.getPostCount() + 1);
            case "DELETE" -> logTotal.setDeleteCount(logTotal.getDeleteCount() + 1);
        }
        int httpStatus = Integer.valueOf(map.get("httpStatus").toString());
        if (httpStatus < 300 && httpStatus >= 200) {
            logTotal.setSuccessCount(logTotal.getSuccessCount() + 1);
        } else if (httpStatus < 400 && httpStatus >= 300) {
            logTotal.setFoundCount(logTotal.getFoundCount() + 1);
        } else if (httpStatus < 500 && httpStatus >= 400) {
            logTotal.setFailCount(logTotal.getFailCount() + 1);
        } else if (httpStatus >= 500) {
            logTotal.setErrorCount(logTotal.getErrorCount() + 1);
        }
        return logTotal;
    }


    public static void parse(List<Map<String, Object>> list){
        Map<String, Integer> ipMap = new HashMap();
        int i = 0;
        for (Map<String, Object> map : list) {
            i++;
            if(map == null){
                System.out.println(i);
            }
            if(map.get("dateTime") == null){
                System.out.println(i);
                continue;
            }
            String dateStr = map.get("dateTime").toString().substring(0, 10);
            String logName = LogEnum.BUSSINESS_TOMCAT_ACCESS.getPrefix() + dateStr + LogEnum.BUSSINESS_TOMCAT_ACCESS.getSuffix();
            String ipStr = String.valueOf(map.get("ip"));
            if(logTotalMap.containsKey(logName)){
                int ipCount = ipMap.get(ipStr) == null ? 0 : ipMap.get(ipStr) + 1;
                ipMap.put(ipStr, ipCount);
                LogTotal logTotal = logTotalMap.get(logName);
                convertLogTotal(logTotal, map, dateStr, logName, ipMap.size());
            }else{
                ipMap.put(ipStr, 1);
                LogTotal logTotal = new LogTotal();
                logTotalMap.put(logName, logTotal);
                convertLogTotal(logTotal, map, dateStr, logName, 1);

            }
        }

    }


}
