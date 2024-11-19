import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;

/**
 * Classe du serveur
 */
public class Server {

    Communication communication;
    ServerSocket serverSocket;
    KeyPair rsaKeyPair;

    public Server() {
        try {
            Socket clientSocket = écoute();
            communication = créerCommunication(clientSocket);
            rsaKeyPair = RSA.generateKey();
            sendRSAKey();
            getAESKey();
            communication.out.println(AES.crypteMessage("Salutation du server", communication.aesKey));
            communication.boucleDeCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Créer la socket sur laquelle le serveur attend une connexion, sur le port 4444 
     */
    public Socket écoute() throws IOException {

        serverSocket = new ServerSocket(4444);
        System.out.println("Server en écoute sur le port 4444");

        return serverSocket.accept();
    }
    
    /**
     * Créer une nouvelle communication avec le client 
     * @param clientSocket Socket envoyé par le client
     * @return un nouveau objet communication
     * @throws IOException
     */
    private Communication créerCommunication(Socket clientSocket) throws IOException{
        System.out.println("Client connecté");
        return new Communication(clientSocket);
    }

    /**
     * Méthode qui créer envoie au client la clé RSA publique 
     */
    public void sendRSAKey(){
        System.out.println("Envoie de la clé publique RSA");
        communication.out.println(RSA.publicKeyToString(rsaKeyPair.getPublic())); 
    }

    /**
     * Récupère la clé AES envoyé par le serveur
     */
    public void getAESKey() throws IOException{
        System.out.println("Récupération de la clé AES");
        String RSAKeyBase64 = communication.in.readLine();
        communication.aesKey = RSA.decrypteKey(RSAKeyBase64, rsaKeyPair.getPrivate());
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}




