package com.bookroles.rmi;

/**
 * @Author: dlus91
 * @Date: 2023/10/23 15:42
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 如果一个远程类已经继承其他类，无法再继承UnicastRemoteObject类，
 * 那么可以在构造方法中调用UnicastRemoteObject类的静态exportObject,
 * 同样，远程类的构造方法也必须声明抛出RemoteException
 */
public class HelloImpl2 implements Hello{

    public HelloImpl2() throws RemoteException{
        //参数port指定监听端口，如果取值为0，就表示监听任意一个匿名端口
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public String welcome(String name) throws RemoteException {
        return "Hello2" + name;
    }
}
