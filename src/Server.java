// Socket sur lequel le serveur attends les connexions

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe du serveur
 */
public class Server {

    Socket clientSocket;
    ServerSocket serverSocket;
    BufferedReader in;
    PrintWriter out;


    public Server() {
        try {
            écoute();
            créerFluxDeCommunication();
            boucleDeCommunication();
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

        clientSocket = serverSocket.accept();
        System.out.println("Client connecté");
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
        String clientInput = "";
        out.println("Salutation du server");
        while (! userInput.equals("end")) {
            if ((clientInput = in.readLine()).equals("end")){
                break;
            }
            System.out.println("echo " + clientInput);
            userInput = stdIn.readLine();
            out.println(userInput);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}




