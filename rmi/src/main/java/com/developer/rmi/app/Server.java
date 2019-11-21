package com.developer.rmi.app;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.developer.rmi.implementations.CalculatorImplementation;
import com.developer.rmi.interfaces.Calculator;

public class Server {

	public Server() {

        try {
            
            Registry registry = LocateRegistry.createRegistry(3000);
            
            Calculator calculator = new CalculatorImplementation();

            registry.bind("calculator", calculator);
      
            System.out.println("\nServer running...");

        } catch (Exception error) {
            System.out.println(error);
        }
    }
    public static void main(String args[]) {
        new Server();
    }

}