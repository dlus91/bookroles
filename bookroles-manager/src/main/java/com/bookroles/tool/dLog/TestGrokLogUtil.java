package com.bookroles.tool.dLog;

import com.bookroles.service.model.LogTotal;
import com.bookroles.tool.LogEnum;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/10/12 18:12
 */
public class TestGrokLogUtil{


    //单线程 单个文件操作
    @Test
    public void test1(){
        long beginTime = System.currentTimeMillis();
        String fileName = "bookroles_access.2023-10-11.txt";
        //无线程操作
        GrokLogUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogUtil logUtil = GrokLogUtil.ready(fileName, 5, 10);
        List<Map<String, Object>> lists = logUtil.getPageLogData();
        System.out.println("================================");
        LogTotal.parse(lists);
        System.out.println(LogTotal.logTotalMap);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }


    @Test
    public void test2(){
        long beginTime = System.currentTimeMillis();
        String fileName = "bookroles_access.2023-10-13.txt";
        //无线程操作
        GrokLogUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogUtil logUtil = GrokLogUtil.ready(fileName, 0, 3000);
        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("httpStatus", "200");
        paramMap.put("userAgent", "AppleWebKit");
        List<Map<String, Object>> lists = logUtil.getPageLogDataWithMap(paramMap);
        System.out.println("================================");
        LogTotal.parse(lists);
        System.out.println(LogTotal.logTotalMap);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }


    @Test
    public void test20(){
        long beginTime = System.currentTimeMillis();
        String fileName = "bookroles_access.2023-10-13.txt";
        //无线程操作
        GrokLogUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogUtil logUtil = GrokLogUtil.ready(fileName, 5, 3000);
        List<Map<String, Object>> lists = logUtil.getPageLogDataWithRange("httpStatus", 200, 299);
        System.out.println("================================");
        LogTotal.parse(lists);
        System.out.println(LogTotal.logTotalMap);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }

    @Test
    public void test21(){
        long beginTime = System.currentTimeMillis();
        String fileName = "bookroles_access.2023-10-13.txt";
        //无线程操作
        GrokLogUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogUtil logUtil = GrokLogUtil.ready(fileName, 0, 3000);
        List<Map<String, Object>> lists = logUtil.getPageLogDataWithGreaterThan("runTime", 500);
        System.out.println("================================");
//        System.out.println("Lists: " + lists);
        LogTotal.parse(lists);
        System.out.println(LogTotal.logTotalMap);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }


    //多线程 单个文件操作
    @Test
    public void test3(){
        long beginTime = System.currentTimeMillis();
        String fileName = "bookroles_access.2023-10-11.txt";
        GrokLogWithExecutorUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogWithExecutorUtil grokLogWithExecutorUtil = GrokLogWithExecutorUtil.ready(fileName);
        List<Map<String, Object>> lists = grokLogWithExecutorUtil.getLogData(0,50);
        LogTotal.parse(lists);
        System.out.println(LogTotal.logTotalMap);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }


    @Test
    public void test31(){
        long beginTime = System.currentTimeMillis();
        String fileName = "bookroles_access.2023-10-11.txt";
        GrokLogWithExecutorUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogWithExecutorUtil grokLogWithExecutorUtil = GrokLogWithExecutorUtil.ready(fileName);
        List<Map<String, Object>> lists = grokLogWithExecutorUtil.getLogData(0,20);
        LogTotal.parse(lists);
        System.out.println(LogTotal.logTotalMap);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }

    //多线程 多个文件操作
    @Test
    public void test4(){
        long beginTime = System.currentTimeMillis();
        String fileName1 = "bookroles_access.2023-10-11.txt";
        String fileName2 = "bookroles_access.2023-10-16.txt";
        GrokLogWithExecutorUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogWithExecutorUtil grokLogWithExecutorUtil = GrokLogWithExecutorUtil.readyBetweent(fileName1, fileName2);

        System.out.println(GrokLogWithExecutorUtil.paramMap);

        Map<String, Map<String, Integer>> paramMap = GrokLogWithExecutorUtil.paramMap;
        Map<String, Integer> map1 = new HashMap<>();
        map1.put(GrokLogWithExecutorUtil.START_NUM, 30);
        map1.put(GrokLogWithExecutorUtil.PAGE_SIZE, 30);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put(GrokLogWithExecutorUtil.START_NUM, 20);
        map2.put(GrokLogWithExecutorUtil.PAGE_SIZE, 30);
        paramMap.put(fileName1,map1);
        paramMap.put(fileName2, map2);

        List<Map<String, Object>> lists = grokLogWithExecutorUtil.getLogData();
        System.out.println("lists.size():" + lists.size());
        LogTotal.parse(lists);
        System.out.println(LogTotal.logTotalMap);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
        System.out.println(GrokLogWithExecutorUtil.paramMap);
    }

    //多线程 多个文件操作
    @Test
    public void test41(){
        long beginTime = System.currentTimeMillis();
        String fileName = "bookroles_access.2023-10-11.txt";
        String fileName2 = "bookroles_access.2023-10-12.txt";

        GrokLogWithExecutorUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        GrokLogWithExecutorUtil grokLogWithExecutorUtil = GrokLogWithExecutorUtil.readys(fileName,fileName2);
        List<Map<String, Object>> lists = grokLogWithExecutorUtil.getLogData(0,30,new GrokLogCallableImpl());
        System.out.println(LogTotal.logTotalMap);
        System.out.println(LogTotal.logTotalMap.size());
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }

    @Test
    public void test5(){
        int i = 0;
        do{
            System.out.println(i);
            i++;
        }while (i < 3);
    }

    @Test
    public void test6(){
        String ss = "asd";
        int i = 0;
        try {
            i = Integer.parseInt(ss);
        } catch (NumberFormatException e) {
            throw new RuntimeException("转换失败或者数据小于等于0");
        }
        System.out.println(i);
    }

    @Test
    public void test7(){
        String ss = "2131231";
        System.out.println(StringUtils.isNumeric(ss));
    }

    @Test
    public void test8(){
        System.out.println(Integer.MAX_VALUE);
    }


}
