package com.bookroles.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/9/23 13:13
 */
public class KeyGenerateUtil {

    final static Logger logger = LoggerFactory.getLogger(KeyGenerateUtil.class);

    private static String sKey="a90e7d2eb24c4b8b88994ab065e377d3";
    private static String ivParameter = "bookrolesABcDEFh";

    private static final int keyTotalCount = 6 * 60 * 24;

    public static String dataUrl;

    static {
        try {
            dataUrl = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.dataFileURL").toString() + "keystore.txt";
            System.out.println("KeyGenerateUtil.dataUrl：" + dataUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    //在没有生成数据时，默认这对键值对
    public static Map<String,String> getDefaultMap(){
        Map<String,String> map = new HashMap<>();
        map.put("key", sKey);
        map.put("iv", ivParameter);
        return map;
    }

    public static void generateKeystoreData() {
        String keyCharts = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String ivCharts = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        long beginTime = System.currentTimeMillis();
        Map<Integer, Map<String,String>> keystore = new HashMap<>();
        //暂定10秒一次的逻辑
        System.out.println("开始生成：" + keyTotalCount + "条密钥键值对");
        for (int keyNum = 0; keyNum < keyTotalCount;keyNum++) {
            StringBuilder key = new StringBuilder();
            StringBuilder iv = new StringBuilder();
            try {
                SecureRandom keyStrong = SecureRandom.getInstanceStrong();
                for (int i = 0; i < 32; i++) {
                    key.append(keyCharts.charAt(keyStrong.nextInt(keyCharts.length())));
                }
                SecureRandom ivStrong = SecureRandom.getInstanceStrong();
                for (int i = 0; i < 7; i++) {
                    iv.append(ivCharts.charAt(ivStrong.nextInt(ivCharts.length())));
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            Map<String, String> keyPair = new HashMap<>();
            keyPair.put("key", key.toString());
            keyPair.put("iv", "bookroles"+iv);
            keystore.put(keyNum,keyPair);
        }
        System.out.println(keyTotalCount+"次生成密钥键值对耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
        StringBuilder strAll = new StringBuilder();
        for (Map.Entry<Integer, Map<String, String>> keyPair : keystore.entrySet()){
            Map<String, String> value = keyPair.getValue();
            strAll.append(keyPair.getKey()+1).append(":").append(value.get("key")).append(",").append(value.get("iv")).append("\r\n");
        }
        try (FileWriter fileWriter = new FileWriter(dataUrl)){
            fileWriter.append(strAll);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("输出到文件总耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
    }

    public  static Map<Integer, Map<String,String>> generateData() {
        long beginTime = System.currentTimeMillis();
        Map<Integer, Map<String,String>> keystore = new HashMap<>();
        try(
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(dataUrl), "utf-8");
                BufferedReader reader = new BufferedReader(fReader)
        ){
            String line;
            while ((line = reader.readLine()) != null){
                String[] keyValues = line.split(":");
                Map<String,String> valueMap = new HashMap<>();
                String[] value = keyValues[1].split(",");
                valueMap.put("key", value[0]);
                valueMap.put("iv", value[1]);
                keystore.put(Integer.valueOf(keyValues[0]),valueMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("将文件内容输入到程序中并转换成既定的格式耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
        return keystore;

    }

    public static Map<String,String> getKeyMap(Map<Integer, Map<String,String>> keystoreMap,long currentMs) {
        long num = (currentMs / 10000) % keyTotalCount;
        return keystoreMap.get((int)num);
    }

    public static void main(String[] args) {
//        generateKeystoreData();
    }

}
