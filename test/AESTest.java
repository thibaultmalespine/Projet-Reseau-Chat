import static org.junit.Assert.*;

import java.security.Key;

import org.junit.Before;
import org.junit.Test;

public class AESTest {
    Key aesKey = null;

    @Before
    public void setUp(){
        aesKey = AES.generateKey();
    }

    @Test
    public void testGenerateKey() {
        assertNotNull(aesKey);
        assertEquals( "AES", aesKey.getAlgorithm());
    }

    @Test
    public void testCrypteAndDecrypteMessage() {
        String cryptedMessage = AES.crypteMessage("message de test", aesKey);
        assertNotNull(cryptedMessage);     
        assertFalse(cryptedMessage.isEmpty());
        
        String decrytedMessage = AES.decrypteMessage(cryptedMessage, aesKey);
        assertEquals("message de test", decrytedMessage);
    }
   
}
