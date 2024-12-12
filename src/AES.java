import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * Classe qui gère les opérations liés au cryptage AES
 */
public class AES {

    /**
     * Permet de générer une clé de cryptage
     * @return une clé de cryptage
     */
    public static Key generateKey() {
        Key key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            key = keyGenerator.generateKey();
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
    public static String crypteMessage(String message, Key key){
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Base64.getEncoder().encodeToString(result); 
    }

    /**
     * Méthode pour décrypter un message avec l'algorithme AES
     * @param message message à décrypter (en base64)
     * @param key clé de cryptage
     * @return le message décrypté
     */
    public static String decrypteMessage(String message, Key key){
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

}
