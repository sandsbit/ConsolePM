package me.nikitaserba.consolepm.utils.encryptors;

import me.nikitaserba.consolepm.utils.exceptions.EncryptionException;
import me.nikitaserba.consolepm.utils.PasswordEncryptor;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class AESGeneralPasswordEncryptor implements PasswordEncryptor {

    private final String SALT = "ILikeToEatFood666";
    private final String SECRET_KEY_FACTORY = "PBKDF2WithHmacSHA256";
    private final String ALGORITHM = "AES";
    private final int ITERATIONS = 65536;
    private final int KEY_LENGTH = 256;

    private final SecretKey secretKey;

    public AESGeneralPasswordEncryptor(String password) throws EncryptionException {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), ITERATIONS, KEY_LENGTH);
            secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new EncryptionException(e);
        }
    }

    @Override
    public byte[] encrypt(String password) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new EncryptionException(e);
        }
    }

    @Override
    public String decrypt(byte[] encryptedPassword) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(encryptedPassword), StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new EncryptionException(e);
        }
    }
}
