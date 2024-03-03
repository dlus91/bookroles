package com.bookroles.tool;

import com.bookroles.Constant;

import java.io.*;
import java.util.*;

/**
 * @Author: dlus91
 * @Date: 2023/8/29 17:07
 */
public class CsvUtil_20230903 {

    private String _fileName;
    private String[] _keys = null;
    private String _encode = "UTF-8";
    private int _bufferSize = 8192;
    private StringBuilder _resultStr;
    private HashMap _singleMap;
    private List<Map> _dataList;

    public static Map<String,List> ALL_DATA = new HashMap<>();

    public StringBuilder getResultStr(){
        return _resultStr;
    }

    public HashMap getSingleMap() {
        return _singleMap;
    }

    public List<Map> getDataList() {
        return _dataList;
    }

    @Override
    public String toString() {
        return "CsvUtil{" +
                "_fileName='" + _fileName + '\'' +
                ", _keys=" + Arrays.toString(_keys) +
                ", _encode='" + _encode + '\'' +
                ", _resultStr=" + _resultStr +
                ", _singleMap=" + _singleMap +
                ", _dataList=" + _dataList +
                '}';
    }

    public CsvUtil_20230903 build(String fileName){
        _fileName = fileName;
        if("article".equals(_fileName)){
            _keys = new String[]{"id","title","content"};
        }else if("article_total".equals(_fileName)){
            _keys = new String[]{"id","count"};
        }else if("dw_article_record".equals(_fileName)){
            _keys = new String[]{"article_id","user_id","timestamp"};
        }
        return this;
    }

