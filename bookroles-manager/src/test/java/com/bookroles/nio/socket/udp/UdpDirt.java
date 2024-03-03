package com.bookroles.nio.socket.udp;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/10/15 22:44
 */
public class UdpDirt extends UdpEchoServer {

    private Map<String, String> dict = new HashMap<>();

    public UdpDirt(int port) throws SocketException {
        super(port);
        dict.put("dog", "小狗");
        dict.put("cat", "小猫");
        dict.put("fish", "鱼");
    }

    @Override
    public String process(String request) {
        return dict.getOrDefault(request, "单词没有查到");
    }

    public static void main(String[] args) throws IOException {
        UdpDirt udpDirt = new UdpDirt(9090);
        udpDirt.start();
    }

}
