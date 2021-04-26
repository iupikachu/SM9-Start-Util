package com.cqp.sm9utilsstarter.util;


import com.cqp.sm9utilsstarter.SM3.SM3Digestt;
import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.crypto.Digest;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9Utils.java
 * @Description TODO
 * @createTime 2021年03月30日 09:45:00
 */
public class SM9Utils {
    public static final String NEW_LINE = "\n";

    public SM9Utils() {
    }

    public static BigInteger genRandom(SecureRandom random, BigInteger max) {
        BigInteger k;
        do {
            k = new BigInteger(max.bitLength(), random);
        } while(k.compareTo(BigInteger.ZERO) <= 0 || k.compareTo(max) >= 0);

        return k;
    }

    public static boolean isBetween(BigInteger a, BigInteger max) {
        return a.compareTo(BigInteger.ZERO) > 0 && a.compareTo(max) < 0;
    }

    /**
     * H1(IDA||hid, N);
     */
    public static BigInteger H1(String id, byte hid, BigInteger N) {
        byte[] bID = id.getBytes();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(0x01);
        bos.write(bID, 0, bID.length);
        bos.write(hid);
        return H(bos.toByteArray(), N);
    }

    public static BigInteger H2(byte[] data, Element w, BigInteger N) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(2);
        bos.write(data, 0, data.length);
        byte[] temp = GTFiniteElementToByte(w);
        bos.write(temp, 0, temp.length);
        return H(bos.toByteArray(), N);
    }

    public static BigInteger H(byte[] Z, BigInteger N) {
        double log2n = Math.log(N.doubleValue()) / Math.log(2.0D);
        int hlen = (int)Math.ceil(5.0D * log2n / 32.0D);
        byte[] hashValue = KDF(Z, hlen);
        BigInteger ha = new BigInteger(1, hashValue);
        return ha.mod(N.subtract(BigInteger.ONE)).add(BigInteger.ONE);
    }

    public static byte[] Hash(byte[] data) {
        Digest digest = (Digest) new SM3Digestt();
        byte[] hv = new byte[digest.getDigestSize()];
        digest.update(data, 0, data.length);
        digest.doFinal(hv, 0);
        return hv;
    }

    /**
     * 消息认证码函数
     * @param key
     * @param data
     * @return
     */
    public static byte[] MAC(byte[] key, byte[] data) {
        SM3Digestt digest = new SM3Digestt();
        // Digest digest = new SM3Digest();
        byte[] hv = new byte[digest.getDigestSize()];
        digest.update(data, 0, data.length);
        digest.update(key, 0, key.length);
        digest.doFinal(hv, 0);
        return hv;
    }

    public static byte[] KDF(byte[] data, int keyByteLen) {

        // 出错
       // Digest digest = new SM3Digest();
        SM3Digestt digest = new SM3Digestt();
        //Digest digest = new SM3Digest();
        int groupNum = (keyByteLen * 8 + (digest.getDigestSize() * 8 - 1)) / (digest.getDigestSize() * 8);
        byte[] hv = new byte[digest.getDigestSize() * groupNum];

        for(int ct = 1; ct <= groupNum; ++ct) {
            digest.reset();
            digest.update(data, 0, data.length);
            digest.update((byte)(ct >> 24 & 255));
            digest.update((byte)(ct >> 16 & 255));
            digest.update((byte)(ct >> 8 & 255));
            digest.update((byte)(ct & 255));
            digest.doFinal(hv, (ct - 1) * digest.getDigestSize());
        }

        return Arrays.copyOfRange(hv, 0, keyByteLen);
    }

    public static byte[] G1ElementToBytes(Element e) {
        return e.toBytes();
    }

    public static byte[] G2ElementToByte(Element gt) {
        byte[] source = gt.toBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 32;

        for(int i = 0; i < 2; ++i) {
            bos.write(source, (i * 2 + 1) * len, len);
            bos.write(source, i * 2 * len, len);
        }

        return bos.toByteArray();
    }

    public static byte[] GTFiniteElementToByte(Element gt) {
        byte[] source = gt.toBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 32;

        for(int i = 2; i >= 0; --i) {
            bos.write(source, (i * 2 + 1 + 6) * len, len);
            bos.write(source, (i * 2 + 6) * len, len);
            bos.write(source, (i * 2 + 1) * len, len);
            bos.write(source, i * 2 * len, len);
        }

        return bos.toByteArray();
    }

    public static byte[] BigIntegerToBytes(BigInteger b) {
        byte[] temp = b.toByteArray();
        if (b.signum() > 0 && temp[0] == 0) {
            temp = Arrays.copyOfRange(temp, 1, temp.length);
        }

        return temp;
    }

    public static byte[] BigIntegerToBytes(BigInteger b, int length) {
        byte[] temp = b.toByteArray();
        if (b.signum() > 0 && temp[0] == 0) {
            temp = Arrays.copyOfRange(temp, 1, temp.length);
        }

        if (temp.length < length) {
            byte[] result = new byte[length];
            System.arraycopy(temp, 0, result, length - temp.length, temp.length);
            return result;
        } else {
            return temp;
        }
    }

    public static boolean isAllZero(byte[] in) {
        byte[] var1 = in;
        int var2 = in.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            byte b = var1[var3];
            if (b != 0) {
                return false;
            }
        }

        return true;
    }

    public static String toHexString(byte[] data) {
        String hexData = Hex.encodeToString(data, true);
        return showString(hexData);
    }

    public static String showString(String data) {
        if (data.length() < 2) {
            return data + "\n";
        } else {
            StringBuffer sb = new StringBuffer();
            String line = "";

            for(int i = 0; i < data.length(); i += 2) {
                line = line + data.substring(i, i + 2);
                if ((i + 2) % 64 == 0) {
                    sb.append(line);
                    sb.append("\n");
                    line = "";
                } else if ((i + 2) % 8 == 0) {
                    line = line + " ";
                }
            }

            if (!line.isEmpty()) {
                sb.append(line);
                sb.append("\n");
            }

            return sb.toString();
        }
    }

    public static boolean byteEqual(byte[] a, byte[] b) {
        return byteCompare(a, b) == 0;
    }

    public static int byteCompare(byte[] a, byte[] b) {
        int lena = a.length;
        int lenb = b.length;
        int len = lena < lenb ? lena : lenb;

        for(int i = 0; i < len; ++i) {
            if (a[i] < b[i]) {
                return -1 * (i + 1);
            }

            if (a[i] > b[i]) {
                return i + 1;
            }
        }

        if (lena < lenb) {
            return -(len + 1);
        } else if (lena > lenb) {
            return len + 1;
        } else {
            return 0;
        }
    }

    public static byte[] xor(byte[] b1, byte[] b2) {
        int length = b1.length > b2.length ? b2.length : b1.length;
        byte[] result = new byte[length];

        for(int i = 0; i < length; ++i) {
            result[i] = (byte)((b1[i] ^ b2[i]) & 255);
        }

        return result;
    }
}
