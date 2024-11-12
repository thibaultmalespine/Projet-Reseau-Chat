import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Encodage {

    /**
     * Permet de générer une clé de cryptage
     * @return une clé de cryptage
     */
    public static Key generateKey() {
        KeyGenerator kg;
        Key key = null;
        try {
            kg = KeyGenerator.getInstance("AES");
            key = kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * Méthode pour crypter un message avec l'algorithme AES
     * @param message message à crypter
     * @param key clé de cryptage
     * @return le message crypté en base64
     */
    public static String crypte(String message, Key key){
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Base64.getEncoder().encodeToString(result); // Conversion en base64 pour un affichage correct, car si on converti directement le message encodé en String on peut avoir des octets qui ne sont pas des caractères imprimables, ce qui corrompt le message
    }

    /**
     * Méthode pour décrypter un message avec l'algorithme AES
     * @param message message à décrypter (en base64)
     * @param key clé de cryptage
     * @return le message décrypté
     */
    public static String decrypte(String message, Key key){
        byte[] original = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] data = Base64.getDecoder().decode(message); // Décodage du message en base64
            original = cipher.doFinal(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new String(original);
    }

    public static void main(String[] args){
        Key key = generateKey();
        String message = "salut à tous";
        String messageCrypté = crypte(message, key);
        System.out.println("Message crypté (Base64) : " + messageCrypté);
        System.out.println("Message décrypté : " + decrypte(messageCrypté, key));
    }
}
