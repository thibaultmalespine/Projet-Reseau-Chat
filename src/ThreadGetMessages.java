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
        boolean next = true;
        try {
            while (next) {   
                messageCrypté = client.in.readLine();     
                // si la variable server n'est pas null, alors c'est un client qui envoie un message au server
                if (server != null) {
                   serverResponse(messageCrypté);
                } else { // sinon c'est le server qui distribue le message à tous les autres clients
                    clientResponse(messageCrypté);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Gère la réponse du serveur lorsqu'il reçoit un message
     * @param messageCrypté
     */
    private void serverResponse(String messageCrypté){
        String messageDecrypté = AES.decrypteMessage(messageCrypté,client.aesKey);
        System.out.println("message crypté : " + messageCrypté);
        System.out.println("message décrypté : "+ messageDecrypté);
        if(messageDecrypté.charAt(0) == '@'){
            sendPrivateMessage(messageDecrypté);
        } else {
            server.diffuserMessage(messageDecrypté, client);
        }
    }
    /**
     * Gère les messages privées reçu par le serveur
     * @param messageDecrypté
     */
    private void sendPrivateMessage(String messageDecrypté){
        String pseudo = messageDecrypté.split(" ")[0].substring(1);
        try {
            messageDecrypté = messageDecrypté.split(" ")[1];
        } finally {
            server.messagePrivé(messageDecrypté, client, pseudo);
        };
    }
    /**
     * Gère la réponse du client lorsqu'il reçoit un message
     * @param messageCrypté
     */
    private void clientResponse(String messageCrypté){
        String pseudo = messageCrypté.split(" ")[0];
        messageCrypté = messageCrypté.split(" ")[1];
        String messageDecrypté = AES.decrypteMessage(messageCrypté,client.aesKey);

        client.gui.getMessages(messageDecrypté, pseudo);
    }
}
