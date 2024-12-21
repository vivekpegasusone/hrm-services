package com.whizzy.hrm.multitenant.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoServiceTest {

    private final CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf", "sk$h43k&fd34ldsk");

    @Test
    void encrypt() {
        String plainText = "connect@123";
        String cipherText = cryptoService.encrypt(plainText);
        assertNotNull(cipherText, "Encrypted text can not be null.");
    }

    @Test
    void decrypt() {
        String plainText = "connect@123";
        String cipherText = cryptoService.encrypt(plainText);
        String text = cryptoService.decrypt(cipherText);
        assertNotNull(text, "Decrypted text can not be null.");
        assertEquals(text, plainText, "Decrypted text must match with un encrypted text");
    }

    @Test
    void throwErrorWhenSecretMoreThan16Char() {
        CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf", "sk$h43k&fd34ldsk1");
        String plainText = "connect@123";
        assertThrows(RuntimeException.class, () -> cryptoService.encrypt(plainText), "Must throw exception.");
    }

    @Test
    void throwErrorWhenSaltMoreThan16Char() {
        CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf1", "sk$h43k&fd34ldsk");
        String plainText = "connect@123";
        assertThrows(RuntimeException.class, () -> cryptoService.encrypt(plainText), "Must throw exception.");
    }
}