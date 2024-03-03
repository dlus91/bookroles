package com.bookroles.tool;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.util.BeanUtil;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/10/8 21:04
 */
public class testJavaGrok {

    static int currentRecordNum = 0;

    static int ioCount = 1;

    static int logTotalCount = 0;

    static String fileName = "bookroles_access.2023-10-09.txt";

    @Test
    public void test1(){
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        //combined apache log
        System.out.println("%{COMBINEDAPACHELOG}");
        final Grok grok = grokCompiler.compile("%{COMBINEDAPACHELOG}");
        String log = "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\"";
        Match grokMatch = grok.match(log);
        final Map<String, Object> capture = grokMatch.capture();
        System.out.println(capture);
    }

    @Test
    public void test2(){
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        // 进行注册, registerDefaultPatterns()方法注册的是Grok内置的patterns
        grokCompiler.registerDefaultPatterns();
        Grok grok = grokCompiler.compile("%{IPV4:remote_addr}\\s*%{DATA:black}\\s*\\[%{HTTPDATE:access_time}\\]\\s*%{NOTSPACE:url}\\s*%{WORD:method}\\s*%{QUOTEDSTRING:api}\\s*%{NOTSPACE:cms}\\s*%{NUMBER:number}\\s*%{NUMBER:4}\\s*%{QUOTEDSTRING:http_referrer}\\s*%{NUMBER:eee}\\s*(?<date>\\d+\\.\\d+\\.\\d+\\.\\d+\\:\\d+)\\s*%{BASE16FLOAT:float}\\s*%{BASE16FLOAT:float1}\\s*%{GREEDYDATA:all}");

        String logMsg = "1.1.1.1 - - [06/Jun/2016:00:00:01 +0800] www.test.com GET \"/api/index\" \"?cms=0&rnd=1692442321\" 200 4 \"http://www.test.com/?cp=sfwefsc\" 200 192.168.0.122:80 0.004 0.004 \"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36\" \"-\"";

        // 通过match()方法进行匹配, 对log进行解析, 按照指定的格式进行输出
        Match grokMatch = grok.match(logMsg);
        // 获取结果
        Map<String, Object> resultMap = grokMatch.capture();
        System.out.println(resultMap);
    }


    //bookroles 管理端日志
    @Test
    public void test3(){
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        // 进行注册, registerDefaultPatterns()方法注册的是Grok内置的patterns
        grokCompiler.registerPatternFromClasspath("/tomcat-pattern");
        Grok grok = grokCompiler.compile("%{TOMCATDATE: dateTime},%{QUOTEDSTRING:api},(%{DATA:params}|%{GREEDYDATA:params}),%{NOTSPACE:httpStatus},%{IP:ip},%{NUMBER:runTime},%{LEAVEL:black2},%{NUMBER:dataSize},%{QUOTEDSTRING:httpReferrer},%{GREEDYDATA:userAgent}");

        String logMsg = "2023-10-09 10:02:09.133,\"GET /system/book/detail?id=1 HTTP/1.1\",-,200,0:0:0:0:0:0:0:1,0.580,+,88510,\"http://localhost:8080/system/book/queryPage\",\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36\"";

        // 通过match()方法进行匹配, 对log进行解析, 按照指定的格式进行输出
        Match grokMatch = grok.match(logMsg);
        // 获取结果
        Map<String, Object> resultMap = grokMatch.capture();
        System.out.println(resultMap);
    }


    //bookroles 客户端日志
    @Test
    public void test4() throws InvocationTargetException, IllegalAccessException {
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerPatternFromClasspath("/tomcat-pattern");
        //
        Grok grok = grokCompiler.compile("%{TOMCATDATE: dateTime},%{IP:ip},%{WORD:method},%{QUOTEDSTRING:url},%{WORDVERSION:httpVersion},%{WORDORNULL:sessionKey},%{LEAVEL:predictedValue},%{GREEDYDATA:params},%{NOTSPACE:httpStatus},%{NUMBER:runTime},%{NUMBER:dataSize},%{QUOTEDSTRING:httpReferrer},%{GREEDYDATA:userAgent}");
        String logMsg = "2023-10-11 13:34:09.676,0:0:0:0:0:0:0:1,POST,\"/bookroles/book/findByArchiveType.do\",HTTP/1.1,A5D35D28A86F23B3CC1B8D87A9FCD9B2,+,{\"archiveTypeId\":\"14\",\"row\":1},200,8,1432,\"http://localhost:8090/bookroles/\",\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36\"";
        Match grokMatch = grok.match(logMsg);
        // 获取结果
        Map<String, Object> resultMap = grokMatch.capture();
        System.out.println(resultMap);
        //map序列化为 对象
//        ClientAccessLog clientAccessLog = JSONObject.parseObject(JSONObject.toJSONString(resultMap), ClientAccessLog.class);
//        System.out.println(clientAccessLog);
    }


