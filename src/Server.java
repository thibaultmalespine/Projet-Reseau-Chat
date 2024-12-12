import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.ArrayList;

/**
 * Classe du serveur
 */
public class Server {

    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;
    KeyPair rsaKeyPair;

    /**
     * Initialise le Server pour qu'il écoute indéfiniment les demandes de connexion,
     * puis créer un nouveau thread pour chaque nouveau client
     */
    public Server() {
        
        rsaKeyPair = RSA.generateKey();
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server en écoute sur le port 4444");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté");
                Client client = new Client(clientSocket);
                getPseudo(client);
                sendRSAKey(client);
                getAESKey(client);
                clients.add(client);

                getMessages(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère le pseudo du client
     * 
     */
    private void getPseudo(Client client) throws IOException{
        String pseudo = client.in.readLine();
        client.pseudo = pseudo;
    }

    /**
     * Méthode qui créer envoie au client la clé RSA publique 
     */
    public void sendRSAKey(Client client){
        System.out.println("Envoie de la clé publique RSA");
        client.out.println(RSA.publicKeyToString(rsaKeyPair.getPublic())); 
    }

    /**
     * Récupère la clé AES envoyé par le serveur
     */
    public void getAESKey(Client client) throws IOException{
        System.out.println("Récupération de la clé AES");
        String RSAKeyBase64 = client.in.readLine();
        client.aesKey = RSA.decrypteKey(RSAKeyBase64, rsaKeyPair.getPrivate());
    }

    /**
     * Méthode qui créer les threads permettant de recevoir les messages des clients
     */
    private void getMessages(Client client){
        ThreadGetMessages threadGetMessages = new ThreadGetMessages(client, this);
        threadGetMessages.start();
    }

    /**
     * Methode pour renvoyer le message reçu par le serveur vers tous les autres clients
     * @param message
     * @param c
     */
    public void diffuserMessage(String message, Client c) {
        for (Client client : clients) {
            if (client != c){
                // on envoie le pseudo et le message associé
                client.out.println(c.pseudo + " " +AES.crypteMessage(message, client.aesKey));
            }
        }
       
    }

    /**
     * Renvoie le message reçu seulement au client destinataire
     * @param message
     * @param pseudo
     */
    public void messagePrivé(String message,Client c, String pseudo){
        System.out.println(pseudo);
        for (Client client : clients) {
            if(client.pseudo.equals(pseudo)){
                // on envoie le pseudo et le message associé
                client.out.println(c.pseudo + " " +AES.crypteMessage(message, client.aesKey));
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}




