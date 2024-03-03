package com.bookroles.tool.dLog;

import com.bookroles.exception.AssertUtil;
import com.bookroles.tool.LogEnum;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

/**
 * @Author: dlus91
 * @Date: 2023/10/12 18:17
 */
public class GrokLogWithExecutorUtil {

    Logger logger = LoggerFactory.getLogger(GrokLogWithExecutorUtil.class);

    private static ExecutorService executorService;

    private static GrokCompiler taskGrokCompiler;

    private static Grok taskGrok;

    private static LogEnum taskLog;

    public static List<String> cachePaths;

    public static Map<String, Map<String, Integer>> paramMap = new ConcurrentHashMap<>();

    private static Map<String, Future<Integer>> logCallback;

    public final static String START_NUM = "start_num";

    public final static String PAGE_SIZE = "page_size";

    public final static int DEFAULT_PAGE_SIZE = 30000;

    public final static long TIME_OUT_NUM = 600L;

    private IGrokLogCallable iGrokLogCallable;

    static {
//        try {
//            //在本地环境随意用newCachedThreadPool，在线上环境还是保守点用固定大小线程池，建议配合 DEFAULT_PAGE_SIZE
//            String debug = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.debug").toString();
//            if(Boolean.parseBoolean(debug)){
//                executorService = Executors.newCachedThreadPool();
//            }else{
//                executorService = Executors.newFixedThreadPool(3);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        executorService = Executors.newFixedThreadPool(3);
    }


    public static void build(LogEnum logEnum){
        GrokLogWithExecutorUtil.taskLog = logEnum;
        taskGrokCompiler = GrokCompiler.newInstance();
        taskGrokCompiler.registerPatternFromClasspath(taskLog.getPatternsPath());
        taskGrok = taskGrokCompiler.compile(taskLog.getLogRegex());
        paramMap = new ConcurrentHashMap<>();
        logCallback = new ConcurrentHashMap<>();
    }

    public static void flush(){
        GrokLogWithExecutorUtil.taskGrok = null;
        GrokLogWithExecutorUtil.taskLog = null;
        GrokLogWithExecutorUtil.paramMap = null;
        GrokLogWithExecutorUtil.cachePaths = null;
        GrokLogWithExecutorUtil.logCallback = null;
    }

    public GrokLogWithExecutorUtil() {
    }

