package com.bookroles.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @Author: dlus91
 * @Date: 2023/10/23 15:28
 */
//远程接口，该接口需要继承Remote接口，并且接口中的方法全部要抛出RemoteException异常
public interface Hello extends Remote {

    public String welcome(String name) throws RemoteException;

}
