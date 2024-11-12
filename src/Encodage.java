import java.security.Key;
import java.security.NoSuchAlgorithmException;

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
            key = kg.generateKey() ;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * Méthode pour crypter un message avec l'algorithme AES
     * @param message message à crypter
     * @param key clé de cryptage
     * @return le message crypté
     */
    public static String crypte(String message, Key key){
        byte result[] = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new String(result);
    }

    /**
     * Méthode pour décrypter un message avec l'algorithme AES
     * @param message message à décrypter
     * @param key clé de cryptage
     * @return le message décrypté
     */
    public static String decrypte(String message, Key key){
        byte[]original = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            original = cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new String(original);
    }
    public static void main(String[] args){
        Key key = generateKey();
        String message_crypté = crypte("salut à tous ", key);
        System.out.println(message_crypté);
        System.out.println(decrypte(message_crypté, key));
    }
}
