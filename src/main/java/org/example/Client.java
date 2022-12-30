package org.example;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    @Override
    public void run() {
        String host = "netology.homework";
        int port = 6322;

        try (Socket clientSocket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream out = new
                     DataOutputStream(clientSocket.getOutputStream());
             DataInputStream in = new
                     DataInputStream(clientSocket.getInputStream())) {

            while (!clientSocket.isOutputShutdown()) {

                System.out.println(in.readUTF());
                String line = reader.readLine();
                out.writeUTF(line);

                if (line.equals("quit")) {
                    System.out.println("Client kill connections");
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}