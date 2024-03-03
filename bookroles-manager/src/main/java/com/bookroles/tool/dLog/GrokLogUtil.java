package com.bookroles.tool.dLog;

import com.bookroles.exception.AssertUtil;
import com.bookroles.exception.BussinessException;
import com.bookroles.tool.LogEnum;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @Author: dlus91
 * @Date: 2023/10/12 18:04
 */
public class GrokLogUtil {

    Logger logger = LoggerFactory.getLogger(GrokLogUtil.class);

    private static GrokCompiler taskGrokCompiler;

    private static Grok taskGrok;

    public static LogEnum taskLog;

    @Getter
    @Setter
    private String fileName;

    @Getter
    @Setter
    private int currentRecordNum;

    @Getter
    @Setter
    private int pageSize;

    private int charNum;

    private List<Map<String, Object>> pageLogData = new ArrayList();

    GrokLogUtil(String fileName) {
        this.fileName = fileName;
    }

    public GrokLogUtil(String fileName, int currentRecordNum, int pageSize) {
        this.fileName = fileName;
        this.currentRecordNum = currentRecordNum;
        this.pageSize = pageSize;
    }

    public static void build(LogEnum logEnum){
        GrokLogUtil.taskLog = logEnum;
        taskGrokCompiler = GrokCompiler.newInstance();
        taskGrokCompiler.registerPatternFromClasspath(taskLog.getPatternsPath());
        GrokLogUtil.taskGrok = taskGrokCompiler.compile(taskLog.getLogRegex());
    }

    public static GrokLogUtil ready(String fileName, int currentRecordNum, int pageSize){
        AssertUtil.isTrue(currentRecordNum < 0,"日志当前记录数不能小于等于0,任务终止. currentRecordNum:" + currentRecordNum);
        AssertUtil.isTrue(pageSize <= 0,"日志页数大小不能小于等于0,任务终止. pageSize:" + pageSize);
        AssertUtil.isTrue(fileName == null || fileName.isEmpty(),"文件名不能为空/'',任务终止. fileName:" + fileName);
        GrokLogUtil grokLogUtil = new GrokLogUtil(fileName, currentRecordNum, pageSize);
        if(grokLogUtil.validFileName(fileName)){
            GrokLogUtil.flush();
            throw new BussinessException("文件名: " + fileName + " 不存在或者格式不正确. 校验格式为： " + taskLog.getValidFileNameRegex());
        }
        return grokLogUtil;
    }

    public static void flush(){
        GrokLogUtil.taskGrok = null;
        GrokLogUtil.taskLog = null;
    }

