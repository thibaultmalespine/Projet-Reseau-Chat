import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Cette classe est complètement inutile en fait
 */
public class PublicAdresse {

    public static String getAdresse() {
        URL url;
        String adresse_public = "";
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
        return adresse_public;
    }
    public static void main(String[] args){
        System.out.println(PublicAdresse.getAdresse());
    }
}

