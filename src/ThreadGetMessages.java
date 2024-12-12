import java.io.IOException;

/**
 * Classe des threads pour les messages entrants
 */
public class ThreadGetMessages extends Thread {
    private Server server;
    private Client client;

    /**
     * Initialise un thread (pour un client)
     * @param client
     */
    public ThreadGetMessages(Client client){
        this.client = client;
    }
    /**
     * Initialise un thread (pour un serveur)
     * @param client
     * @param server
     */
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
                // si la variable serveur n'est pas null, alors c'est le serveur qui reçoit un message d'un client
                if (server != null) {
                   serverResponse(messageCrypté);
                } else { // sinon c'est un client qui reçoit un message du serveur
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

        switch (messageDecrypté.charAt(0)) {
            case '@':
                sendPrivateMessage(messageDecrypté);    
                break;

            case '\\':
                respondToCommand(messageDecrypté.substring(1));
                break;
            default:
                server.diffuserMessage(messageDecrypté, client);
                break;
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
     * Gère la commande reçu par le serveur
     * @param commande 
     */
    private void respondToCommand(String commande){
        switch (commande) {
            case "all":
            String pseudo = "";
            for (Client c : server.clients) {
                pseudo += c.pseudo+" ";
            }
            client.out.println("pseudo" + " " +AES.crypteMessage(pseudo, client.aesKey));
            break;
        
            default:
                break;
        }
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
