package com.bookroles.nio.socket;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: dlus91
 * @Date: 2023/10/16 11:30
 */
public class TestDatagramChannel {

    int port = 9999;

    int dataSize = 1024;


    //发送的实现
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
        //打开 DatagramChannel
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress inetAddress = new InetSocketAddress("127.0.0.1", port);

        int i = 0;
        //发送
        while (true){
            ByteBuffer byteBuffer = ByteBuffer.wrap(("hello "+i) .getBytes("UTF-8"));
            sendChannel.send(byteBuffer,inetAddress);
            System.out.println("已经完成发送");
            Thread.sleep(1000);
            i++;
        }


    }

    @Test
    public void receiveDatagram() throws IOException, InterruptedException {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveInetAddress = new InetSocketAddress(port);
        receiveChannel.bind(receiveInetAddress);

        ByteBuffer buffer = ByteBuffer.allocate(dataSize);

        while (true){
            buffer.clear();
            SocketAddress receive = receiveChannel.receive(buffer);

            buffer.flip();

            System.out.printf("ip:%s ", receive.toString());

            System.out.println(Charset.forName("UTF-8").decode(buffer));

        }
    }

    @Test
    public void testConnect() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(port));
        datagramChannel.connect(new InetSocketAddress("127.0.0.1", port));

        int count = 20;

        for (int i = 0; i < count; i++) {
            String data = "hello bookroles"+i;
            ByteBuffer wrap = ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8));
            datagramChannel.write(wrap);
            System.out.println("发送:"+data);
        }


        ByteBuffer byteBuffer = ByteBuffer.allocate(dataSize);
        int i = 0;
        while (i < count){
            byteBuffer.clear();
            int read = datagramChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.printf("%d count: %d ", i,read);
            System.out.println(Charset.forName("UTF-8").decode(byteBuffer));
            i++;
        }

//        byteBuffer.clear();
//        datagramChannel.close();

    }


}
