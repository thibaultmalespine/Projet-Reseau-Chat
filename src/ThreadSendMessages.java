import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Key;

public class ThreadSendMessages extends Thread {
    private PrintWriter out;
    private Key aesKey;

    public ThreadSendMessages(Client client){
        this.out = client.out;
        this.aesKey = client.aesKey;
    }

    /**
     * Code exécuté par le Thread
     */
    @Override
    public void run(){
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String message;
        try {
            while (!(message = stdIn.readLine()).equals("bye")) {           
                out.println(AES.crypteMessage(message, aesKey));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