    @Test
    public void test5() throws IOException {
        long beginTime = System.currentTimeMillis();
        //获取总条数
        logTotalCount = getLogTotalCount();
        System.out.println("总条数为："+logTotalCount);
        //分10次 每次获取10条数据
        for (int i = 0; i < 20; i++) {
            System.out.printf("第%d页数据:\r\n", i+1);
//            List<ClientAccessLog> logData = getLogData(3);
            List<Map<String, Object>> logData = getLogMapData(15);
            if(logData == null){
                break;
            }
            System.out.printf("第%d页数据量为%d:\r\n", i+1,logData.size());
            logData.forEach(System.out::println);
        }
        System.out.println("任务结束后，当前条数为："+currentRecordNum);
        System.out.printf("总耗时为：%dms\r\n",(System.currentTimeMillis() - beginTime));
    }

    //不序列为对象，效率低下，而且架构设计为 多维OLAP数据模型，日志不进数据库进行规范化，只从中获取有用数据进行分析
    //留下该方法只是为了说明可以进行序列化对象 这个功能可以实现 仅此而已
    @Deprecated(forRemoval = true)
    private static List<ClientAccessLog> getLogData(int pageSize) throws IOException {
        List<ClientAccessLog> list = new ArrayList();
        String[] logMsgs = getLog(pageSize).split("\r\n");
        System.out.println("io次数：" + ioCount++);
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerPatternFromClasspath("/tomcat-pattern");
        Grok grok = grokCompiler.compile("%{TOMCATDATE:logDateTime},%{QUOTEDSTRING:api},%{GREEDYDATA:params},%{NOTSPACE:httpStatus},%{IP:ip},%{NUMBER:runTime},%{LEAVEL:black},%{NUMBER:dataSize},%{QUOTEDSTRING:httpReferrer},%{GREEDYDATA:userAgent}");
        for (String logMsg : logMsgs) {
            Match grokMatch = grok.match(logMsg);
            ClientAccessLog clientAccessLog = JSONObject.parseObject(JSONObject.toJSONString(grokMatch.captureFlattened()), ClientAccessLog.class);
            list.add(clientAccessLog);
        }
        return list;
    }

    private static List<Map<String, Object>> getLogMapData(int pageSize) throws IOException {
        List<Map<String, Object>> list = new ArrayList();
        String log = getLog(pageSize);
        if(log == null){
            return null;
        }
        String[] logMsgs = log.split("\r\n");
        System.out.println("io次数：" + ioCount++);
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerPatternFromClasspath("/tomcat-pattern");
//        Grok grok = grokCompiler.compile("%{TOMCATDATE: dateTime},%{IP:ip},%{WORD:method},%{QUOTEDSTRING:url},%{WORDVERSION:httpVersion},%{WORDORNULL:sessionKey},%{LEAVEL:predictedValue},%{GREEDYDATA:params},%{NOTSPACE:httpStatus},%{NUMBER:runTime},%{NUMBER:dataSize},%{QUOTEDSTRING:httpReferrer},%{GREEDYDATA:userAgent}");
        Grok grok = grokCompiler.compile("%{BOOKROLES_BUSSINESS}");
        for (String logMsg : logMsgs) {
            Match grokMatch = grok.match(logMsg);
            Map<String, Object> capture = grokMatch.capture();
            list.add(capture);
        }
        return list;
    }


    private static String getLog(int pageSize) throws IOException {
        String clientLogPath = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.clientLogPath").toString();
        String clientLog = clientLogPath + fileName;
        int beginNum = 0;
        int endNum = pageSize;
        if(currentRecordNum > 0){
            beginNum = currentRecordNum;
            endNum = currentRecordNum + pageSize;
        }
        if(beginNum >= logTotalCount){
            System.out.println("日志已经遍历完成，不再进行io操作！");
            return null;
        }
        StringBuilder log = new StringBuilder();
        try {
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(clientLog), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            int num = 0;
            while ((line = reader.readLine()) != null) {
                if (num >= beginNum && num < endNum ){
                    currentRecordNum++;
                    log.append(line).append("\r\n");
                } else if (num == endNum) {
                    break;
                }
                num++;
            }
            System.out.println("该此io操作，遍历数据量为："+num);
            reader.close();
            fReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return log.toString();
    }

    private static int getLogTotalCount() throws IOException {
        String clientLogPath = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.clientLogPath").toString();
        String clientLog = clientLogPath + fileName;
        int num = 0;
        try {
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(clientLog), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(fReader);
            while (reader.readLine() != null) {
                num++;
            }
            reader.close();
            fReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }



}
