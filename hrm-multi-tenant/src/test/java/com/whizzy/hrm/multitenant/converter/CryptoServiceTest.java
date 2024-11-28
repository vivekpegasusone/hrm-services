package com.whizzy.hrm.multitenant.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoServiceTest {

    private final CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf", "sk$h43k&fd34ldsk", "AES/CBC/PKCS5PADDING");

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
        CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf", "sk$h43k&fd34ldsk1", "AES/CBC/PKCS5PADDING");
        String plainText = "connect@123";
        assertThrows(RuntimeException.class, () -> cryptoService.encrypt(plainText), "Must throw exception.");
    }

    @Test
    void throwErrorWhenSaltMoreThan16Char() {
        CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf1", "sk$h43k&fd34ldsk", "AES/CBC/PKCS5PADDING");
        String plainText = "connect@123";
        assertThrows(RuntimeException.class, () -> cryptoService.encrypt(plainText), "Must throw exception.");
    }

    @Test
    void throwErrorWhenAlgoIsNotCorrect() {
        CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf", "sk$h43k&fd34ldsk1", "AAA");
        String plainText = "connect@123";
        assertThrows(RuntimeException.class, () -> cryptoService.encrypt(plainText), "Must throw exception.");
    }

    @Test
    void throwErrorWhenDecryptWithIncorrectAlgo() {
        CryptoService cryptoService = new CryptoService("tyk@8hr34#9sdklf", "sk$h43k&fd34ldsk", "hfasklf");
        assertThrows(RuntimeException.class, () -> cryptoService.decrypt("bmAfIzB79pvOiuM76moiRg=="), "Must throw exception.");
    }
}