import java.io.IOException;
import java.net.Socket;
import java.security.Key;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

/**
 * Classe du client
 */
public class Client {
    Communication communication;
    private String ipServer = "127.0.0.1";
    private int port = 4444;

   
    public Client(){
        try {
            établirLaConnexion();
            communication.créerFluxDeCommunication();
            getKey();
            communication.boucleDeCommunication();
        } catch (IOException e) {
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
     * Récupère la clé envoyer par le serveur
     * @throws IOException
     */
    public void getKey() throws IOException{

        String keyBase64 = communication.in.readLine();
        
        // Convertir la chaîne Base64 en un tableau d'octets
        byte[] decodedKey = Base64.getDecoder().decode(keyBase64);
        
        // Reconstruire la clé à partir du tableau d'octets
        Key keyCommunication = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        
        this.communication.key = keyCommunication;

    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
