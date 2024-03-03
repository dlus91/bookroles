package com.bookroles.nio;

import org.junit.Test;

import java.text.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/10/22 21:04
 */
public class TestText3 {

    //一次性使用的MessageFormat
    @Test
    public void test1(){
        int planet = 7;
        String event = "a disturbance in the Force";

        String result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.", planet, new Date(), event);
        System.out.println(result);
    }

    //可重复使用的MessageFormat
    @Test
    public void test2(){
        int fileCount = 1273;
        String diskName = "MyDisk";
        Object[] testArgs = {fileCount, diskName};
        MessageFormat format = new MessageFormat("The disk \"{1}\" containes {0} file(s). ");
        System.out.println(format.format(testArgs));
    }

    //复杂模式的，可以使用ChoiceFormat生成单数和复数的正确形式
    @Test
    public void test3(){
        MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
        double[] filelimits = {0,1,2};
        String[] filepart = {"no files", "one file", "{0,number} files"};
        ChoiceFormat fileform = new ChoiceFormat(filelimits, filepart);
        form.setFormatByArgumentIndex(0, fileform);

        int fileCount = 123450;
        String disName = "MyDiskssssssssssss";
        Object[] testArgs = {fileCount, disName};
        System.out.println(form.format(testArgs));

    }

    @Test
    public void test4(){
        String format = MessageFormat.format("There {0,choice,0#are no files|1#is one file|1<are {0,number,integer} files}.", 35);
        System.out.println(format);

        int fileCount = 2123;
        String disName = "asdasdasdas";
        Object[] testArgs = {fileCount, disName};
//        System.out.println(platform.format(testArgs));

    }

    //在字符串中多次解析单个参数时，最后一个匹配将是解析的最终结果
    @Test
    public void test5(){
        MessageFormat mf = new MessageFormat("{0,number,#.##},{0,number,#.#}");
        Object[] objs = {3.1415};
        String result = mf.format(objs);
        System.out.println(result);
        objs = null;
        objs = mf.parse(result, new ParsePosition(0));
        Arrays.stream(objs).forEach(System.out::println);
    }

    //使用包含多次出现的相同参数的模式使用MessageFormat对象进行语法分析将返回最后一个匹配项
    @Test
    public void test6(){
        MessageFormat mf = new MessageFormat("{0},{0},{0}");
        String forParsing = "x,y,z";
        Object[] objs = mf.parse(forParsing, new ParsePosition(0));
        Arrays.stream(objs).forEach(System.out::println);
    }

    @Test
    public void test7(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("asdsad");

    }

}
