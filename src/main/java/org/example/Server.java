package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    StringBuilder stringBuilder = new StringBuilder();

    private static String weatherInCity(String city) throws IOException {
        Model model = new Model();
        Weather weather = new Weather();
        return weather.getWeather(city, model);
    }

    @Override
    public void run() {
        System.out.println("Server started");
        int port = 6322;
        String name;
        String city;


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                     DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {

                    System.out.println("New connection accepted. Port: " + clientSocket.getPort());
                    out.writeUTF("What is you name?");
                    name = in.readUTF();
                    out.writeUTF("Hi " + name + ", your port is " + clientSocket.getPort() + ". To continue press enter");
                    while (!clientSocket.isClosed()) {
                        inMessage(in);
                        out.writeUTF("Do you want to know what the weather is outside? Enter yes/no or да/нет. To exit, enter quit.");
                        switch (in.readUTF()) {
                            case "no":
                            case "нет":
                                out.writeUTF("Good bye!");
                                break;
                            case "yes":
                            case "да":
                                out.writeUTF("Enter the name of your city?");
                                city = in.readUTF();
                                out.writeUTF(weatherInCity(city) + "\npress enter");
                                break;
                            case "quit":
                                System.out.println("Client initialize connections suicide ...");
                                return;
                            default:
                                out.writeUTF("Enter yes/no or да/нет. \npress enter");
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String inMessage(DataInputStream in) {
        try {
            String i = in.readUTF();
            if (!i.equals("")) {
                stringBuilder.append(in.read());
                inMessage(in);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(stringBuilder);
    }
}
