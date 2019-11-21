package com.developer.rmi.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.developer.rmi.interfaces.Calculator;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

    private static final long serialVersionUID = 1L;

	public CalculatorImplementation() throws RemoteException {
        super();
    }

    public long factorial(long number) throws RemoteException {
        if (number == 1 || number == 0) return 1;
        return factorial(number - 1) * number;
    }
}