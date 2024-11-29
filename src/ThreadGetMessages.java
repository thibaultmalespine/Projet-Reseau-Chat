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
        String messageCrypté;
        String messageDecrypté;
        boolean next = true;
        try {
            while (next) {   
                messageCrypté = client.in.readLine();     
                messageDecrypté = AES.decrypteMessage(messageCrypté,client.aesKey);

                System.out.println("message crypté : " + messageCrypté);
                System.out.println("message décrypté : "+ messageDecrypté);
                if (server != null) {
                    server.diffuserMessage(messageDecrypté, client);
                } else {
                    client.gui.getMessages(messageDecrypté,"Autres: ");
                }

                if (messageDecrypté.equals("bye")) {
                    next = false;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
