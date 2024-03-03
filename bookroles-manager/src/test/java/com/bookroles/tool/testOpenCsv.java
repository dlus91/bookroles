package com.bookroles.tool;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/10/8 20:36
 */
public class testOpenCsv {

    @Test
    public void test1(){
        List<String[]> csvDataMethod = getCsvDataMethod();
        for (String[] strings : csvDataMethod) {
            for (String string : strings) {
                System.out.println(string);
            }
        }
    }

    @Test
    public void test2(){
        List<BookClick> csvDataMethod3 = getCsvDataMethod3(BookClick.class);
        for (BookClick bookClick : csvDataMethod3) {
            System.out.println(bookClick);
        }
    }


    public static List<String[]> getCsvDataMethod(){
        String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\book_click_total.csv";
        List<String[]> list = new ArrayList<String[]>();
        int i = 0;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            CSVReader csvReader = new CSVReaderBuilder(
                    new BufferedReader(
                            new InputStreamReader(fileInputStream, "utf-8"))).build();
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                String[] next = iterator.next();
                //去除第一行的表头，从第二行开始
                if (i >= 1) {
                    list.add(next);
                }
                i++;
            }
            return list;
        } catch (Exception e) {
            System.out.println("CSV文件读取异常");
            return list;
        }
    }


    public static <T> List<T> getCsvDataMethod3(Class<T> clazz) {
        String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\book_click_total.csv";
        InputStreamReader in = null;
        CsvToBean<T> csvToBean = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            in = new InputStreamReader(fileInputStream, "utf-8");
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            csvToBean = new CsvToBeanBuilder<T>(in).withMappingStrategy(strategy).build();
        } catch (Exception e) {
            System.out.println("数据转化失败");
            return null;
        }
        return csvToBean.parse();
    }


}
