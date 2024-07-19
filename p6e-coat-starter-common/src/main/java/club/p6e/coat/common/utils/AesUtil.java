package club.p6e.coat.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class AesUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(
                AesUtil.generateKey().getEncoded().length
        );
    }

    public static Key generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static String keyToString(Key secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static Key stringToKey(String encodedKeyString) {
        return new SecretKeySpec(Base64.getDecoder().decode(encodedKeyString), "AES");
    }

    public static String encryption(String plainText, Key secretKey) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        final byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryption(String encryptedText, Key secretKey) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        final byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        final byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

}

