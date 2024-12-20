import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;

/**
 * Classe du client
 */
public class Client {
    private String ipServer = "localhost";
    private int port = 4444;
    public Socket socket;
    public BufferedReader in; 
    public PrintWriter out;
    private Key rsaPublicKey;
    public Key aesKey;
    public ClientGUI gui;
    public String pseudo;
   
    /**
     * Initialise un Client qui établit la connexion avec le serveur
     */
    public Client(){
        gui = new ClientGUI(this);
        try {
            établirLaConnexion();
            sendPseudo();
            getRSAPublicKey();
            sendAESKey();
            boucleDeCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    /**
     * Initialise un Client à partir d'une socket 
     * (pour la liste de clients du serveur)
     *
     * @param socket socket de communication entre le client et le serveur
     */
    public Client(Socket socket){
        try {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    /**
    * Établit la connexion avec le serveur
    */
    private void établirLaConnexion() throws IOException{
        // Fait la demande de connexion
        socket = new Socket(ipServer, port);
        // Créer le flux d'entré et de sortie pour communiquer entre deux appareils
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Envoi le pseudo du client au serveur
     * @throws IOException
     */
    private void sendPseudo(){
        out.println(this.pseudo);
    }

    /**
     * Récupère la clé publique envoyée par le serveur
     * @throws IOException
     */
    private void getRSAPublicKey() throws IOException{
        System.out.println("Récupération de la clé publique RSA");
        String encodedKey = in.readLine();
        rsaPublicKey = RSA.stringToPublicKey(encodedKey);
    }

    /**
     * Méthode qui créer puis envoie au serveur la clé AES
     */
    private void sendAESKey(){
        System.out.println("Cryptage et envoie de la clé AES");

        // Générer une clé AES
        aesKey = AES.generateKey();
    
        // Envoyer la clé AES chiffrée au serveur
        out.println(RSA.crypteKey(aesKey, rsaPublicKey));    

    }

    /**
     * Permet de continuer la communication tant que le message envoyé n'est pas vide
     */
    public void boucleDeCommunication() throws IOException{
        System.out.println("Début de la communication");



        new ThreadGetMessages(this).start();
        new ThreadSendMessages(this).start();
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
