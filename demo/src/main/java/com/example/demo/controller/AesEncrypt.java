package com.example.demo.controller;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncrypt {
    /**
     * @param strKey
     * @param strIn
     * @return
     * @throws Exception
     */
    public static String encrypt(String strKey, String strIn, byte[] vi) {
        Cipher cipher = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(strKey.getBytes(), "AES"),
                    new IvParameterSpec(vi)
            );
            byte[] encrypted = cipher.doFinal(strIn.getBytes());
            String encryptedString = Base64.encodeBase64String(encrypted);
            return encryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * @param strKey
     * @param strIn
     * @return
     */
    public static String decrypt(String strKey, String strIn, byte[] vi) {
        try {
            byte[] encrypted1 = Base64.decodeBase64(strIn);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(strKey.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(vi);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } }
