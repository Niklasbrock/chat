package Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements Runnable{
    private String name;
    private boolean active;
    private Socket link;
    private PrintWriter heartbeat;

    @Override
    public void run() {
        System.out.println("Thread has been executed");
        active = true;
        try {
            heartbeat = new PrintWriter(link.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (active) {
            heartbeat.println("IMAV " + name +", "+link.getInetAddress() + ":" + link.getPort());
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop(){
        active = false;
    }
    public ChatClient(String name, Socket link) {
        this.name = name;
        this.link = link;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getLink() {
        return link;
    }

    public void setLink(Socket link) {
        this.link = link;
    }

}