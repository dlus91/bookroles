package com.bookroles.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: dlus91
 * @Date: 2023/10/16 22:42
 */
public class TestReadRow1 {

    private static String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\abc.txt";


    public static void main(String[] args) throws IOException {
        File file = new File(filePath);
        TestReadRow1 mf = new TestReadRow1();
        mf.parseFile(file);
    }

    public void parseFile(File file) throws IOException {
        if(!file.exists()){
            System.out.println("文件不存在parseFile() ======= filePath -->" + file.getPath());
            return;
        }
        long length = file.length();
        MappedByteBuffer buffer = new RandomAccessFile(file, "r")
                .getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++){
            if(buffer.get(i) == 10){//判断遇到换行符,处理此行数据
                System.out.println(sb);
                sb.delete(0, sb.length());
            }else if(i == length - 1){//判断到了最后一行,处理此行数据
                sb.append((char) buffer.get(i));
                System.out.println();
            }else {//拼接成一行数据
                sb.append((char) buffer.get(i));
            }
        }
        sb = null;


    }

}
