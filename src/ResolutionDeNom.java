import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class ResolutionDeNom{
    public static String getNom() {
        InetAddress address;
        String ip = "";
        try{
            address = InetAddress.getByName("localhost");
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ip;
        
    }

    public static void main(String[] args) {
        System.out.println(ResolutionDeNom.getNom());

        URL url;
        try {
            url = new URL("http://ifconfig.me/");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}