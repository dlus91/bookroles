package com.bookroles.nio.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 22:51
 */
public class TcpEchoServer {

    private ServerSocket serversocket = null;
    public TcpEchoServer(int port) throws IOException {
        serversocket = new ServerSocket(port);
    }
    public void start() throws IOException {
        System.out.println("服务器启动！");
        while (true){
            Socket clientsocket = serversocket.accept();
            processConnection(clientsocket);
        }
    }

    private void processConnection(Socket clientsocket) throws IOException {
        System.out.printf("[%s:%d] 客户端上线！\n",clientsocket.getInetAddress().toString(),
                clientsocket.getPort());
        try (InputStream inputStream = clientsocket.getInputStream();
             OutputStream outputStream = clientsocket.getOutputStream() ){
            Scanner scanner = new Scanner(inputStream);
            PrintWriter printWriter = new PrintWriter(outputStream);
            while (true){
                //1.读取请求
                if (!scanner.hasNext()){
                    //读取的流到了结尾了
                    System.out.printf("[%s:%d] 客户端已下线！\n",clientsocket.getInetAddress().toString()
                            ,clientsocket.getPort());
                    break;
                }
                //用字符串接收用户写入的数据
                String request = scanner.next();
                //根据请求计算响应
                String response = process(request);
                //将响应写给客户端
                printWriter.println(response);
                printWriter.flush();
                System.out.printf("[%s:%d] rep: %s; resp: %s\n",clientsocket.getInetAddress().toString(),
                        clientsocket.getPort(),request,response);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            clientsocket.close();
        }
    }

    private String process(String request) {
        return request;
    }

    public static void main(String[] args) throws IOException {
        TcpEchoServer tcpEchoServer = new TcpEchoServer(9090);
        tcpEchoServer.start();
    }

}
