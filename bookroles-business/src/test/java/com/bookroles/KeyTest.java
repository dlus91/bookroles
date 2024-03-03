package com.bookroles;

import org.junit.Test;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/9/23 10:13
 */
public class KeyTest {

    public static String sKey="a90e7d2eb24c4b8b88994ab065e377d3";
    private final static String ivParameter = "bookrolesABcDEFh";

    @Test
    // 当前10秒换一次key策略
    public void genKeyTest() throws NoSuchAlgorithmException, IOException {
        System.out.println("=============" + sKey.length());
        System.out.println("=============" + ivParameter.length());


        String keyCharts = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String ivCharts = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        long beginTime = System.currentTimeMillis();
        Map<Integer, Map<String,String>> keystore = new HashMap<>();
        int keyTotalCount = 6 * 60 * 24;
        System.out.println("开始生成：" + keyTotalCount + "条密钥键值对");
        for (int keyNum = 0; keyNum < keyTotalCount;keyNum++) {
            StringBuilder key = new StringBuilder();
            SecureRandom keyStrong = SecureRandom.getInstanceStrong();
            for (int i = 0; i < 32; i++) {
                key.append(keyCharts.charAt(keyStrong.nextInt(keyCharts.length())));
            }
            StringBuilder iv = new StringBuilder();
            SecureRandom ivStrong = SecureRandom.getInstanceStrong();
            for (int i = 0; i < 7; i++) {
                iv.append(ivCharts.charAt(ivStrong.nextInt(ivCharts.length())));
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
        FileWriter fileWriter = new FileWriter("H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\test_keystore.txt");
        fileWriter.append(strAll);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("输出到文件总耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
    }
    
    @Test
    public void getKeyTest() throws InterruptedException {
        String path = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\test_keystore.txt";
        long beginTime = System.currentTimeMillis();
        Map<Integer, Map<String,String>> keystore = new HashMap<>();
        try(
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(path), "utf-8");
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
        System.out.println("map size : " + keystore.size());
        int keyTotalCount = 6 * 60 * 24;
        for (int i = 1; i < 5; i++) {
            long cms = System.currentTimeMillis() / 10000;
            long num = cms % keyTotalCount;
            Map<String, String> stringStringMap = keystore.get((int)num);
            System.out.println("第"+i+"次，测试输出第" +num+ "条，值为：" + stringStringMap);
            Thread.sleep(6000);
        }
    }

    @Test
    public void test3(){
        int keyTotalCount = 6 * 60 * 24;
        long cms = System.currentTimeMillis() / 10000;
        long num = cms % keyTotalCount;
        System.out.println("ms:" + cms + " total:" + keyTotalCount + " ms%total:" + num);
    }

}
