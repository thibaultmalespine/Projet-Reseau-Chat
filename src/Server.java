import java.io.IOException;
import java.net.ServerSocket;
import java.util.Base64;

/**
 * Classe du serveur
 */
public class Server {

    Communication communication;
    ServerSocket serverSocket;

    public Server() {
        try {
            écoute();
            communication.créerFluxDeCommunication();
            sendKey();
            communication.out.println(Encodage.crypte("Salutation du server", communication.key));
            communication.boucleDeCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui créer puis envoie au client une Key
     */
    public void sendKey(){
        communication.key = Encodage.generateKey();
        communication.out.println(Base64.getEncoder().encodeToString(communication.key.getEncoded())); // convertit la clé de cryptage en base64 pour l'envoyer à travers le réseau
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

    public static void main(String[] args) {
        Server server = new Server();
    }

}




