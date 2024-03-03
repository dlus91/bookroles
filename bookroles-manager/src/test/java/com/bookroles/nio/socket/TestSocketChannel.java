package com.bookroles.nio.socket;

/**
 * @Author: dlus91
 * @Date: 2023/10/16 10:49
 */

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * SocketChannel 是用来连接Socket套接字,也就是说可以用于 网络编程/远程传输 处于网络7层模型中的第4层 运输层
 * SocketChannel 主要用于 处理 网络I/O的通道
 * SocketChannel 是基于TCP连接传输
 * SocketChannel 实现了可选择通道,可以被多路复用的！！！
 */
public class TestSocketChannel {
    /**
     * SocketChannel.open 没有进行网络级联, 需要使用connect接口连接到指定地址+端口
     *
     * SO_SNDBUF 套接字发送缓冲区大小
     * SO_RCVBUF 套接字接收缓冲区大小
     * SO_KEEPALIVE 保持连接
     * SO_REUSEADDR 复用地址
     * SO_LINGER 有数据传输时延缓关闭Channel(只有在非阻塞模式下生效)
     * TCP_NODELAY 禁用NAGLE算法
     *
     * socketChannel.getOption(StandardSocketOptions.上面这些字段)
     * socketChannel.setOption(StandardSocketOptions.上面这些字段)
     */

    int port = 8888;

    @Test
    public void test1() throws IOException {
        //socketChannel还支持自定义协议,应该是基于TCP基础之上的协议 ProtocolFamily family

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com",80));

//        SocketChannel socketChannel = SocketChannel.open();
//        socketChannel.connect(new InetSocketAddress(port));

        socketChannel.isOpen();
        socketChannel.isConnected();
        //是否正在进行连接
        socketChannel.isConnectionPending();
        //是否完成连接
        socketChannel.finishConnect();

        //此该方法返回( SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE )
        int status = socketChannel.validOps();
        System.out.println(status);

        //设置阻塞和非阻塞
        socketChannel.configureBlocking(false);

        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        //阻塞和非阻塞差异就在这里, 阻塞read的时候会一直等到响应,而非阻塞则不会
        socketChannel.read(byteBuffer);
        socketChannel.close();
        System.out.println("read over");

    }


}
