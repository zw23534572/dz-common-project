package com.dazong.common.util.codec;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * RSA算法工具类，提供基于RSA算法的加签、验签、加密、解密
 * @author Sam
 * @version 1.0.0
 */
public class RSA {

    public static final String ALGORITHM_RSA = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static final int KEY_SIZE = 1024;

    public static final String PUBLIC_KEY = "public_key";
    public static final String PRIVATE_KEY = "private_key";

    private RSA(){
        
    }

    /**
     * 私钥签名
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {

        byte[] keyBytes = EncoderUtil.base64Decode(privateKey);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);

        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 公钥验签
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        byte[] keyBytes             = EncoderUtil.base64Decode(publicKey);
        X509EncodedKeySpec keySpec  = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory       = KeyFactory.getInstance(ALGORITHM_RSA);
        PublicKey pubKey            = keyFactory.generatePublic(keySpec);
        Signature signature         = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(EncoderUtil.base64Decode(sign));
    }

    /**
     * RSA私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes                     = EncoderUtil.base64Decode(key);
        PKCS8EncodedKeySpec pkcs8KeySpec    = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory               = KeyFactory.getInstance(ALGORITHM_RSA);
        Key privateKey                      = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher                       = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * RSA公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        //先解码
        byte[] keyBytes                 = EncoderUtil.base64Decode(key);


        X509EncodedKeySpec x509KeySpec  = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory           = KeyFactory.getInstance(ALGORITHM_RSA);
        Key publicKey                   = keyFactory.generatePublic(x509KeySpec);

        //
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * RSA公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = EncoderUtil.base64Decode(key);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * RSA私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)  throws Exception {

        byte[] keyBytes                     = EncoderUtil.base64Decode(key);
        PKCS8EncodedKeySpec pkcs8KeySpec    = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory               = KeyFactory.getInstance(ALGORITHM_RSA);
        Key privateKey                      = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher                       = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }



    public static Map<String,String> generateKeyPairForMap() {
        KeyPair keyPair = generateKeyPair();
        /** 得到公钥 */
        Key publicKey = keyPair.getPublic();

        /** 得到私钥 */
        Key privateKey = keyPair.getPrivate();

        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();

        String publicKeyBase64 = new BASE64Encoder().encode(publicKeyBytes);
        String privateKeyBase64 = new BASE64Encoder().encode(privateKeyBytes);

        return Commons.map(PUBLIC_KEY,publicKeyBase64,PRIVATE_KEY,privateKeyBase64);
    }

    public static StringKeyPair generateStringKeyPair() {
        Map<String,String> keyPairMap = generateKeyPairForMap();
        StringKeyPair keyPair = new StringKeyPair();
        keyPair.setPrivateKey(keyPairMap.get(PRIVATE_KEY));
        keyPair.setPublicKey(keyPairMap.get(PUBLIC_KEY));
        return keyPair;
    }

    /**
     * 创建长度为2048的RSA密钥对
     * @return
     */
    public static KeyPair generateKeyPair() {
        return generateKeyPair(KEY_SIZE,ALGORITHM_RSA);
    }

    /**
     * 创建一对密钥对
     * @param keyLen key的长度
     * @param algorithm 算法
     * @return
     */
    public static KeyPair generateKeyPair(int keyLen, String algorithm) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
            keyPairGen.initialize(keyLen);
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw Throwables.wrapThrow(e);
        }
    }
}
