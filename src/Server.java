// Socket sur lequel le serveur attends les connexions

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    Socket clientSocket;
    ServerSocket serverSocket;

    public Server() {
        Ecoute();
    }
    
    public void Ecoute() {

        try{
            serverSocket = new ServerSocket(4444);
        }
        catch(IOException e){
            System.out.println("Could not listen on port 4444");
            System.exit(-1);
        }

        try{
            clientSocket = serverSocket.accept();
        }
        catch(IOException e){
            System.out.println("Accept failed on port 4444");
            System.exit(-1);
        }

        System.out.println("Client connecté");

}

}




