package com.bookroles.tool;

import com.bookroles.Constant;
import com.bookroles.HtmlFetcher;
import com.bookroles.exception.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class CsvUtil {
    final static Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    private String _fileName;
    private final String _encode = "UTF-8";
    private String[] _keys = null;
    private StringBuilder _dataStr;
    private Map<String,String> _dataSingle;
    private List<Map<String,String>> _dataList;
    public static String DEFAULT_DATAURL;

    static {
        try {
            DEFAULT_DATAURL = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.dataFileURL").toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public Map<String,String> getDataSingle() { return _dataSingle; }

    public List<Map<String,String>> getDataList() {
        return _dataList;
    }

    public CsvUtil build(String csvFileName, String headerString) {
        AssertUtil.isStringEmpty(headerString, "文件名为空");
        _fileName = csvFileName;
        AssertUtil.isStringEmpty(headerString, "空字符串");
        _keys = headerString.split(",");
        return this;
    }

    public CsvUtil readMapById(int id){
        _dataStr = new StringBuilder();
        _dataSingle = new HashMap<>();
        _dataList = null;
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
                BufferedReader reader = new BufferedReader(fReader)
            ){
            String line;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                if(rowNum > 0){
                    line = line.replace("\"", "");
                    _dataStr.append(line);
                    String[] strSplits = line.split(",");
                    for (int j = 0; j < _keys.length; j++) {
                        if(id == Integer.parseInt(strSplits[0])){
                            _dataSingle.put(_keys[j], strSplits[j]);
                        }
                    }
                    if(_dataSingle.size() > 1){
                        break;
                    }
                }
                rowNum++;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public CsvUtil readList() {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = new ArrayList<>();
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
                BufferedReader reader = new BufferedReader(fReader)
            ){
            String line;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                if(rowNum > 0){
                    line = line.replace("\"", "");
                    _dataStr.append(line);
                    String[] strSplits = line.split(",");
                    Map<String,String> map = new HashMap<>();
                    for (int j = 0; j < _keys.length; j++) {
                        map.put(_keys[j], strSplits[j]);
                    }
                    _dataList.add(map);
                }
                rowNum++;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public CsvUtil readList(int pageNum) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = new ArrayList<>();
        int pageSize = Constant.pageSize;
        int beginNum = (pageNum - 1) * pageSize + 1;
        int endNum = pageNum * pageSize + 1;
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
                BufferedReader reader = new BufferedReader(fReader)
            ){
            String line;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                if(rowNum > 0 && rowNum < endNum && rowNum >= beginNum) {
                    line = line.replace("\"", "");
                    _dataStr.append(line);
                    String[] strSplits = line.split(",");
                    Map<String,String> map = new HashMap<>();
                    for (int j = 0; j < _keys.length; j++) {
                        map.put(_keys[j], strSplits[j]);
                    }
                    _dataList.add(map);
                }else if(rowNum == endNum){
                    break;
                }
                rowNum++;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public CsvUtil readDataGroupbyForLastTimestamp(String timestampFieldName ,String fieldName){
        _dataStr = null;
        _dataSingle = new HashMap<>();
        _dataList = null;
        int timestampFieldNameNum = 0;
        int fieldNameNum = 0;
        for (int i = 0; i < _keys.length; i++) {
            if(timestampFieldName.equals(_keys[i])){
                timestampFieldNameNum = i;
            }
            if(fieldName.equals(_keys[i])){
                fieldNameNum = i;
            }
        }
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
                BufferedReader reader = new BufferedReader(fReader)
            ){
            String line;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                if(rowNum > 0){
                    line = line.replace("\"", "");
                    String[] strSplits = line.split(",");
                    if(!"0".equals(strSplits[fieldNameNum])) {
                        _dataSingle.put(strSplits[fieldNameNum], strSplits[timestampFieldNameNum]);
                    }
                }
                rowNum++;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public CsvUtil readDataWithIndex(List<Map<String,String>> list) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = new ArrayList<>();
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
                BufferedReader reader = new BufferedReader(fReader)
            ){
            int runCount = -1;
            for(Map<String,String> map : list){
                int rowNum = Integer.parseInt(map.get("rowNum"));
                String line;
                while ((line = reader.readLine()) != null) {
                    runCount++;
                    if(runCount == rowNum){
                        line = line.replace("\"", "");
                        String[] strSplits = line.split(",");
                        Map<String,String> dataMap = new HashMap<>();
                        for(int j = 0; j < _keys.length; j++){
                            dataMap.put(_keys[j], strSplits[j]);
                        }
                        _dataList.add(dataMap);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public CsvUtil includeHeaderArray(String[] headers) {
        AssertUtil.isTrue(headers == null || headers.length == 0, "请正确传入参数...");
        AssertUtil.isFalse(this.getDataSingle().isEmpty() || this.getDataList().isEmpty(), "返回值为空请检查...");
        List<Map<String,String>> nlist = new ArrayList<>();
        if(_dataList != null && _dataSingle == null){
            for(Map<String,String> dataMap : _dataList){
                Map<String,String> nMap = new HashMap<>();
                for(String header : headers){
                    nMap.put(header, dataMap.get(header));
                }
                nlist.add(nMap);
            }
            if(nlist.isEmpty()) _dataList = nlist;
        }else{
            Map<String,String> nMap = new HashMap<>();
            for(String header : headers) {
                nMap.put(header, _dataSingle.get(header));
            }
            if(!nMap.isEmpty()) _dataSingle = nMap;
        }
        return this;
    }

    public CsvUtil includeHeaders(String... headers) {
        includeHeaderArray(headers);
        return this;
    }

    public CsvUtil excludeHeaderArray(String[] headers){
        AssertUtil.isTrue(headers == null || headers.length == 0, "请正确传入参数...");
        AssertUtil.isFalse(this.getDataSingle() == null || this.getDataList() == null, "返回值为空请检查...");
        if(_dataList != null && _dataSingle == null){
            for(Map<String,String> dataMap : _dataList){
                for(String header : headers){
                    dataMap.remove(header);
                }
            }
        }else{
            for(String header : headers) {
                _dataSingle.remove(header);
            }
        }
        return this;
    }

    public CsvUtil excludeHeaders(String... headers){
        excludeHeaderArray(headers);
        return this;
    }

    //undo 可以考虑一段时间追加一次 不用每次追加
    //todo 加个缓存 每1000条追加一次 或者每10个小时追加一次
    public CsvUtil add(Object obj) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = null;
        writeFormatOperation(obj);
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                FileWriter fileWriter = new FileWriter(filePath,true)
            ) {
            fileWriter.append(_dataStr);
            fileWriter.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public CsvUtil addList(List<Map<String,String>> list) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = null;
        writeFormatOperation(list);
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                FileWriter fileWriter = new FileWriter(filePath,true)
            ) {
            fileWriter.append(_dataStr);
            fileWriter.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public CsvUtil writeForData(List<Map<String,String>> list) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = null;
        //写一次头
        writeCsvHeaderFormat();
        writeFormatOperation(list);
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try(
                FileWriter fileWriter = new FileWriter(filePath)
            ) {
            fileWriter.append(_dataStr);
            fileWriter.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this;
    }


    private void writeCsvHeaderFormat(){
        for(int i = 0; i < _keys.length; i++){
            if(i > 0 && i < _keys.length - 1){
                _dataStr.append(",\"").append(_keys[i]).append("\"");
            }else if(i == 0){
                _dataStr.append("\"").append(_keys[i]).append("\"");
            }else if(i == _keys.length - 1){
                _dataStr.append(",\"").append(_keys[i]).append("\"\r\n");
            }
        }
    }

    private void writeCsvDataFormat(Map<String,String> map){
        for(int i = 0; i < _keys.length; i++){
            if(i > 0 && i < _keys.length - 1){
                _dataStr.append(",\"").append(map.get(_keys[i])).append("\"");
            }else if(i == 0){
                _dataStr.append("\"").append(map.get(_keys[i])).append("\"");
            }else if(i == _keys.length - 1){
                _dataStr.append(",\"").append(map.get(_keys[i])).append("\"\r\n");
            }
        }
    }

    private void writeFormatOperation(Object object){
        if(object instanceof List){
            for(Map<String,String> map : (List<Map<String,String>>)object){
                writeCsvDataFormat(map);
            }
        }else if(object instanceof Map){
            writeCsvDataFormat((Map<String,String>)object);
        }else{
            String exceptionStr = "数据类型异常obj不是List或Map类型";
            logger.error(exceptionStr);
            throw new RuntimeException(exceptionStr);
        }
    }

    public static void main(String[] args) {
//        CsvUtil bookCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK.getName(), CsvPropertiesEnum.BOOK.getHeaderString())
//                .readList().excludeHeaderArray(new String[]{"id","author","content_intro","download_url"});
//        System.out.println(bookCsv.getDataList());
//        CsvUtil bookClickRecordCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK_CLICK_RECORD.getName(), CsvPropertiesEnum.BOOK_CLICK_RECORD.getHeaderString())
//                .readDataGroupbyForLastTimestamp("current_timestamp", "book_id").excludeHeaders("12","13","14");
//        System.out.println(bookClickRecordCsv.getDataSingle());
        System.out.println(HtmlFetcher.htmlUrl);
        System.out.println(CsvUtil.DEFAULT_DATAURL);
    }


}