    private static List<String> validPath(){
        if(GrokLogWithExecutorUtil.cachePaths == null || GrokLogWithExecutorUtil.cachePaths.isEmpty()){
            try {
                Path folder = Paths.get(taskLog.getFilePath());
                GrokLogWithExecutorUtil.cachePaths = Files.walk(folder).filter(Files::isRegularFile).filter(file ->
                        Pattern.matches(taskLog.getValidFileNameRegex(), file.getFileName().toString())
                ).map(file -> file.getFileName().toString()).toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return GrokLogWithExecutorUtil.cachePaths;
    }

    private static List<String> validPathList(List<String> fileNameList){
        return GrokLogWithExecutorUtil.validPath().stream()
                .filter(file -> {
                    for (String fileName : fileNameList) {
                        if(fileName.equals(file)){
                            return true;
                        }
                    }
                    return false;
                }).toList();
    }

    public static GrokLogWithExecutorUtil ready(String fileName){
        List<String> validFileNameList = GrokLogWithExecutorUtil.validPath().stream().filter(file -> file.equals(fileName)).toList();
        AssertUtil.isTrue(validFileNameList.size() == 0, "文件名不存在或校验不通过, fileName:" + fileName);
        Future<Integer> submit = GrokLogWithExecutorUtil.executorService.submit(
                new LogSizeCall(fileName));
        GrokLogWithExecutorUtil.logCallback.put(fileName, submit);
        GrokLogWithExecutorUtil grokLogWithExecutorUtil = new GrokLogWithExecutorUtil();
        return grokLogWithExecutorUtil;
    }

    public static GrokLogWithExecutorUtil readyBetweent(String startFileName, String endFileName){
        String startLogDate = startFileName.substring(taskLog.getPrefix().length());
        String startDateLog = startLogDate.substring(0, startLogDate.length() - taskLog.getSuffix().length());
        String endLogDate = endFileName.substring(taskLog.getPrefix().length());
        String endDateLog = endLogDate.substring(0, endLogDate.length() - taskLog.getSuffix().length());

        List<String> fileNames = GrokLogWithExecutorUtil.validPath().stream().filter(file -> {
            int prefixCount = taskLog.getPrefix().length();
            String suffixDateStr = file.substring(prefixCount);
            String dateStr = suffixDateStr.substring(0, suffixDateStr.length() - taskLog.getSuffix().length());
            LocalDate parse = LocalDate.parse(dateStr);
            return parse.compareTo(LocalDate.parse(startDateLog)) >= 0 && parse.compareTo(LocalDate.parse(endDateLog)) <= 0;
        }).toList();
        AssertUtil.isTrue(fileNames == null || fileNames.isEmpty(), "文件名不存在或校验不通过, startFileName:" + startFileName + " endFileName:"+endFileName);
        return GrokLogWithExecutorUtil.realReady(fileNames);
    }

    public static GrokLogWithExecutorUtil readyList(List<String> fileNameList){
        List<String> fileNames = validPathList(fileNameList);
        AssertUtil.isTrue(fileNames == null || fileNames.isEmpty(), "文件名不存在或校验不通过, fileNameList:" + fileNameList.toString());
        return GrokLogWithExecutorUtil.realReady(fileNames);
    }

    public static GrokLogWithExecutorUtil readys(String... fileNameArray){
        List<String> fileNames = validPathList(Arrays.asList(fileNameArray));
        AssertUtil.isTrue(fileNames == null || fileNames.isEmpty(), "文件名不存在或校验不通过, fileNameArray:" + fileNames.toString());
        return GrokLogWithExecutorUtil.realReady(fileNames);
    }

    public static GrokLogWithExecutorUtil readyArray(String[] fileNameStrs){
        List<String> fileNames = validPathList(Arrays.asList(fileNameStrs));
        AssertUtil.isTrue(fileNames == null || fileNames.isEmpty(), "文件名不存在或校验不通过, fileNameStrs:" + fileNames.toString());
        return GrokLogWithExecutorUtil.realReady(fileNames);
    }

    //多线程查询 每个文件的大小
    private static GrokLogWithExecutorUtil realReady(List<String> fileNames){
        List<Map<String, Future<Integer>>> list = new ArrayList<>();
        for (String fileName : fileNames) {
            Future<Integer> submit = GrokLogWithExecutorUtil.executorService.submit(
                    new LogSizeCall(fileName));
            GrokLogWithExecutorUtil.logCallback.put(fileName, submit);
        }
        return new GrokLogWithExecutorUtil();
    }

    public int pageCount(int logSize, int pageSize){
        return (int) Math.ceil((double) logSize / pageSize);
    }

    private int getLogSize(Future<Integer> logSizeFuture){
        int logSize = 0;
        try {
            logSize = logSizeFuture.get(TIME_OUT_NUM, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        return logSize;
    }

    private List<Map<String, Object>> mergeData(List<Future<List<Map<String, Object>>>> futures){
        List<Map<String, Object>> lists = new CopyOnWriteArrayList<>();
        try {
            for (Future<List<Map<String, Object>>> future : futures){
                List<Map<String, Object>> list = future.get(TIME_OUT_NUM, TimeUnit.SECONDS);
                if(iGrokLogCallable != null) {
                    iGrokLogCallable.pageCallAfter(list);
                }
                lists.addAll(list);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            GrokLogWithExecutorUtil.flush();
        }
        return lists;
    }

    public List<Map<String, Object>> getLogData(IGrokLogCallable iGrokLogCallable) {
        this.iGrokLogCallable = iGrokLogCallable;
        return this.getLogData(0, GrokLogWithExecutorUtil.DEFAULT_PAGE_SIZE);
    }

    public List<Map<String, Object>> getLogData(int currentRecordNum, IGrokLogCallable iGrokLogCallable) {
        this.iGrokLogCallable = iGrokLogCallable;
        return this.getLogData(currentRecordNum, GrokLogWithExecutorUtil.DEFAULT_PAGE_SIZE);
    }

    public List<Map<String, Object>> getLogData() {
        return this.getLogData(0, GrokLogWithExecutorUtil.DEFAULT_PAGE_SIZE);
    }

    public List<Map<String, Object>> getLogData(int currentRecordNum) {
        return this.getLogData(currentRecordNum, GrokLogWithExecutorUtil.DEFAULT_PAGE_SIZE);
    }

    public List<Map<String, Object>> getLogData(int currentRecordNum, int pageSize, IGrokLogCallable iGrokLogCallable) {
        this.iGrokLogCallable = iGrokLogCallable;
        return this.getLogData(currentRecordNum, pageSize);
    }

    public List<Map<String, Object>> getLogData(int currentRecordNum, int pageSize) {
        AssertUtil.isTrue(currentRecordNum < 0,"日志当前记录数不能小于等于0,任务终止. currentRecordNum:" + currentRecordNum);
        AssertUtil.isTrue(pageSize <= 0,"日志页数大小不能小于等于0,任务终止. pageSize:" + pageSize);
        //线程操作
        List<Future<List<Map<String, Object>>>> dataFutures = new CopyOnWriteArrayList<>();
        GrokLogUtil.build(LogEnum.BUSSINESS_TOMCAT_ACCESS);
        for (String fileName : GrokLogWithExecutorUtil.logCallback.keySet()) {
            int currentStartNum = currentRecordNum;
            int currentPageSize = pageSize;
            Map<String, Integer> paramMap = GrokLogWithExecutorUtil.paramMap.get(fileName);
            Future<Integer> logSizeFuture = GrokLogWithExecutorUtil.logCallback.get(fileName);
            //参数优先级 高于传递的参数
            if(paramMap != null){
                if(paramMap.get(START_NUM) != null && paramMap.get(START_NUM) >= 0){
                    currentStartNum = paramMap.get(START_NUM);
                }
                if(paramMap.get(PAGE_SIZE) != null && paramMap.get(PAGE_SIZE) > 0){
                    currentPageSize = paramMap.get(PAGE_SIZE);
                }
            }
            int logSize = getLogSize(logSizeFuture);
            AssertUtil.isTrue(logSize < currentStartNum,"日志总条数小于开始条数,任务终止. logSize:" + logSize + ",currentRecordNum:" + currentStartNum);
            System.out.printf("bookFileName:%s param.get(START_NUM):%d param.get(PAGE_SIZE):%d \r\n",fileName,currentStartNum, currentPageSize);
            int threadCount = pageCount((logSize - currentStartNum), currentPageSize);
            for (int i = 0; i <threadCount; i++) {
                Future<List<Map<String, Object>>> submit = GrokLogWithExecutorUtil.executorService.submit(
                        new GrokLogWithExecutorUtil.LogContentCall(fileName,currentStartNum + (i * currentPageSize), currentPageSize));
                dataFutures.add(submit);
            }
        }
        return mergeData(dataFutures);
    }

    public int getLogSize(String fileName) {
        String clientLog = taskLog.getFilePath() + fileName;
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
            e.printStackTrace();
        }
        return num;
    }

    public static class LogContentCall implements Callable {

        GrokLogUtil grokLogUtil;

        public LogContentCall(String fileName, int currentRecordNum, int pageSize) {
            this.grokLogUtil = new GrokLogUtil(fileName, currentRecordNum, pageSize);
        }

        @Override
        public List<Map<String, Object>> call() throws Exception {
            return grokLogUtil.getPageLogData();
        }
    }

    public static class LogSizeCall implements Callable {

        String fileName;

        GrokLogWithExecutorUtil grokLogWithExecutorUtil;


        public LogSizeCall(String fileName) {
            this.fileName = fileName;
            this.grokLogWithExecutorUtil = new GrokLogWithExecutorUtil();
        }

        @Override
        public Integer call() throws Exception {
            return this.grokLogWithExecutorUtil.getLogSize(this.fileName);
        }
    }

}
