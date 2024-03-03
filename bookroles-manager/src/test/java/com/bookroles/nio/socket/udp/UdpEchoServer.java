package com.bookroles.nio.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 22:42
 */
public class UdpEchoServer {

    //首先定义一个socket对象
    //通过网络通信，使用socket对象
    private DatagramSocket socket = null;

    public UdpEchoServer (int port) throws SocketException {
        //构造socket时要记得指定绑定端口
        socket = new DatagramSocket(port);//抛出异常是因为一个进程只能绑定一个端口
    }
    public void start() throws IOException {
        System.out.println("服务器启动！");
        while (true){
            //每次循环要做的事
            //1.读取请求并解释
            DatagramPacket requestPacket = new DatagramPacket(new byte[4096],4096);
            socket.receive(requestPacket);
            //将数据包构造成String类型，这个操作是为了结果更直观
            String request = new String(requestPacket.getData(),0,requestPacket.getLength());
            //2.根据请求计算响应
            String response = process(request);
            //3.把响应结果写入客户端
            DatagramPacket responsePacket = new DatagramPacket(response.getBytes(),response.getBytes().length,
                    requestPacket.getSocketAddress());//getSocketAddress里包含IP地址和端口号
            //数组的起始位置和数组的长度,还有将这个数据包发给谁
            socket.send(requestPacket);
            System.out.printf("[%s:%d] rep: %s ,reap : %s\n",requestPacket.getAddress().toString(),
                    requestPacket.getPort(),request,response);//打印服务器日志
        }
    }

    public static void main(String[] args) throws IOException {
        UdpEchoServer udpEchoServer = new UdpEchoServer(9090);
        udpEchoServer.start();
    }

    public String process(String request) {
        return request;
    }

}
