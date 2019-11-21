package com.developer.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {

    public long factorial(long x) throws RemoteException;
}