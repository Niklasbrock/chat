package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Terminal {

    public static void main(String[] args) {
        boolean running = true;
        Scanner systemInput = new Scanner(System.in);
        try {
            Socket link = new Socket(InetAddress.getLocalHost(), 1337);
            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);
            while (running) {
                String inputMessage;
                do {
                    inputMessage = input.nextLine();
                    if (!inputMessage.equals("over")) {
                        System.out.println(inputMessage);
                    }
                } while (!inputMessage.equals("over"));
                System.out.println("done receiving from server");
                output.println(systemInput.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
