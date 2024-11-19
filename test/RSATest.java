import static org.junit.Assert.*;

import java.security.Key;
import java.security.KeyPair;

import org.junit.Before;
import org.junit.Test;

public class RSATest {
    KeyPair rsaKeys = null;
    Key aesKey = null;

   @Before
   public void setUp(){
       rsaKeys = RSA.generateKey();
       aesKey = AES.generateKey();
    }

    @Test
    public void testGenerateKey() {
        assertNotNull(rsaKeys);
        assertNotNull(rsaKeys.getPublic());
        assertNotNull(rsaKeys.getPrivate());
        assertEquals( "RSA", rsaKeys.getPublic().getAlgorithm());
        assertEquals( "RSA", rsaKeys.getPrivate().getAlgorithm());
    }
    
    @Test
    public void testCrypteAndDecrypteKey() {
        String encryptedKey = RSA.crypteKey(aesKey, rsaKeys.getPublic());
        assertNotNull(encryptedKey);
        assertFalse(encryptedKey.isEmpty());

        Key decryptedKey = RSA.decrypteKey(encryptedKey, rsaKeys.getPrivate());
        assertEquals(aesKey, decryptedKey);

    }

    @Test
    public void testPublicKeyToStringAndStringToPublicKey() {
        String encodedKey = RSA.publicKeyToString(rsaKeys.getPublic());
        assertNotNull(encodedKey);
        assertFalse(encodedKey.isEmpty());

        Key decodedKey = RSA.stringToPublicKey(encodedKey);
        assertEquals(rsaKeys.getPublic(), decodedKey);
    }


}
