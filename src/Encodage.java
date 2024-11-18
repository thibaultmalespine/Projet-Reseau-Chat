import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class Encodage {

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
     * Méthode pour générer une paire de clés RSA
     * @return la paire de clés
     */
    public static KeyPair generateRSAKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); 
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
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
        return Base64.getEncoder().encodeToString(result); // Conversion en base64 pour un affichage correct, car si on converti directement le message encodé en String on peut avoir des octets qui ne sont pas des caractères imprimables, ce qui corrompt le message
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

    /**
     * Méthode pour crypter une clé AES avec une clé publique RSA
     * @param aesKey clé AES à crypter
     * @param publicKey clé publique RSA
     * @return la clé AES cryptée en base64
     */
    public static String crypteKey(Key aesKey, Key publicKey) {
        byte[] encryptedKey = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedKey = cipher.doFinal(aesKey.getEncoded());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    /**
     * Méthode pour décrypter une clé AES avec une clé privée RSA
     * @param encryptedKey clé AES cryptée (en base64)
     * @param privateKey clé privée RSA
     * @return la clé AES décryptée
     */
    public static Key decrypteKey(String encryptedKey, Key privateKey) {
        byte[] decryptedKey = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] data = Base64.getDecoder().decode(encryptedKey);
            decryptedKey = cipher.doFinal(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new SecretKeySpec(decryptedKey, "AES");
    }

  

    public static void main(String[] args){
        Key key = generateKey();
        String message = "salut à tous";
        String messageCrypté = crypteMessage(message, key);
        System.out.println("Message crypté (Base64) : " + messageCrypté);
        System.out.println("Message décrypté : " + decrypteMessage(messageCrypté, key));
    }
}
