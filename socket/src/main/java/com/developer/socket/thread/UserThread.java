package com.developer.socket.thread;

import com.developer.socket.app.*;
import java.io.*;
import java.net.*;


public class UserThread extends Thread {
    
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
 
    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }
 
    public void run() {
        try {

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
 
            String userName = reader.readLine();
            server.addUserName(userName);
 
            String serverMessage = "\nNew user connected: " + userName;
            server.broadcast(serverMessage, this);
 
            String clientMessage;
 
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);
 
            } while (!clientMessage.equals("bye"));
 
            server.removeUser(userName, this);

            socket.close();
            output.close();   
            
            serverMessage = userName + " exited";
            server.broadcast(serverMessage, this);
 
        } catch (IOException ex) {
            System.out.println("\nError in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    /**
     * Envia uma lista de usuários online para o usuário recém-conectado.
     */
    public void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }
 
    /**
     * Envia uma mensagem para o cliente.
     */
    public void sendMessage(String message) {
        writer.println(message);
    }
}