package com.bookroles.tool;

import com.bookroles.Constant;

import java.io.*;
import java.util.*;

/**
 * @Author: dlus91
 * @Date: 2023/8/27 17:32
 */
public class CsvUtil_20230829 {

    public static List<Map> readList(String fileName) {
        String location = "/data/" + fileName + ".csv";
        List<Map> dataList = new ArrayList();
        HashMap singleData = null;
        String[] keys = null;
        int i = 0;
        try{
            String filePath = Constant.getInstanceServletContext().getResource(location).getPath();
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("\"", "");
                String[] strSplits = line.split(",");
                if(i == 0){
                    keys = new String[strSplits.length];
                    for(int j = 0; j < strSplits.length; j++){
                        keys[j] = strSplits[j];
                    }
                }else{
                    singleData = new HashMap();
                    for(int j = 0; j < strSplits.length; j++){
                        singleData.put(keys[j], strSplits[j]);
                    }
                    dataList.add(singleData);
                }
                i++;
            }
            reader.close();
            fReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static Map readById(String fileName, int id) {
        String location = "/data/" + fileName + ".csv";
        HashMap singleData = new HashMap();
        String[] keys = null;
        int i = 0;
        try{
            String filePath =  Constant.getInstanceServletContext().getResource(location).getPath();
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("\"", "");
                String[] strSplits = line.split(",");
                if(i == 0){
                    keys = new String[strSplits.length];
                    for(int j = 0; j < strSplits.length; j++){
                        keys[j] = strSplits[j];
                    }
                }else if(id == Integer.parseInt(strSplits[0])){
                    for(int j = 0; j < strSplits.length; j++){
                        singleData.put(keys[j], strSplits[j]);
                    }
                }
                i++;
            }
            reader.close();
            fReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return singleData;
    }

    public static void add(String fileName, Object obj, String[] headers) {
        if(!(obj instanceof List) && !(obj instanceof Map)){
            throw new RuntimeException("数据类型异常obj不是List或Map类型");
        }
        String location = "/data/" + fileName + ".csv";
        StringBuilder str = new StringBuilder();
        try {
            String filePath = "H:\\programmer\\Interviewspace\\testProject\\project1\\src\\main\\webapp\\data\\dw_article_record.csv";
//            String filePath = Constant.getInstanceServletContext().getResource(location).getPath();
//            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
//            BufferedReader reader = new BufferedReader(fReader);
//            String line;
//            while ((line = reader.readLine()) != null) {
//                str.append(line).append("\r\n");
//            }
//            reader.close();
            if(obj instanceof List){
                writeFormatOperation(str, headers, (List)obj);
            }else if(obj instanceof Map){
                writeFormatOperation(str, headers, (Map)obj);
            }
            FileWriter fileWriter = new FileWriter(filePath,true);
            fileWriter.append(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map writeById(String fileName, int id, Map<String,?> map,String[] headers) {
        List<Map> list = readList(fileName);
        Map singData = null;
        StringBuilder str = new StringBuilder();
        writeFormatOperation(str, headers);
        try {
            for(int i = 0; i < list.size(); i++){
                singData = list.get(i);
                if(id == Integer.parseInt(String.valueOf( singData.get("id")))) {
                    Iterator<? extends Map.Entry<String, ?>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, ?> entry = iterator.next();
                        singData.put(entry.getKey(),entry.getValue());
                    }
                }
                writeFormatOperation(str, headers, singData);
            }
            String location = "/data/" + fileName + ".csv";
            FileWriter fileWriter = new FileWriter( Constant.getInstanceServletContext().getResource(location).getPath());
            fileWriter.append(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return singData;
    }

    public static void writeForData(String fileName, List<Map> list,String[] headers) {
        StringBuilder str = new StringBuilder();
        writeFormatOperation(str, headers);
        try {
            for(int i = 0; i < list.size(); i++){
                writeFormatOperation(str, headers, list.get(i));
            }
            String location = "/data/" + fileName + ".csv";
            FileWriter fileWriter = new FileWriter( Constant.getInstanceServletContext().getResource(location).getPath());
            fileWriter.append(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFormatOperation(StringBuilder str, String[] headers){
        for(int i = 0; i < headers.length; i++){
            if(i > 0 && i < headers.length - 1){
                str.append(",\"" + headers[i] + "\"");
            }else if(i == 0){
                str.append("\"" + headers[i] + "\"");
            }else if(i == headers.length - 1){
                str.append(",\"" + headers[i] + "\"\r\n");
            }
        }
    }

    private static void writeFormatOperation(StringBuilder str, String[] headers, Map dataMap){
        for(int i = 0; i < headers.length; i++){
            if(i > 0 && i < headers.length - 1){
                str.append(",\"" + dataMap.get(headers[i]) + "\"");
            }else if(i == 0){
                str.append("\"" + dataMap.get(headers[i]) + "\"");
            }else if(i == headers.length - 1){
                str.append(",\"" + dataMap.get(headers[i]) + "\"\r\n");
            }
        }
    }

    private static void writeFormatOperation(StringBuilder str, String[] headers, List<Map> dataList){
        for(Map dataMap : dataList){
            writeFormatOperation(str, headers, dataMap);
        }
    }



    public static void main(String[] args){
//        String filePath = "H:\\programmer\\Interviewspace\\testProject\\project1\\src\\main\\webapp\\data\\article_total.csv";
//        HashMap countMap = new HashMap();
//        countMap.put("count", "5001");
//        CsvUtils.writeById("article_total", 3, countMap,new String[]{"id","count"});

//        List list = new ArrayList<>();
//        Map map = new HashMap();
//        map.put("id", "1");
//        map.put("count", "11");
//        list.add(map);
//        Map map2 = new HashMap();
//        map2.put("id", "2");
//        map2.put("count", "22");
//        list.add(map2);
//        Map map3 = new HashMap();
//        map3.put("id", "3");
//        map3.put("count", "33");
//        list.add(map3);
//        Map map4 = new HashMap();
//        map4.put("id", "4");
//        map4.put("count", "55");
//        list.add(map4);
//        CsvUtils.writeForData("article_total", list,new String[]{"id","count"});

//        CsvUtils.readList("").stream().forEach(map -> {
//            System.out.println("map->"+map);
//        });

//        Map recordMap = new HashMap();
//        recordMap.put("article_id", 1);
//        recordMap.put("user_id", 0);
//        recordMap.put("timestamp", System.currentTimeMillis());
//        List<Map> list = new ArrayList<>();
//        list.add(recordMap);
//        CsvUtils.add("dw_article_record", list, new String[]{"article_id","user_id","timestamp"});

    }


}
