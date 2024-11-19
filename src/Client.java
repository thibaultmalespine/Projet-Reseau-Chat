import java.io.IOException;
import java.net.Socket;
import java.security.Key;

/**
 * Classe du client
 */
public class Client {
    Communication communication;
    private String ipServer = "127.0.0.1";
    private int port = 4444;
    private Key rsaPublicKey;
   
    public Client(){
        try {
            communication = établirLaConnexion();
            getRSAPublicKey();
            sendAESKey();
            communication.boucleDeCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    /**
    * Fait la demande de connexion
    */
    public Communication établirLaConnexion() throws IOException{
        return new Communication(new Socket(ipServer, port));
    }

    /**
     * Récupère la clé publique envoyée par le serveur
     * @throws IOException
     */
    public void getRSAPublicKey() throws IOException{
        System.out.println("Récupération de la clé publique RSA");
        String encodedKey = communication.in.readLine();
        rsaPublicKey = RSA.stringToPublicKey(encodedKey);
    }

    
    /**
     * Méthode qui créer puis envoie au serveur la clé AES
     */
    public void sendAESKey(){
        System.out.println("Cryptage et envoie de la clé AES");

        // Générer une clé AES
        communication.aesKey = AES.generateKey();
    
        // Envoyer la clé AES chiffrée au serveur
        communication.out.println(RSA.crypteKey(communication.aesKey, rsaPublicKey));    

    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
