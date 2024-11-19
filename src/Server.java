import java.io.IOException;
import java.net.ServerSocket;
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
            écoute();
            rsaKeyPair = RSA.generateKey();
            communication.créerFluxDeCommunication();
            sendRSAKey();
            getAESKey();
            communication.out.println(AES.crypteMessage("Salutation du server", communication.aesKey));
            communication.boucleDeCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /**
     * Créer la socket sur laquelle il attend une connexion, sur le port 4444 
     */
    public void écoute() throws IOException {

        
        serverSocket = new ServerSocket(4444);
        System.out.println("Server en écoute sur le port 4444");

        communication = new Communication(serverSocket.accept());
        System.out.println("Client connecté");
    }

    /**
     * méthode qui créer puis envoie au client la clé RSA publique 
     */
    public void sendRSAKey(){
        System.out.println("Envoie de la clé publique RSA");
        communication.out.println(RSA.publicKeyToString(rsaKeyPair.getPublic())); // envoie de la clé public
    }

    /**
     * Récupère la Key AES envoyé par le serveur
     */
    public void getAESKey() throws IOException{
        System.out.println("Récupération de la clé AES");
        String RSAKeyBase64 = communication.in.readLine();
        
        // Reconstruire la clé AES à partir du tableau d'octets décrypté
        communication.aesKey = RSA.decrypteKey(RSAKeyBase64, rsaKeyPair.getPrivate());
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}




