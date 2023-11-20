package com.dstorage.server;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;

public class Crypto {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String generateID() throws Exception {
        byte[] bytes = new byte[32];
        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return bytesToHex(bytes);
    }

    public static PathKey CASPathTransformFunc(String key) throws Exception {
        byte[] hashedBytes = sha1(key.getBytes(StandardCharsets.UTF_8));
        String hashStr = bytesToHex(hashedBytes);

        int blocksize = 5;
        int sliceLen = hashStr.length() / blocksize;
        String[] paths = new String[sliceLen];

        for (int i = 0; i < sliceLen; i++) {
            int from = i * blocksize;
            int to = (i * blocksize) + blocksize;
            paths[i] = hashStr.substring(from, to);
        }

        return new PathKey(String.join("/", paths), hashStr);
    }

    private static byte[] sha1(byte[] input) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
