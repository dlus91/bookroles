package com.bookroles.nio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 19:15
 */
public class TestNio {

    String fileOnePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\abc.txt";

    String fileTwoPath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\cde.txt";

    //单位字节byte
    int size = 10 * 1024;

    long beginTime;

    @Before
    public void init(){
        beginTime = System.currentTimeMillis();
    }

    @After
    public void destory(){
        System.out.println(System.currentTimeMillis() - beginTime + "ms");
    }

    //读nio
    @Test
    public void testRead() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileOnePath, "r");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        int bytesRead = channel.read(buffer);

        System.out.println("size:"+channel.size());
        while (bytesRead != -1){
            System.out.println("read:" + bytesRead);
            buffer.flip();
            //是否有剩余的内容
            while (buffer.hasRemaining()){
//                System.out.println((char)buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        randomAccessFile.close();
        System.out.println("====succees====");
    }

    //写nio
    @Test
    public void testWrite() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileOnePath, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(size);

        String newData = "data bookroles.com";
        String newData1 = " haha bookroles.com";
        buffer.clear();

        buffer.put(newData.getBytes());
        buffer.put(newData1.getBytes());
        buffer.flip();

        System.out.println(channel.size());
        while (buffer.hasRemaining()){
            channel.write(buffer);
        }
        channel.close();

        System.out.println("====succees====");
    }


    // transferFrom/transferTo 是通道之间的传输/ channel间的操作
    // transferFrom 是将一个通道的数据 给另一个读通道
    // transferTo 是将一个通道的数据 给另一个写通道
    @Test
    public void test3() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileOnePath, "rw");
        FileChannel fromChannel = accessFile.getChannel();

        RandomAccessFile accessFile2 = new RandomAccessFile(fileTwoPath, "rw");
        FileChannel toChannel = accessFile2.getChannel();


        //fromChannel 传输到 toChannel
//        toChannel.transferFrom(fromChannel, 0, fromChannel.size());

        //fromChannel 传输到 toChannel
        fromChannel.transferTo(0, fromChannel.size(), toChannel);

        fromChannel.close();
        toChannel.close();

    }

    @Test
    public void test4() throws FileNotFoundException {

    }

}
