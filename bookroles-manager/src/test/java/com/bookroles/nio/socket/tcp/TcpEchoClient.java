package com.bookroles.nio.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 22:49
 */
public class TcpEchoClient {

    private Socket socket = null;
    public TcpEchoClient (String IP,int port) throws IOException {
        //这里进行TCP连接
        socket = new Socket(IP,port);
    }
    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()){
            PrintWriter printWriter = new PrintWriter(outputStream);
            Scanner scannerFromSocket = new Scanner(inputStream);
            while (true){
                //1.从键盘读取用户输入内容
                System.out.print("->");
                String request = scanner.next();
                //2.把读取的内容构造成请求发送给服务器
                printWriter.println(request);
                printWriter.flush();//手动刷新缓冲区
                //3.从服务器读取响应内容
                String response = scannerFromSocket.next();
                //4.把响应结果在控制台打印
                System.out.printf("req: %s, resp: %s\n", request, response);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        TcpEchoClient Client = new TcpEchoClient("127.0.0.1",9090);
        Client.start();
    }


}
