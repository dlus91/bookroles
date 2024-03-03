package com.bookroles.rmi;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Author: dlus91
 * @Date: 2023/10/23 15:46
 */
public class Server {

    //服务端创建了一个注册表，并注册客户端需要的对象
    public static void main(String[] args) throws RemoteException, MalformedURLException, NamingException {
        //创建对象
        HelloImpl hello = new HelloImpl();
        //本地主机上的远程对象注册表Registry的实例
        //并指定端口，这一步必不可少（Java默认端口是1099）
        Registry registry = LocateRegistry.createRegistry(1099);
        //绑定对象到注册表，并给它取名为hello
        registry.rebind("hello", hello);



//        //ps 向注册器注册远程对象有三种方式 前两种静态存根都是不被推荐的，可能会被移除
//        //方式1：调用 java.rmi.registry.Registry 接口的 bind 或 rebind 方法
//        Registry registry1 = LocateRegistry.createRegistry(1099);
//        registry1.rebind("hello", hello);
//        //方式2：调用命名服务类 java.rmi.Naming 的 bind 或 rebind 方法
//        Naming.rebind("hello", hello);
//        //方式3：调用JNDI API 的 javax.naming.Context 接口的 bind 或 rebind 方法
//        InitialContext namingContext = new InitialContext();
//        namingContext.rebind("rmi:hello", hello);

    }


}
