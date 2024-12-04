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
        String pseudo;
        boolean next = true;
        try {
            while (next) {   
                messageCrypté = client.in.readLine();     
                // si la variable server n'est pas null, alors c'est un client qui envoie un message au server
                if (server != null) {
                    messageDecrypté = AES.decrypteMessage(messageCrypté,client.aesKey);
    
                    System.out.println("message crypté : " + messageCrypté);
                    System.out.println("message décrypté : "+ messageDecrypté);
                    server.diffuserMessage(messageDecrypté, client);
                } else { // sinon c'est le server qui distribue le message à tous les autres clients
                    pseudo = messageCrypté.split(" ")[0];
                    messageCrypté = messageCrypté.split(" ")[1];
                    messageDecrypté = AES.decrypteMessage(messageCrypté,client.aesKey);

                    client.gui.getMessages(messageDecrypté, pseudo);
                }

                
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
