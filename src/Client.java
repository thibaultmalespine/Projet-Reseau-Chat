import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe du client
 */
public class Client {
    Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String ipServer;
    private int port = 4444;

   
    public Client(){
        try {
            établirLaConnexion();
            créerFluxDeCommunication();
            boucleDeCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }     
    }

     /**
     * Fait la demande de connexion
     */
    public void établirLaConnexion() throws UnknownHostException, IOException{
        ipServer = PublicAdresse.getAdresse();
        this.clientSocket = new Socket(ipServer, port);
    }

     /**
     * Créer le flux d'entré et de sortie pour communiquer entre deux appareils
     */
    public void créerFluxDeCommunication() throws IOException{
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

     /**
     * Permet de continuer la communication tant que le message envoyé n'est pas vide
     */
    public void boucleDeCommunication() throws IOException{
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        String serverInput = "";
        while (! userInput.equals("end")) {
            if ((serverInput = in.readLine()).equals("end")){
                break;
            }
            System.out.println("echo " + serverInput);
            userInput = stdIn.readLine();
            out.println(userInput);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
