package com.yyy.wrsf.utils;

import android.util.Base64;

import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil {

    public static String getCode(String string, String pwd) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(
                    string.getBytes(), "AES"), new IvParameterSpec(string.getBytes()));
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = pwd.getBytes();
            int length = dataBytes.length;
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            byte[] encrypted = cipher.doFinal(plaintext);
            return URLEncoder.encode(Base64.encodeToString(encrypted, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
