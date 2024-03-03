package com.bookroles.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 21:10
 */
public class TestNioDatagram2 {

    @Test
    public void testSendChannel() throws IOException, InterruptedException {
        DatagramChannel channel = DatagramChannel.open();
        DatagramSocket socket = channel.socket();
        InetSocketAddress localAddr = new InetSocketAddress(7000);
        SocketAddress remoteAddr = new InetSocketAddress(InetAddress.getByName("localhost"), 8000);
        socket.bind(localAddr);

        int i = 1;
        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            System.out.println("缓冲区的剩余字节为：" + buffer.remaining());
            int send = channel.send(buffer, remoteAddr);
            System.out.println(i+"发送的字节数为：" + send);
            Thread.sleep(500);
            i++;
        }
    }


    @Test
    public void testReceiveChannel() throws InterruptedException, IOException {
        int cmd = 1;

        final int ENOUGH_SIZE = 1024;
        final int SMALL_SIZE = 4;

        boolean isBlocked = true; //阻塞或非阻塞模式
        int size = ENOUGH_SIZE; //表示缓冲区的大小

        switch (cmd) {
            case 1 -> {
                isBlocked = true;
                size = ENOUGH_SIZE;
            }
            case 2 -> {
                isBlocked = true;
                size = SMALL_SIZE;
            }
            case 3 -> {
                isBlocked = false;
                size = ENOUGH_SIZE;
            }
            case 4 -> {
                isBlocked = false;
                size = SMALL_SIZE;
            }
        }

        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(isBlocked);
        ByteBuffer buffer = ByteBuffer.allocate(size);
        DatagramSocket socket = channel.socket();
        InetSocketAddress localAddr = new InetSocketAddress(8000);
        socket.bind(localAddr);

        int i = 1;
        while (true){
            System.out.println("开始接收数据包");
            SocketAddress remoteAddr = channel.receive(buffer);
            if(remoteAddr == null){
                System.out.println("没有收到数据包");
            }else{
                buffer.flip();
                System.out.println(i+"接收到的数据包的大小为" + buffer.remaining());
            }
            Thread.sleep(500);
            i++;
        }


    }


}
