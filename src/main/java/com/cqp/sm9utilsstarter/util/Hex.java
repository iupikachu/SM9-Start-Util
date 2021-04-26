package com.cqp.sm9utilsstarter.util;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName Hex.java
 * @Description TODO
 * @createTime 2021年03月30日 09:37:00
 */
public class Hex {
    public Hex() {
    }

    public static String encodeToString(byte[] data) {
        return encodeToString(data, false);
    }

    public static String encodeToString(byte[] data, boolean isUpperCase) {
        char[] digital = "0123456789abcdef".toCharArray();
        if (isUpperCase) {
            digital = "0123456789ABCDEF".toCharArray();
        }

        StringBuffer sb = new StringBuffer("");

        for(int i = 0; i < data.length; ++i) {
            int bit = (data[i] & 240) >> 4;
            sb.append(digital[bit]);
            bit = data[i] & 15;
            sb.append(digital[bit]);
        }

        return sb.toString();
    }

    public static byte[] encode(byte[] data) {
        return encodeToString(data).getBytes();
    }

    public static byte[] encode(byte[] data, boolean isUpperCase) {
        return encodeToString(data, isUpperCase).getBytes();
    }

    public static byte[] decode(String hex) {
        String digital = "0123456789abcdef";
        char[] hex2char = hex.toLowerCase().toCharArray();
        byte[] bytes = new byte[hex.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            int temp = digital.indexOf(hex2char[2 * i]) << 4;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte)(temp & 255);
        }

        return bytes;
    }
}