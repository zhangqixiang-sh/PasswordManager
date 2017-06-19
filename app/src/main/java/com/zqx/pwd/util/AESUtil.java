package com.zqx.pwd.util;

import com.zqx.pwd.global.GlobalData;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ZhangQixiang on 2017/3/19.
 */

public class AESUtil {
    private final static String HEX = "0123456789ABCDEF";

    public static String encrypt(String seed, String cleartext) {
        byte[] keyBytes = getKeyBytes(seed);
        byte[] result = encrypt(keyBytes, cleartext.getBytes());
        return toHex(result);
    }

    private static byte[] getKeyBytes(String seed) {
        return GlobalData.getKeyBytes();
    }

    public static byte[] createKeyBytes(String seed) {
    /* Store these things on disk used to derive key later: */
        int iterationCount = 1000;
        int saltLength = 32; // bytes; should be the same size as the output (256 / 8 = 32)
        int keyLength = 256; // 256-bits for AES-256, 128-bits for AES-128, etc

        /* When first creating the key, obtain a salt with this: */
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        /* Use this to derive the key from the password: */
        KeySpec keySpec = new PBEKeySpec(seed.toCharArray(), salt, iterationCount, keyLength);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] encoded = keyFactory.generateSecret(keySpec).getEncoded();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String seed, String encrypted) {
        byte[] keyBytes = getKeyBytes(seed);
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(keyBytes, enc);
        return new String(result);
    }

    private static byte[] encrypt(byte[] keyBytes, byte[] clear) {
        return crypt(keyBytes, clear, Cipher.ENCRYPT_MODE);
    }

    private static byte[] decrypt(byte[] keyBytes, byte[] encrypted) {
        return crypt(keyBytes, encrypted, Cipher.DECRYPT_MODE);
    }

    private static byte[] crypt(byte[] raw, byte[] target, int cipherMode) {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher;
        byte[] crypt = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(
                    cipherMode, skeySpec,
                    new IvParameterSpec(new byte[cipher.getBlockSize()])
            );
            crypt = cipher.doFinal(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crypt;
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    private static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (byte aBuf : buf) {
            appendHex(result, aBuf);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
