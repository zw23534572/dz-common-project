package com.dazong.common.security;


import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;


public class DesEncrypter extends Des {


    public static String cryptString(String str, String key) throws Exception {

        DesEncrypter encrypter = new DesEncrypter(key);
        return encrypter.encrypt(str);

    }


    public DesEncrypter(String passPhrase) throws Exception {
        // Create the key

        KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance(FACTORY_KEY).generateSecret(keySpec);
        cipher = Cipher.getInstance(key.getAlgorithm());


        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        // Create the ciphers
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);


    }


    public String encrypt(String str) throws Exception {

        // Encode the string into bytes using utf-8
        byte[] utf8 = str.getBytes();

        // Encrypt
        byte[] enc = encrypt(utf8);

        return Base32.encode(enc);

    }

    public byte[] encrypt(byte[] utf8) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(utf8);
    }


    public static void main(String[] args) throws Exception {
        String key = "c2#sUjAKq3dGP7%Zjz-ydBPUvKoe_qI8";
        System.out.println("key = " + key);

        String encrypted = DesEncrypter.cryptString("hello world xxss", key);
        System.out.println("encrypted = " + encrypted);
        String plain = DesDecrypter.decryptString(encrypted, key);
        System.out.println("plain = " + plain);


    }


}