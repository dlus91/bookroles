package com.bookroles.tool;

import com.bookroles.Constant;
import com.bookroles.HtmlFetcher;
import com.bookroles.exception.AssertUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.*;

public class CsvUtil_20230918 {

    private String _fileName;
    private final String _encode = "UTF-8";
    private String[] _keys = null;
    private StringBuilder _dataStr;
    private Map _dataSingle;
    private List<Map> _dataList;
    public static String DEFAULT_DATAURL;

    static {
        try {
            DEFAULT_DATAURL = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.dataFileURL").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataStr(){
        return _dataStr.toString();
    }

    public Map getDataSingle() { return _dataSingle; }

    public List<Map> getDataList() {
        return _dataList;
    }

    public CsvUtil_20230918 build(String csvFileName, String headerString) {
        AssertUtil.isStringEmpty(headerString, "文件名为空");
        _fileName = csvFileName;
        AssertUtil.isStringEmpty(headerString, "空字符串");
        _keys = headerString.split(",");
        return this;
    }

    public CsvUtil_20230918 readMapById(int id){
        _dataStr = new StringBuilder();
        _dataSingle = new HashMap();
        _dataList = null;
        String filePath = DEFAULT_DATAURL + _fileName + ".csv";
        try{
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
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
            fReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230918 readList() {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = new ArrayList();
        try{
            String filePath = DEFAULT_DATAURL + _fileName + ".csv";
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                if(rowNum > 0){
                    line = line.replace("\"", "");
                    _dataStr.append(line);
                    String[] strSplits = line.split(",");
                    Map map = new HashMap();
                    for (int j = 0; j < _keys.length; j++) {
                        map.put(_keys[j], strSplits[j]);
                    }
                    _dataList.add(map);
                }
                rowNum++;
            }
            fReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230918 readList(int pageNum) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = new ArrayList();
        int pageSize =  Constant.pageSize;
        int beginNum = (pageNum - 1) * pageSize + 1;
        int endNum = pageNum * pageSize + 1;
        try{
            String filePath = DEFAULT_DATAURL + _fileName + ".csv";
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                if(rowNum > 0 && rowNum < endNum && rowNum >= beginNum) {
                    line = line.replace("\"", "");
                    _dataStr.append(line);
                    String[] strSplits = line.split(",");
                    Map map = new HashMap();
                    for (int j = 0; j < _keys.length; j++) {
                        map.put(_keys[j], strSplits[j]);
                    }
                    _dataList.add(map);
                }else if(rowNum == endNum){
                    break;
                }
                rowNum++;
            }
            fReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230918 readDataGroupbyForLastTimestamp(String timestampFieldName , String fieldName){
        _dataStr = null;
        _dataSingle = new HashMap();
        _dataList = null;
        try{
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
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
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
            fReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230918 readDataWithIndex(List<Map> list) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = new ArrayList();
        try{
            String filePath = DEFAULT_DATAURL + _fileName + ".csv";
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
            int runCount = -1;
            int rowCount = 0;
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                int rowNum = Integer.parseInt(String.valueOf(map.get("rowNum")));
                StringBuilder str = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    runCount++;
                    if(runCount == rowNum){
                        rowCount++;
                        line = line.replace("\"", "");
                        String[] strSplits = line.split(",");
                        Map dataMap = new HashMap();
                        for(int j = 0; j < _keys.length; j++){
                            dataMap.put(_keys[j], strSplits[j]);
                        }
                        _dataList.add(dataMap);
                        break;
                    }
                }
            }
            reader.close();
            fReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230918 includeHeaderArray(String[] headers) {
        AssertUtil.isTrue(headers == null || headers.length == 0, "请正确传入参数...");
        AssertUtil.isFalse(this.getDataSingle() == null || this.getDataList() == null, "返回值为空请检查...");
        List nlist = new ArrayList();
        if(this.getDataList() != null && this.getDataSingle() == null){
            for(Map dataMap : this.getDataList()){
                Map nMap = new HashMap();
                for(String header : headers){
                    nMap.put(header, dataMap.get(header));
                }
                nlist.add(nMap);
            }
            if(nlist.size() > 0) _dataList = nlist;
        }else{
            Map nMap = new HashMap();
            for(String header : headers) {
                nMap.put(header, _dataSingle.get(header));
            }
            if(!nMap.isEmpty()) _dataSingle = nMap;
        }
        return this;
    }

    public CsvUtil_20230918 includeHeaders(String... headers) {
        includeHeaderArray(headers);
        return this;
    }

    public CsvUtil_20230918 excludeHeaderArray(String[] headers){
        AssertUtil.isTrue(headers == null || headers.length == 0, "请正确传入参数...");
        AssertUtil.isFalse(this.getDataSingle() == null || this.getDataList() == null, "返回值为空请检查...");
        List nlist = new ArrayList();
        if(this.getDataList() != null && this.getDataSingle() == null){
            for(Map dataMap : this.getDataList()){
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

    public CsvUtil_20230918 excludeHeaders(String... headers){
        excludeHeaderArray(headers);
        return this;
    }

    //undo 可以考虑一段时间追加一次 不用每次追加
    //todo 加个缓存 每1000条追加一次 或者每10个小时追加一次
    public CsvUtil_20230918 add(Object obj) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = null;
        writeFormatOperation(obj);
        try {
            String filePath = DEFAULT_DATAURL + _fileName + ".csv";
            FileWriter fileWriter = new FileWriter(filePath,true);
            fileWriter.append(_dataStr);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230918 addList(List list) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = null;
        writeFormatOperation(list);
        try {
            String filePath = DEFAULT_DATAURL + _fileName + ".csv";
            FileWriter fileWriter = new FileWriter(filePath,true);
            fileWriter.append(_dataStr);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230918 writeForData(List<Map> list) {
        _dataStr = new StringBuilder();
        _dataSingle = null;
        _dataList = null;
        try {
            //写一次头
            writeCsvHeaderFormat();
            writeFormatOperation(list);
            String filePath = DEFAULT_DATAURL + _fileName + ".csv";
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.append(_dataStr);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


    private void writeCsvHeaderFormat(){
        for(int i = 0; i < _keys.length; i++){
            if(i > 0 && i < _keys.length - 1){
                _dataStr.append(",\"" + _keys[i] + "\"");
            }else if(i == 0){
                _dataStr.append("\"" + _keys[i] + "\"");
            }else if(i == _keys.length - 1){
                _dataStr.append(",\"" + _keys[i] + "\"\r\n");
            }
        }
    }

    private void writeCsvDataFormat(Map map){
        for(int i = 0; i < _keys.length; i++){
            if(i > 0 && i < _keys.length - 1){
                _dataStr.append(",\"" + map.get(_keys[i]) + "\"");
            }else if(i == 0){
                _dataStr.append("\"" + map.get(_keys[i]) + "\"");
            }else if(i == _keys.length - 1){
                _dataStr.append(",\"" + map.get(_keys[i]) + "\"\r\n");
            }
        }
    }

    private void writeFormatOperation(Object object){
        if(object instanceof List){
            for(Map map : (List<Map>)object){
                writeCsvDataFormat(map);
            }
        }else if(object instanceof Map){
            writeCsvDataFormat((Map)object);
        }else{
            throw new RuntimeException("数据类型异常obj不是List或Map类型");
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
        System.out.println(CsvUtil_20230918.DEFAULT_DATAURL);
    }

    public static void main2(String[] args) {
        long beginTime = System.currentTimeMillis();

        //事实表
        CsvUtil_20230918 bookClickRecordCsv = new CsvUtil_20230918().build(CsvPropertiesEnum.BOOK_CLICK_RECORD.getName(), CsvPropertiesEnum.BOOK_CLICK_RECORD.getHeaderString()).readDataGroupbyForLastTimestamp("current_timestamp", "book_id");
        //快照表
        CsvUtil_20230918 bookClickTotalCsv = new CsvUtil_20230918().build(CsvPropertiesEnum.BOOK_CLICK_TOTAL.getName(), CsvPropertiesEnum.BOOK_CLICK_TOTAL.getHeaderString()).readList();
        List<Map> dataList = bookClickTotalCsv.getDataList();
        for(Map map : dataList){
            int id = Integer.parseInt(String.valueOf(map.get("id")));
            int count = Integer.parseInt(String.valueOf(map.get("count")));
            Map singMap = new HashMap<>();
            singMap.put("count",count);
             Constant.BOOK_CLICK_LAST_RECORD.put(id, singMap);
        }

        Iterator<Map.Entry<String, Integer>> iterator = bookClickRecordCsv.getDataSingle().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ?> entry = iterator.next();
            Map map =  Constant.BOOK_CLICK_LAST_RECORD.get(Integer.parseInt(entry.getKey()));
            if(map == null || map.isEmpty()){
                Map nMap = new HashMap();
                nMap.put("count", 0);
                nMap.put("timestamp", entry.getValue());
                 Constant.BOOK_CLICK_LAST_RECORD.put(Integer.parseInt(entry.getKey()), nMap);
            }else{
                map.put("timestamp", entry.getValue());
            }
        }

        System.out.println( Constant.BOOK_CLICK_LAST_RECORD);

        List list = new ArrayList();
//        Iterator<Map.Entry<Integer, Map<String, ?>>> bookClickCountMapIterator =  Constant.BOOK_CLICK_LAST_RECORD.entrySet().iterator();
//        while (bookClickCountMapIterator.hasNext()) {
//            Map.Entry<Integer, Map<String, ?>> entry = bookClickCountMapIterator.next();
//            Map nMap = new HashMap();
//            Map valueMap = entry.getValue();
//            nMap.put("id", entry.getKey());
//            nMap.put("count", valueMap.get("count"));
//            list.add(nMap);
//        }
//        new CsvUtil_20230918().build(CsvPropertiesEnum.BOOK_CLICK_TOTAL.getName(), CsvPropertiesEnum.BOOK_CLICK_TOTAL.getHeaderString()).writeForData(list);

        System.out.println("耗时ms->" + (System.currentTimeMillis() - beginTime) + "ms");

    }

}
