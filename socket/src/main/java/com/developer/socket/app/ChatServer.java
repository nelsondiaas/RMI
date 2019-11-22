package com.developer.socket.app;

import com.developer.socket.thread.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
 
    public ChatServer(int port) {
        this.port = port;
    }
 
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            
            System.out.printf("\n[+] Server running with sucess at PORT: " + port);
 
            while (true) {
                
                Socket socket = serverSocket.accept();
                System.out.printf("\n\nNew user connected");
 
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
 
            }
 
        } catch (IOException ex) {
            System.out.println("\n\nError in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Entrega uma mensagem de um usuário para outro (Broadcast)
     */
    public void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }
 
    /**
     * Armazena o nome de usuário do cliente recém-conectado.
     */
    public void addUserName(String userName) {
        userNames.add(userName);
    }
 
    /**
     * Quando um cliente é desconectado, remove o nome de usuário associado e UserThread
     */
    public void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.printf("\n\nThe user " + userName + " exited");
        }
    }
 
    public Set<String> getUserNames() {
        return this.userNames;
    }
 
    /**
     * Retorna true se houver outros usuários conectados (sem contar o usuário conectado no momento)
     */
    public boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    public static void main(String[] args) {
       
        int port = 3000;
        
        ChatServer server = new ChatServer(port);
        server.execute();
    }
}