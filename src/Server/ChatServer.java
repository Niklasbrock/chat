package Server;

import Client.ChatClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatServer {
    private static ServerSocket serverSocket;
    private static Socket link;
    private static Scanner input;
    private static PrintWriter output;
    private static ExecutorService service;
    private static ArrayList<Thread> threads;
    private static Set<String> activeUserNames;

//    THREAD IDEA: everytime a new client connects,
//    a thread is created for the client connection.
    public static void main(String[] args){
        /*String text = "JOIN LeoLord, 127.0.0.1:1337";
        String[] textSplit = text.split("\\s|\\,|\\:");
        System.out.println(textSplit[0]);
        System.out.println(textSplit[1]);
        System.out.println(textSplit[2]);
        System.out.println(textSplit[3]);
        System.out.println(textSplit[4]);*/
        boolean running = true;
        service = Executors.newFixedThreadPool(5);
        activeUserNames = new HashSet<>();
        threads = new ArrayList<>();
        startServer(1337);
        output.println("Welcome to this Chat Server");
        while(running){
            output.println("Awaiting request...");
            output.println("over");
            String message = input.nextLine();
            String[] messageSplit = message.split("\\s|\\,|\\:");
            switch (messageSplit[0]){
                case "JOIN":
                    output.println("You have requested to join the server");
                    output.println(requestJoin(messageSplit));
                    break;
                case "DATA":
                    output.println("You have requested to send data");
                    break;
                case "IMAV":
                    output.println(messageSplit[1] + "is still connected on " + messageSplit[3] + ":" + messageSplit[4]);
                    break;
                case "QUIT":
                    output.println("You have requested to quit the server");
                    output.println(requestQuit());
                    break;
                default:
                    output.println("Invalid request");
            }
        }
    }

    public static String requestJoin(String[] messageSplit){
        try {
            if (activeUserNames.contains(messageSplit[1])){
                return "User with that name is already connected";
            }else activeUserNames.add(messageSplit[1]);
            ChatClient newClient = new ChatClient(messageSplit[1], new Socket(messageSplit[3], Integer.parseInt(messageSplit[4])));
            Thread newThread = new Thread(newClient);
            threads.add(newThread);
            service.execute(newThread);
            return "Connected as "+ newClient.getName() + " " + messageSplit[3] + ":" + messageSplit[4];
        } catch (IOException e) {
            e.printStackTrace();
            return "Error connecting";
        }
    }

    public static String requestQuit(){
        return null;
    }
    
    public static void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            link = serverSocket.accept();
            input = new Scanner(link.getInputStream());
            output = new PrintWriter(link.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