    private boolean validFileName(String fileName){
        try {
            Path folder = Paths.get(taskLog.getFilePath());
            return Files.walk(folder).filter(Files::isRegularFile)
                    .filter(file -> Pattern.matches(taskLog.getValidFileNameRegex(), file.getFileName().toString()))
                    .map(file -> file.getFileName().toString()).filter(file -> file.equals(fileName)).toList().isEmpty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getPageLogData(){
        String log = this.getLogString(this.currentRecordNum, this.pageSize);
        if(log == null){
            return null;
        }
        String[] logMessages = log.split("\r\n");
        this.pageLogData = new ArrayList<>();
        for (String logMsg : logMessages) {
            Match grokMatch = taskGrok.match(logMsg);
            Map<String, Object> capture = grokMatch.capture();
            this.pageLogData.add(capture);
        }
        return pageLogData;
    }

    public List<Map<String, Object>> getPageLogDataWithMap(Map<String, Object> wordMap){
        this.pageLogData = new ArrayList<>();
        long logSize = this.getLogSize();
        if(wordMap != null && !wordMap.isEmpty()){
            int page = 0;
            int count = 0;
            do{
                int startNum = this.currentRecordNum + (page * this.pageSize);
                String log = this.getLogString(startNum, this.pageSize);
                if(log == null) return null;
                String[] logMessages = log.split("\r\n");
                for (String logMsg : logMessages) {
                    if(this.pageLogData.size() == this.pageSize || count == logSize) break;
                    Match grokMatch = taskGrok.match(logMsg);
                    Map<String, Object> capture = grokMatch.capture();
                    for(String paramKey : wordMap.keySet()){
                        if(capture.get(paramKey) == null) continue;
                        if(capture.get(paramKey).toString().contains((wordMap.get(paramKey).toString()))){
                            this.pageLogData.add(capture);
                            break;
                        }
                    }
                    count++;
                }
                page++;
            }while (this.pageLogData.size() < this.pageSize && count < logSize);

        }
        return pageLogData;
    }

    public List<Map<String, Object>> getPageLogDataWithRange(String key, int start, int end){
        this.pageLogData = new ArrayList<>();
        long logSize = this.getLogSize();
        if(key != null && !key.isEmpty()){
            int page = 0;
            int count = 0;
            do{
                int startNum = this.currentRecordNum + (page * this.pageSize);
                String log = this.getLogString(startNum, this.pageSize);
                if(log == null) return null;
                String[] logMessages = log.split("\r\n");
                for (String logMsg : logMessages) {
                    if(this.pageLogData.size() == this.pageSize || count == logSize) break;
                    Match grokMatch = taskGrok.match(logMsg);
                    Map<String, Object> capture = grokMatch.capture();
                    if(capture.get(key) != null && StringUtils.isNumeric(capture.get(key).toString())){
                        int value = Integer.parseInt(capture.get(key).toString());
                        if(value >= start && value <= end){
                            this.pageLogData.add(capture);
                        }
                    }else {
                        AssertUtil.isTrue(1==1, "key的值为空或者不是数字类型 value:" + capture.get(key));
                        break;
                    }
                    count++;
                }
                page++;
            }while (this.pageLogData.size() < this.pageSize && count < logSize);

        }
        return pageLogData;
    }

    public List<Map<String, Object>> getPageLogDataWithGreaterThan(String key, int num){
        this.pageLogData = new ArrayList<>();
        long logSize = this.getLogSize();
        if(key != null && !key.isEmpty()){
            int page = 0;
            int count = 0;
            do{
                int startNum = this.currentRecordNum + (page * this.pageSize);
                String log = this.getLogString(startNum, this.pageSize);
                if(log == null) return null;
                String[] logMessages = log.split("\r\n");
                for (String logMsg : logMessages) {
                    if(this.pageLogData.size() == this.pageSize || count == logSize) break;
                    Match grokMatch = taskGrok.match(logMsg);
                    Map<String, Object> capture = grokMatch.capture();
                    if(capture.get(key) != null && StringUtils.isNumeric(capture.get(key).toString())){
                        int value = Integer.parseInt(capture.get(key).toString());
                        if(value > num){
                            this.pageLogData.add(capture);
                        }
                    }else {
                        AssertUtil.isTrue(1==1, "key的值为空或者不是数字类型 value:" + capture.get(key));
                        break;
                    }
                    count++;
                }
                page++;
            }while (this.pageLogData.size() < this.pageSize && count < logSize);

        }
        return pageLogData;
    }

    private String getLogString(int currentRecordNum, int pageSize){
        String clientLog = taskLog.getFilePath() + fileName;
        int beginNum = currentRecordNum;
        int endNum = currentRecordNum + pageSize;
        System.out.printf("fileName: %s,beginNum：%d,endNum：%d \r\n",fileName,beginNum,endNum);
        StringBuilder logContent = new StringBuilder();
        try{
            BufferedReader bufferedReader = Files.newBufferedReader(Path.of(clientLog), StandardCharsets.UTF_8);
            String line;
            int num = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (num >= beginNum && num < endNum){
                    logContent.append(num).append(",").append(line).append("\r\n");
                }else if (num >= endNum){
                    break;
                }
                num++;
            }
            logger.info("该此io操作，遍历数据量为：" + num);
            bufferedReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
//            e.printStackTrace();
        }
        return logContent.toString();
    }

    public long getLogSize() {
        String clientLog = taskLog.getFilePath() + fileName;
        long num = 0;
        try {
            num = Files.lines(Path.of(clientLog)).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return num;
    }


}
