import java.io.IOException;
import java.net.Socket;

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

    public static void main(String[] args) {
        Client client = new Client();
    }
}
