import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class PublicAdresse {
    String adresse_public;
    public PublicAdresse(){
        URL url;
        try {
            url = new URL("https://ifconfig.me");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null;) {
                adresse_public = line;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PublicAdresse pa = new PublicAdresse();
        System.out.println(pa.adresse_public);
    }
}
