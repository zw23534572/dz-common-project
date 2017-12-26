package com.dazong.common.security;

import javax.crypto.Cipher;

/**
 * User: yangsiyong@360buy.com
 * Date: 2010-5-21
 * Time: 9:07:43
 */
public class Des {

    protected final String FACTORY_KEY = "PBEWithMD5AndDES";
    protected Cipher cipher;

    // 8-byte Salt
    static byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };

    // Iteration count
    static int iterationCount = 19;


}
