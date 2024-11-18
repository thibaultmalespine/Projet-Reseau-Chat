import java.io.IOException;
import java.net.Socket;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Classe du client
 */
public class Client {
    Communication communication;
    private String ipServer = "127.0.0.1";
    private int port = 4444;
    private Key RSAPublicKey;
   
    public Client(){
        try {
            établirLaConnexion();
            communication.créerFluxDeCommunication();
            getRSAPublicKey();
            sendAESKey();
            communication.boucleDeCommunication();
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

     /**
     * Fait la demande de connexion
     */
    public void établirLaConnexion() throws IOException{
        this.communication = new Communication(new Socket(ipServer, port));
    }

    /**
     * Récupère la clé publique envoyée par le serveur
     * @throws IOException
     */
    public void getRSAPublicKey() throws Exception{
        System.out.println("Récupération de la clé publique RSA");
        String keyBase64 = communication.in.readLine();
        
        // Décodage de la clé Base64 en bytes
        byte[] decodedKey = Base64.getDecoder().decode(keyBase64);

        // Reconstruction de la clé publique
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        RSAPublicKey = keyFactory.generatePublic(keySpec);
        
    }

    
    /**
     * Méthode qui créer puis envoie au serveur la Key AES
     */
    public void sendAESKey(){
        System.out.println("Envoie de la clé AES");

        // Générer une clé AES
        communication.AESkey = Encodage.generateKey();
    
        // Envoyer la clé AES chiffrée au serveur
        communication.out.println( Encodage.crypteKey(communication.AESkey, RSAPublicKey));    

    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