    public CsvUtil_20230903 readList() {
        _resultStr = new StringBuilder();
        _singleMap = null;
        _dataList = new ArrayList();
        String location = "/data/" + _fileName + ".csv";
        int i = 0;
        try{
//            String filePath = Constant.getInstanceServletContext().getResource(location).getPath();
            String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            while ((line = reader.readLine()) != null) {

                _resultStr.append(line);
                line = line.replace("\"", "");
                String[] strSplits = line.split(",");
                if(i == 0){
                    _keys = new String[strSplits.length];
                    for(int j = 0; j < strSplits.length; j++){
                        _keys[j] = strSplits[j];
                    }
                }else{
                    Map map = new HashMap();
                    for(int j = 0; j < strSplits.length; j++){
                        map.put(_keys[j], strSplits[j]);
                    }
                    _dataList.add(map);
                }
                i++;
            }
            fReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    //读取并添加索引
    public CsvUtil_20230903 readList2() {
        _resultStr = new StringBuilder();
        _singleMap = null;
        _dataList = new ArrayList();
        String location = "/data/" + _fileName + ".csv";
        int i = 0;
        try{
//            String filePath = Constant.getInstanceServletContext().getResource(location).getPath();
            String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            int totalLength = 0;
            while ((line = reader.readLine()) != null) {
                int length = line.length() + 2;
                totalLength += length;
                _resultStr.append(line);
                line = line.replace("\"", "");
                String[] strSplits = line.split(",");
                if(i == 0){
                    _keys = new String[strSplits.length];
                    for(int j = 0; j < strSplits.length; j++){
                        _keys[j] = strSplits[j];
                    }
                }else{
                    Map map = new HashMap();
                    for(int j = 0; j < strSplits.length; j++){
                        map.put(_keys[j], strSplits[j]);
                    }
                    map.put("rowNum", i+1);
                    map.put("startLength", (totalLength-length));
                    map.put("dataLength", length);
                    map.put("totalLength", totalLength);
                    _dataList.add(map);

                }
                i++;
            }
            fReader.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ALL_DATA.put(_fileName,_dataList);
        return this;
    }

    //运用索引查询并分页 -- 字节流查询
    public CsvUtil_20230903 readList3(String articleId) {
        _resultStr = new StringBuilder();
        _singleMap = null;
        _dataList = new ArrayList();
        String location = "/data/" + _fileName + ".csv";


        List<Map> list = ALL_DATA.get(_fileName);
        List<Map> destList = new ArrayList<>();

        int currentPageNum = 1;
        int pageSize = 10;
        int beginNum = (currentPageNum - 1) * pageSize;
        int endNum = currentPageNum * pageSize;
        int addCount = 0;
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            if (articleId.equals(map.get("article_id"))) {
                if(addCount < endNum && addCount >= beginNum) {
                    destList.add(map);
                }
                addCount++;
            }
        }
        System.out.println("===========运行readList3===========第" + currentPageNum + "页");
        try{
//            String filePath = Constant.getInstanceServletContext().getResource(location).getPath();
            String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            int runCount = 0;
            int rowCount = 0;
            for (int i = 0; i < destList.size(); i++) {
                Map map = destList.get(i);
                int startLength = Integer.parseInt(String.valueOf(map.get("startLength")));
                int dataLength = Integer.parseInt(String.valueOf(map.get("dataLength")));
                int totalLength = Integer.parseInt(String.valueOf(map.get("totalLength")));
                int strNum = 0;
                StringBuilder str = new StringBuilder();
                while ((strNum = fReader.read()) > -1) {
                    runCount++;
                    if(runCount >= startLength && runCount <= totalLength){
                        String a = Character.toString((char)strNum);
                        str.append(a);
                        if(runCount == totalLength) {
                            rowCount++;
                            break;
                        }
                    }

                }
                System.out.println("第" + (i + 1) + "条 运行"+runCount+"次 开始长度：" + startLength + " 字符长度：" + dataLength + " 总长度："+ totalLength +" -> "+ str.toString());
            }
            System.out.println("查询总条数为：" + rowCount);
            fReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    //运用索引查询并分页 -- 字符流查询
    public CsvUtil_20230903 readList4(String articleId, int currentPageNum) {
        _resultStr = new StringBuilder();
        _singleMap = null;
        _dataList = new ArrayList();
        String location = "/data/" + _fileName + ".csv";


        List<Map> list = ALL_DATA.get(_fileName);
        List<Map> destList = new ArrayList<>();

//        int currentPageNum = 192;
        int pageSize = 8;
        int beginNum = (currentPageNum - 1) * pageSize;
        int endNum = currentPageNum * pageSize;
        int addCount = 0;
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            if (articleId.equals(map.get("article_id"))) {
                if(addCount < endNum && addCount >= beginNum) {
                    destList.add(map);
                }else if(addCount == endNum){
                    break;
                }
                addCount++;
            }
        }

        System.out.println("===========运行readList4===========第" + currentPageNum + "页 article id: " + articleId);
        try{
//            String filePath = Constant.getInstanceServletContext().getResource(location).getPath();
            String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
            int runCount = 0;
            int rowCount = 0;
//            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < destList.size(); i++) {
                Map map = destList.get(i);
//                int startLength = Integer.parseInt(String.valueOf(map.get("startLength")));
                int rowNum = Integer.parseInt(String.valueOf(map.get("rowNum")));
//                int dataLength = Integer.parseInt(String.valueOf(map.get("dataLength")));
//                int totalLength = Integer.parseInt(String.valueOf(map.get("totalLength")));
                StringBuilder str = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    runCount++;
                    if(runCount == rowNum){
                        rowCount++;
                        str.append(line);
                        break;
                    }

                }
//                System.out.println("第" + (i + 1) + "条 运行"+runCount+"次 行号：" + rowNum + " -> "+ str.toString());

            }

//            System.out.println("查询总条数为：" + rowCount + " io时间：" + (System.currentTimeMillis() - beginTime + "ms"));
            reader.close();
            fReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230903 readById(int id) {
        _resultStr = new StringBuilder();
        _singleMap = new HashMap();
        _dataList = null;
        String location = "/data/" + _fileName + ".csv";
        String[] keys = null;
        int i = 0;
        try{
            String filePath =  Constant.getInstanceServletContext().getResource(location).getPath();
//            String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), _encode);
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String line2 = line.replace("\"", "");
                String[] strSplits = line2.split(",");
                if(i == 0){
                    keys = new String[strSplits.length];
                    for(int j = 0; j < strSplits.length; j++){
                        keys[j] = strSplits[j];
                    }
                    _resultStr.append(line);
                }else if(id == Integer.parseInt(strSplits[0])){
                    for(int j = 0; j < strSplits.length; j++){
                        _singleMap.put(keys[j], strSplits[j]);
                    }
                    _resultStr.append(line);
                }
                i++;
            }
            reader.close();
            fReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230903 add(Object obj) {
        _resultStr = new StringBuilder();
        _singleMap = null;
        _dataList = null;
        writeFormatOperation(obj);
        String location = "/data/" + _fileName + ".csv";
        try {
            String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
//            String filePath = Constant.getInstanceServletContext().getResource(location).getPath();
            FileWriter fileWriter = new FileWriter(filePath,true);
            fileWriter.append(_resultStr);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230903 writeById(int id, Map<String,?> map) {
        List<Map> list = this.readList().getDataList();
        _resultStr = new StringBuilder();
        _singleMap = new HashMap();
        Map singData = null;
        writeCsvHeaderFormat();
        try {
            for(int i = 0; i < list.size(); i++){
                singData = list.get(i);
                if(id == Integer.parseInt(String.valueOf(singData.get("id")))) {
                    Iterator<? extends Map.Entry<String, ?>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, ?> entry = iterator.next();
                        _singleMap.put(entry.getKey(),entry.getValue());
                    }
                }
                writeFormatOperation(singData);
            }
            String location = "/data/" + _fileName + ".csv";
//           String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
            String filePath =  Constant.getInstanceServletContext().getResource(location).getPath();
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.append(_resultStr);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CsvUtil_20230903 writeForData(List<Map> list) {
        _resultStr = new StringBuilder();
        _singleMap = null;
        _dataList = null;
        writeCsvHeaderFormat();
        try {
            for(int i = 0; i < list.size(); i++){
                writeFormatOperation(list.get(i));
            }
            String location = "/data/" + _fileName + ".csv";
//            String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business\\src\\main\\webapp\\" + location;
            String filePath =  Constant.getInstanceServletContext().getResource(location).getPath();
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.append(_resultStr);
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
                _resultStr.append(",\"" + _keys[i] + "\"");
            }else if(i == 0){
                _resultStr.append("\"" + _keys[i] + "\"");
            }else if(i == _keys.length - 1){
                _resultStr.append(",\"" + _keys[i] + "\"\r\n");
            }
        }
    }

    private void writeCsvDataFormat(Map map){
        for(int i = 0; i < _keys.length; i++){
            if(i > 0 && i < _keys.length - 1){
                _resultStr.append(",\"" + map.get(_keys[i]) + "\"");
            }else if(i == 0){
                _resultStr.append("\"" + map.get(_keys[i]) + "\"");
            }else if(i == _keys.length - 1){
                _resultStr.append(",\"" + map.get(_keys[i]) + "\"\r\n");
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


    public static void main(String[] args) throws InterruptedException {
//        new CsvUtil().build("article").readList()
//                .getDataList().stream().forEach(System.out::println);
//        System.out.println(new CsvUtil().build("article").readList().toString());

//        long beginMs = System.currentTimeMillis();
//        List list = new ArrayList();
//        for (int i = 0; i < 1400; i++) {
//            Map map = new HashMap();
//            map.put("article_id", (int) (Math.random() * 10 + 1));
//            map.put("user_id", "11");
//            map.put("timestamp", System.currentTimeMillis());
//            list.add(map);
//            Map map2 = new HashMap();
//            map2.put("article_id", (int) (Math.random() * 10 + 1));
//            map2.put("user_id", "22");
//            map2.put("timestamp", System.currentTimeMillis());
//            list.add(map2);
//            Map map3 = new HashMap();
//            map3.put("article_id", (int) (Math.random() * 10 + 1));
//            map3.put("user_id", "33");
//            map3.put("timestamp", System.currentTimeMillis());
//            list.add(map3);
//            Map map4 = new HashMap();
//            map4.put("article_id", (int) (Math.random() * 10 + 1));
//            map4.put("user_id", "11");
//            map4.put("timestamp", System.currentTimeMillis());
//            list.add(map4);
//            Map map5 = new HashMap();
//            map5.put("article_id", (int) (Math.random() * 10 + 1));
//            map5.put("user_id", "55");
//            map5.put("timestamp", System.currentTimeMillis());
//            list.add(map5);
//            Map map6 = new HashMap();
//            map6.put("article_id", (int) (Math.random() * 10 + 1));
//            map6.put("user_id", "66");
//            map6.put("timestamp", System.currentTimeMillis());
//            list.add(map6);
//
//        }
//        new CsvUtil().build("dw_article_record").add(list);
//        System.out.println("run time -> " + (System.currentTimeMillis() - beginMs) + "ms");

//        long beginMs = System.currentTimeMillis();
//        new CsvUtil().build("dw_article_record").readList2();
//        System.out.println("run time -> " + (System.currentTimeMillis() - beginMs) + "ms");
//        beginMs = System.currentTimeMillis();
//        new CsvUtil().build("dw_article_record").readList3("3");
//        System.out.println("run time -> " + (System.currentTimeMillis() - beginMs) + "ms");

        long beginMs = System.currentTimeMillis();
        new CsvUtil_20230903().build("dw_article_record").readList2();
        System.out.println("run time -> " + (System.currentTimeMillis() - beginMs) + "ms");

        long tBeginMs = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
//            beginMs = System.currentTimeMillis();
            int articleIdNum = (int) (Math.random() * 10 + 1);
            new CsvUtil_20230903().build("dw_article_record").readList4(String.valueOf (articleIdNum),(int) (Math.random() * 100 + 1));
//            System.out.println("模拟第" + (i+1) + "次 run time -> " + (System.currentTimeMillis() - beginMs) + "ms");
        }
        System.out.println(" total run time -> " + (System.currentTimeMillis() - tBeginMs) + "ms");


    }



}
