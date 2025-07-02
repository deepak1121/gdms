package com.aiims.gdms.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESGCMDecryption {

    private static final String BASE64_KEY = "oqrAdo2B1A3xiGJuvUR2L7eZaS3qkAB7T8LNZ0xQfgI=";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH_BIT = 128;

    public static String decrypt(String base64CipherText) throws Exception {
        
        byte[] combined = Base64.getDecoder().decode(base64CipherText);

        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(combined, 0, iv, 0, IV_LENGTH);

        byte[] cipherText = new byte[combined.length - IV_LENGTH];
        System.arraycopy(combined, IV_LENGTH, cipherText, 0, cipherText.length);

        byte[] keyBytes = Base64.getDecoder().decode(BASE64_KEY);
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decrypted = cipher.doFinal(cipherText);

        return new String(decrypted);
    }
}

