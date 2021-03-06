package ro.msg.edu.jbugs.shared.business.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class Encryptor {

    private static final String KEY = "Bar12345Bar12345";

    /**
     * Encrypts the given parameter.
     * @param toEncrypt the parameter to be encrypted
     * @return the encrypted parameter
     */
    public static String encrypt(String toEncrypt) {
        String encryptedString = null;

        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
            encryptedString = new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
}
