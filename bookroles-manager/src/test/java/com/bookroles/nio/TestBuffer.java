package com.bookroles.nio;

import lombok.val;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @Author: dlus91
 * @Date: 2023/10/16 18:47
 */
public class TestBuffer {

    String file = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\abc.txt";

    @Test
    public void buffer01() throws IOException {
        //创建 FileChannel
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        FileChannel channel = accessFile.getChannel();

        //创建buffer大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int bytesRead = channel.read(buffer);
        while (bytesRead != -1){
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
            System.out.println("last: " + bytesRead);
        }

        accessFile.close();


    }

    @Test
    public void buffer02() throws FileNotFoundException {
        val accessFile = new RandomAccessFile(file, "r");

//        accessFile.getChannel().map(FileChannel.MapMode.READ_ONLY,0, )




    }

}
