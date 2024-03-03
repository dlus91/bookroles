package com.bookroles.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 20:25
 */
public class TestNioDatagram1 {


    // nio channel有四个实现类 FileChannel/文件io,DatagramChannel/utpio,SocketChannel/tcpio,ServerSocketChannel/client-server规范监听

    //发送的实现
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
        //打开 DatagramChannel
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1",9999);
        int i = 0;
        //发送
        while(i < 10) {
            ByteBuffer buffer = ByteBuffer.wrap(("你好"+i).getBytes(StandardCharsets.UTF_8));
            //没有 connect时 它会进行安全检查checkConnect,可以通过调用connect来避免
            sendChannel.send(buffer,sendAddress);
            System.out.println("已经发送完成");
            Thread.sleep(1000);
            i++;
        }
    }

    //接收的实现
    @Test
    public void receiveDatagram() throws IOException {
        //打开 DatagramChannel
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress(9999);
        //绑定
        receiveChannel.bind(sendAddress);
        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //接收
        while(true) {
            buffer.clear();
            SocketAddress socketAddress = receiveChannel.receive(buffer);
            buffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(Charset.forName("UTF-8").decode(buffer));
        }

    }


    //批量操作
    @Test
    public void testConnect() throws IOException, InterruptedException {
        //打开DatagramChannel
        DatagramChannel connChannel = DatagramChannel.open();
        //绑定
        connChannel.bind(new InetSocketAddress(9999));
        //连接
        connChannel.connect(new InetSocketAddress("127.0.0.1",9999));
        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int count = 10;

        for (int i = 0; i < count; i++) {
            String data = "你好 "+i;
            connChannel.write(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));
            System.out.println("发送:"+data);
        }


        int i = 0;
        while(i < count) {
            buffer.clear();
            connChannel.read(buffer);
            buffer.flip();
            System.out.println(i+"接收:"+Charset.forName("UTF-8").decode(buffer));
            i++;
        }

//        buffer.clear();
//        connChannel.close();

    }


}
