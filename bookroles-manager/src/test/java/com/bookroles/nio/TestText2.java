package com.bookroles.nio;

import com.bookroles.tool.BookClick;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.util.stream.Collectors;

/**
 * @Author: dlus91
 * @Date: 2023/10/22 10:34
 */
public class TestText2 {

    @Test
    public void test1(){
        String aaa = "我是啊哈哈\n 你是\n";
        System.out.println(aaa.lines().collect(Collectors.toList()));
        System.out.println(aaa.lines().count());
    }

    @Test
    public void test2(){
        FieldPosition fp = new FieldPosition(NumberFormat.FRACTION_FIELD);
        fp.setBeginIndex(10);
        fp.setEndIndex(20);
        System.out.println(fp);
    }

    @Test
    public void test3(){
        BookClick bookClick = new BookClick();
        bookClick.setId(1);
        bookClick.setCount(10);
        bookClick.setTimestamp(System.currentTimeMillis());
        System.out.println(bookClick);

        FieldPosition fp = new FieldPosition(NumberFormat.FRACTION_FIELD);


        FieldPosition pos_1 = new FieldPosition(DateFormat.Field.AM_PM);
        FieldPosition pos_2 = new FieldPosition(DateFormat.Field.AM_PM);
        boolean i = pos_1.equals(pos_2);
        System.out.println(i);
    }

    @Test
    public void test4(){
        //建立一个数字格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //建立字符数组缓冲器）—— 线程安全
        StringBuffer stringBuffer1 = new StringBuffer();
        //数字格式化，用于格式化时对索引跟踪（第一个字符的索引，紧跟字段最后一个字符的索引）
        FieldPosition fieldPosition = new FieldPosition(NumberFormat.INTEGER_FIELD);
        //十进位小数对象
        BigDecimal bigDecimal = new BigDecimal("1234.456456465");
        //执行格式化
        stringBuffer1 = numberFormat.format(bigDecimal, stringBuffer1, fieldPosition);
        System.out.println("stringBuffer1 = " + stringBuffer1); //结果为1.235
        System.out.println("整数 = " + stringBuffer1.substring(fieldPosition.getBeginIndex(), fieldPosition.getEndIndex()));
        //获取索引
        System.out.println("INTEGER : beginIndex=" + fieldPosition.getBeginIndex() + ",endIndex=" + fieldPosition.getEndIndex());
        fieldPosition = new FieldPosition(NumberFormat.FRACTION_FIELD);
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2 = numberFormat.format(bigDecimal, stringBuffer2, fieldPosition);
        System.out.println("stringBuffer2 = " + stringBuffer2);
        System.out.println("FRACTION: beginIndex=" + fieldPosition.getBeginIndex() + ",endIndex=" + fieldPosition.getEndIndex());
        System.out.println("小数 = " + stringBuffer2.substring(fieldPosition.getBeginIndex(), fieldPosition.getEndIndex()));
    }

    @Test
    public void test5(){
        double i = 123123.321312;
        NumberFormat numberFormat = NumberFormat.getInstance();
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(NumberFormat.INTEGER_FIELD);
        stringBuffer = numberFormat.format(i, stringBuffer, fieldPosition);
        System.out.println(stringBuffer);
        System.out.println("整数 = " + stringBuffer.substring(fieldPosition.getBeginIndex(),fieldPosition.getEndIndex()));
    }

    @Test
    public void test6(){
        double i = 123123.321312;
        NumberFormat numberFormat = NumberFormat.getInstance();
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(NumberFormat.FRACTION_FIELD);
        stringBuffer = numberFormat.format(i, stringBuffer, fieldPosition);
        System.out.println(stringBuffer);
        System.out.println("小数 = " + stringBuffer.substring(fieldPosition.getBeginIndex(), fieldPosition.getEndIndex()));
    }

    @Test
    public void test7(){

    }


}
