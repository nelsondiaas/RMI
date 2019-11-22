package com.developer.socket.thread;

import com.developer.socket.app.*;
import java.io.*;
import java.net.*;

public class ReadThread extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
 
    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {

            InputStream input = this.socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

        } catch (IOException ex) {
            System.out.printf("\nError getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
        while (true) {
            try {
                
                String response = reader.readLine();
                System.out.println("\n" + response);
                
                /* imprime o nome de usuário após exibir a mensagem do servidor */
                
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
                
            } catch (IOException ex) {
                System.out.printf("\nError reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}