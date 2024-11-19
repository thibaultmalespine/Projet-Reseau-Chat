import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;

/**
 * Classe qui gère les communications entre deux appareils à partir d'une socket
 */
public class Communication {
    BufferedReader in;
    PrintWriter out;
    Socket socket;
    Key aesKey;
    
    private String patternDeFin = "bye";
    
    public Communication(Socket socket) throws IOException {
        this.socket = socket;
        créerFluxDeCommunication();
    }
    
    /**
     * Créer le flux d'entré et de sortie pour communiquer entre deux appareils
     */
    private void créerFluxDeCommunication() throws IOException{
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Permet de continuer la communication tant que le message envoyé n'est pas vide
     */
    public void boucleDeCommunication() throws IOException{
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        String clientInput = "";
        while (! userInput.equals(patternDeFin)) {
            if ((clientInput = in.readLine()).equals(patternDeFin)){
                break;
            }
            System.out.println("début communication");
            System.out.println("message crypté : " + clientInput);
            System.out.println("message décrypté : "+AES.decrypteMessage(clientInput, aesKey));
            userInput = stdIn.readLine();
            out.println(AES.crypteMessage(userInput, aesKey));
        }
    }
}
