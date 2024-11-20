import java.io.BufferedReader;
import java.io.IOException;
import java.security.Key;

public class ThreadGetMessages extends Thread {
    private BufferedReader in;
    private Key aesKey;
    private Server server;

    public ThreadGetMessages(Client client){
        this.in = client.in;
        this.aesKey = client.aesKey;
    }

    public ThreadGetMessages(Client client, Server server){
        this.in = client.in;
        this.aesKey = client.aesKey;
        this.server = server;
    }

    /**
     * Code exécuté par le Thread
     */
    @Override
    public void run(){
        String message;
        try {
            while (!(message = in.readLine()).equals("bye")) {           
                System.out.println("message crypté : " + message);
                System.out.println("message décrypté : "+AES.decrypteMessage(message, aesKey));
                if (server != null) {
                    server.diffuserMessage(AES.decrypteMessage(message, aesKey));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
