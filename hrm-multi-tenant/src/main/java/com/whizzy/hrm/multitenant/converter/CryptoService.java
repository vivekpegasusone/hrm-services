package com.whizzy.hrm.multitenant.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class CryptoService { // not needed

    private final String salt;
    private final String secret;
    private final String algorithm;

    public CryptoService(@Value("${encryption.salt}") String salt,
                         @Value("${encryption.secret}") String secret,
                         @Value("${encryption.algorithm}") String algorithm) {
        this.salt = salt;
        this.secret = secret;
        this.algorithm = algorithm;
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            IvParameterSpec iv = new IvParameterSpec(salt.getBytes());
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            IvParameterSpec iv = new IvParameterSpec(salt.getBytes());
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] text = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(text);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
