package com.dazong.common.security;


import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

/**
 */

public class DesDecrypter extends Des{

    /**
     */
    public static String decryptString(String str, String key) throws Exception {
        DesDecrypter encrypter = new DesDecrypter(key);
        return encrypter.decrypt(str);
    }

    DesDecrypter(String passPhrase) throws Exception {
        // Create the key

        KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance(FACTORY_KEY).generateSecret(keySpec);
        cipher = Cipher.getInstance(key.getAlgorithm());
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        // Create the ciphers
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

    }

 

    public String decrypt(String str) throws Exception {
        byte[] dec = Base32.decode(str);
        // Decrypt
        byte[] utf8 = decrypt(dec);

        // Decode using utf-8
        return new String(utf8);

    }

    public byte[] decrypt(byte[] dec) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(dec);
    }


    public static void main(String[] args) throws Exception {
        String key = "ck|jhtr%oxo)ajlos\\qz=i_g,ge*g|j[";
        System.out.println("key = " + key);

        String encrypted = DesEncrypter.cryptString("hello world xxss", key);
        System.out.println("encrypted = " + encrypted);
        String plain = DesDecrypter.decryptString(encrypted, key);
        System.out.println("plain = " + plain);


    }


}