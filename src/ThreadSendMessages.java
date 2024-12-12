import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Classe des threads pour les messages envoyés par un client
 */
public class ThreadSendMessages extends Thread {
    private Client client;

    /**
     * Initialise un thread 
     * @param client
     */
    public ThreadSendMessages(Client client){
        this.client = client;
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
                client.out.println(AES.crypteMessage(message, client.aesKey));
                client.gui.getMessages(message, "Vous: ");
                if (message.equals("bye")){
                    next = false;
                };
            }
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
