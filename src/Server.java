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

    ArrayList<ThreadGetMessages> threadsGetMessages = new ArrayList<>();

    public Server() {
        
        rsaKeyPair = RSA.generateKey();
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server en écoute sur le port 4444");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté");
                Client client = new Client(clientSocket);
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
        threadsGetMessages.add(threadGetMessages);
    }

    public void diffuserMessage(String message, Client c) {
        for (Client client : clients) {
            if (client != c){
                client.out.println(AES.crypteMessage(message, client.aesKey));
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}




