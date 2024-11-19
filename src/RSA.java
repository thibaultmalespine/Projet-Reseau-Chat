import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class RSA {
    /**
     * Méthode pour générer une paire de clés RSA
     * @return la paire de clés
     */
    public static KeyPair generateKey() {
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

    /**
     * Convertis une clé publique en String codé en Base64 
     * @param publicKey Clé à encoder
     * @return String en Base64
     */
    public static String publicKeyToString(Key publicKey){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * Décode une clé publique encodée en Base64 
     * @param encodedKey La clé au format Base64
     * @return La clé au format Key
     */
    public static Key stringToPublicKey(String encodedKey){
        Key publicKey = null;

        // Décodage de la clé Base64 en bytes
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        try {
            // Reconstruction de la clé publique
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return publicKey;
    }
}
