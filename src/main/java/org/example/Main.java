package org.example;

public class Main {

    static Server server;
    static Client client;

    public static void main(String[] args) {

        server = new Server();
        client = new Client();

        Thread serverThread = new Thread(server);
        Thread clientThread = new Thread(client);

        serverThread.start();
        clientThread.start();

        System.out.println("Главный поток завершён...");
    }
}

