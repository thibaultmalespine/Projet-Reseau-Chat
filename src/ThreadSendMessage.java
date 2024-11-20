import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadSendMessage extends Thread {
    Communication communication;

    public ThreadSendMessage(Communication communication){
        this.communication = communication;
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
                communication.out.println(AES.crypteMessage(message, communication.aesKey));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
