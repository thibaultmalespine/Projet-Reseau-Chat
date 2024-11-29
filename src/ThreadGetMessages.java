import java.io.IOException;

public class ThreadGetMessages extends Thread {
    private Server server;
    private Client client;

    public ThreadGetMessages(Client client){
        this.client = client;
    }

    public ThreadGetMessages(Client client, Server server){
        this.client = client;
        this.server = server;
    }

    /**
     * Code exécuté par le Thread
     */
    @Override
    public void run(){
        String message;
        try {
            while (!AES.decrypteMessage(message = client.in.readLine(),client.aesKey).equals("bye")) {           
                System.out.println("message crypté : " + message);
                System.out.println("message décrypté : "+AES.decrypteMessage(message, client.aesKey));
                if (server != null) {
                    server.diffuserMessage(AES.decrypteMessage(message, client.aesKey), client);
                } else {
                    client.gui.getMessages(AES.decrypteMessage(message, client.aesKey),"Autres: ");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
