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
        boolean next = true;
        try {
            while (next) {    
                message = stdIn.readLine();       
                out.println(AES.crypteMessage(message, aesKey));
                if (message.equals("bye")){
                    next = false;
                };
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
