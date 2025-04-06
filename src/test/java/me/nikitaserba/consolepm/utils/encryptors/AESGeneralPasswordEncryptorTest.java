package me.nikitaserba.consolepm.utils.encryptors;

import me.nikitaserba.consolepm.utils.exceptions.EncryptionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AESGeneralPasswordEncryptorTest {
    @Test
    void encryptDecrypt() throws EncryptionException {
        final String TEST_PASSWORD = "testPassword";
        AESGeneralPasswordEncryptor encryptor =  new AESGeneralPasswordEncryptor(TEST_PASSWORD);

        String testString = "My secret message";
        byte[] encryptedMessage = encryptor.encrypt(testString);
        String testStringDecrypted = encryptor.decrypt(encryptedMessage);

        assertEquals(testString, testStringDecrypted);
    }

    @Test
    void invalidKey() throws EncryptionException {
        final String TEST_PASSWORD = "testPassword";
        final String TEST_PASSWORD2 = "testPassword2";
        String testString = "My secret message";

        AESGeneralPasswordEncryptor encryptor =  new AESGeneralPasswordEncryptor(TEST_PASSWORD);
        byte[] encryptedMessage = encryptor.encrypt(testString);
        AESGeneralPasswordEncryptor encryptor2 = new AESGeneralPasswordEncryptor(TEST_PASSWORD2);
        assertThrows(EncryptionException.class, () -> encryptor2.decrypt(encryptedMessage),
                "Using invalid key must generate exception!");
    }
}