import java.io.IOException;

public class ThreadGetMessage extends Thread {
    Communication communication;

    public ThreadGetMessage(Communication communication){
        this.communication = communication;
    }

    /**
     * Code exécuté par le Thread
     */
    @Override
    public void run(){
        String message;
        try {
            while (!(message = communication.in.readLine()).equals("bye")) {           
                System.out.println("message crypté : " + message);
                System.out.println("message décrypté : "+AES.decrypteMessage(message, communication.aesKey));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
