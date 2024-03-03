package com.bookroles.tool;

import com.bookroles.service.model.LogTotal;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: dlus91
 * @Date: 2023/10/11 15:27
 */
public class Test10 {

    List<LogTotal> logList;

    @Before
    public void init(){
        LogTotal log1 = new LogTotal();
        log1.setDay("2023/10/09");
        log1.setLogName("aaa");
        log1.setDoneCount(10);
        log1.setTotalCount(5);
        LogTotal log2 = new LogTotal();
        log2.setDay("2023/10/10");
        log2.setLogName("bbb");
        log2.setDoneCount(20);
        log2.setTotalCount(3);
        LogTotal log3 = new LogTotal();
        log3.setDay("2023/10/10");
        log3.setLogName("aaa");
        log3.setDoneCount(30);
        log3.setTotalCount(32);
        LogTotal log4 = new LogTotal();
        log4.setDay("2023/10/10");
        log4.setLogName("aaa");
        log4.setDoneCount(15);
        log4.setTotalCount(13);
        logList = List.of(log1, log2, log3, log4);
    }


    @Test
    public void test1(){

        Map<String, List<LogTotal>> resultMap =
                logList.stream()
                        .collect(Collectors.groupingBy(LogTotal::getDay));
        System.out.println(resultMap);
    }

    @Test
    public void test2(){
        Map<String, Integer> resultMap = logList.stream()
                .collect(Collectors.groupingBy(LogTotal::getDay, Collectors.summingInt(LogTotal::getDoneCount)));
        System.out.println(resultMap);
    }

    @Test
    public void test3(){

        Map<String, Map<String, Integer>> collect = logList.stream()
                .collect(Collectors.groupingBy(
                                LogTotal::getDay,
                                Collectors.groupingBy(
                                        LogTotal::getLogName,
                                        Collectors.summingInt(log -> {
                                            return log.getDoneCount();
                                        })
                                )
                        )
                );
        System.out.println(collect);

    }

    @Test
    public void test4(){
        Map<String, List<LogTotal>> resultMap =
                logList.stream()
                        .collect(Collectors.groupingBy(LogTotal::getDay));
        System.out.println(resultMap);
    }

    @Test
    public void test5(){
    }
}
