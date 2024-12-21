package com.whizzy.hrm.multitenant.util;

import com.whizzy.hrm.multitenant.crypto.CryptoService;

public class EncryptionUtil {

    public static void main(String[] args) {
        if(args.length != 4) {
            throw new RuntimeException("Please provide salt, secret key and text and action (encrypt/decrypt).");
        }

        String salt = args[0];
        String secret = args[1];
        String text = args[2];
        String action = args[3];

        CryptoService cryptoService = new CryptoService(salt,secret);

        if (action.equalsIgnoreCase("ENCRYPT")) {
            System.out.println(cryptoService.encrypt(text));
        } else {
            System.out.println(cryptoService.decrypt(text));
        }
    }

}
