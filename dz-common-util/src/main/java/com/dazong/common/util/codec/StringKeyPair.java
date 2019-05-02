package com.dazong.common.util.codec;

import java.io.Serializable;

/**
 * RSA密钥对，以字符串返回便于程序进行存储
 * @author Sam
 * @version 1.0.0
 */
public class StringKeyPair implements Serializable {

    private String privateKey;

    private String publicKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
