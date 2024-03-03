package com.bookroles.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Author: dlus91
 * @Date: 2023/10/23 15:31
 */

/**
 * 远程接口实现类，必须继承 UnicastRemoteObject
 * （继承RemoteServer->继承RemoteObject->实现Remote，Serializable），
 * 只有继承UnicastRemoteObject类，才表明其可以作为远程对象，被注册到注册表中供客户端远程调用
 * （补充：客户端lookup找到对象，只是该远程对象的Stub（存根对象），
 * 而服务的的对象有一个对应的骨架Skeleton（用于接收客户端stub的请求，以及调用真实的对象）对应，
 * Stub是远程对象的客户端代理，Skeleton是远程对象的服务端代理，
 * 他们之间协作完成客户端与服务器之间的方法调用时的通信。）
 */
public class HelloImpl extends UnicastRemoteObject implements Hello {

    //因为UnicastRemoteObject的构造方法抛出RemoteException异常
    //因此这里默认的构造方法必须写，也必须声明抛出RemoteException异常
    protected HelloImpl() throws RemoteException {
    }

    @Override
    public String welcome(String name) throws RemoteException {
        return "Hello" + name;
    }
}
