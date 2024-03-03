package com.bookroles.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: dlus91
 * @Date: 2023/10/16 10:33
 */
//ServerSocketChannel
public class TestServerSocketChannel {

    //可以用浏览器 模拟客户端发送或者说链接
    public static void main(String[] args) throws IOException, InterruptedException {
        //端口号
        int port = 8888;

        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello bookroles.com".getBytes());

        //ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //绑定
        ssc.socket().bind(new InetSocketAddress(port));

        //设置非阻塞
        //非阻塞false,阻塞true,可以自己尝试
        ssc.configureBlocking(true);

        //监听有新链接传入
        while (true) {
            SocketChannel accept = ssc.accept();
            System.out.println("等待新链接传入");
            if(accept == null){
                System.out.println("无新链接传入");
                Thread.sleep(1500);
            }else {
                System.out.println("处理新链接,来自：" + accept.socket().getRemoteSocketAddress());
                buffer.rewind(); //指针 指向0
                accept.write(buffer);
                accept.close();
            }

        }




    }


}
