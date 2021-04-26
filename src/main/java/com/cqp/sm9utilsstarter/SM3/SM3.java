package com.cqp.sm9utilsstarter.SM3;

import com.cqp.sm9utilsstarter.util.Util;


/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM3.java
 * @Description TODO
 * @createTime 2021年03月29日 09:42:00
 */
public class SM3 {

    // iv初始值
    public  static final byte[] iv=
            {
                    (byte)0x73, (byte)0x80, (byte)0x16, (byte)0x6f,
                    (byte)0x49, (byte)0x14, (byte)0xb2, (byte)0xb9,
                    (byte)0x17, (byte)0x24, (byte)0x42, (byte)0xd7,
                    (byte)0xda, (byte)0x8a, (byte)0x06, (byte)0x00,
                    (byte)0xa9, (byte)0x6f, (byte)0x30, (byte)0xbc,
                    (byte)0x16, (byte)0x31, (byte)0x38, (byte)0xaa,
                    (byte)0xe3, (byte)0x8d, (byte)0xee, (byte)0x4d,
                    (byte)0xb0, (byte)0xfb, (byte)0x0e, (byte)0x4e
            };

    // 常量Tj
    public static int[] Tj = new int[64];
    static {
        for (int i = 0; i < 16; i++) {
            Tj[i] = 0x79cc4519;
        }

        for (int i = 16; i < 64; i++) {
            Tj[i] = 0x7a879d8a;
        }
    }

    // 布尔函数的位运算函数
    private static int FFj_1(int X,int Y,int Z){
        int result = X ^ Y ^ Z ;
        return result;
    }
    private static int FFj_2(int X,int Y,int Z){
        int result = ((X & Y) | (X & Z) | (Y & Z)) ;
        return result;
    }
    private static int GGj_1(int X,int Y,int Z){
        int result = X ^ Y ^ Z;
        return result;
    }
    private static int GGj_2(int X,int Y,int Z){
        int result = (X & Y) | (~X & Z);
        return result;
    }

    // 布尔函数.1 FFj
    private static int FFj(int X,int Y,int Z,int j){
        if(j >= 0 && j <= 15 ){
            return FFj_1(X,Y,Z);
        } else {
            return FFj_2(X,Y,Z);
        }
    }

    // 布尔函数.2 GGj
    private static int GGj(int X,int Y,int Z,int j){
        if(j >= 0 && j <=15){
            return GGj_1(X,Y,Z);
        }else{
            return GGj_2(X,Y,Z);
        }
    }

    // 置换函数 P0 P1
    private static int P0(int X){
//        int y = rotateLeft(X, 9);
      int  y = bitCycleLeft(X, 9);
//        int z = rotateLeft(X, 17);
      int  z = bitCycleLeft(X, 17);
      int t = X ^ y ^ z;
        return t;
    }

    private static int P1(int X) {
        int t = X ^ bitCycleLeft(X, 15) ^ bitCycleLeft(X, 23);
        return t;
    }

    /**
     * 字节数组逆序
     */
    private static byte[] back(byte[] in) {
        byte[] out = new byte[in.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = in[out.length - i - 1];
        }
        return out;
    }
    // 大端数据 左边为高有效位 右边为低有效位 数的高阶字节放在存储器的低地址，数的低阶字节放在存储器的高地址
    private static byte[] bigEndianIntToByte(int num) {
        return back(Util.intToBytes(num));
    }

    private static int bigEndianByteToInt(byte[] bytes) {
        return Util.byteToInt(back(bytes));
    }

    public static int rotateLeft(int x, int n) {
        return (x << n) | (x >> (32 - n));
    }

    // 循环左移k比特运算
    // 比如左移15bit
    // byteLen=1 循环左移1个byte
    // len=7 再循环左移7个bit

    private static int bitCycleLeft(int n, int bitLen) {
        bitLen %= 32;
        byte[] tmp = bigEndianIntToByte(n);
        int byteLen = bitLen / 8;
        int len = bitLen % 8;
        if (byteLen > 0) {
            tmp = byteCycleLeft(tmp, byteLen);
        }

        if (len > 0) {
            tmp = bitSmall8CycleLeft(tmp, len);
        }

        return bigEndianByteToInt(tmp);
    }
    // 左移bit
    // 假设    in=      01010101     循环左移7bit  len=7
    //        t1=      10000000
    //        t2=      00101010
    // t3=t1|t2 =      10101010
    private static byte[] bitSmall8CycleLeft(byte[] in, int len) {
        byte[] tmp = new byte[in.length];
        int t1, t2, t3;
        for (int i = 0; i < tmp.length; i++) {
            t1 = (byte) ((in[i] & 0x000000ff) << len);
            t2 = (byte) ((in[(i + 1) % tmp.length] & 0x000000ff) >> (8 - len));
            t3 = (byte) (t1 | t2);
            tmp[i] = (byte) t3;
        }

        return tmp;
    }
    // 左移 byte
    private static byte[] byteCycleLeft(byte[] in, int byteLen) {
        byte[] tmp = new byte[in.length];
        System.arraycopy(in, byteLen, tmp, 0, in.length - byteLen);
        System.arraycopy(in, 0, tmp, in.length - byteLen, byteLen);
        return tmp;
    }

    // 消息扩展过程  扩展生成132个字w0 w1 w2....用于压缩函数CF
    private static int[][] expand(int[] B) {
        int W[] = new int[68];
        int W1[] = new int[64];
        for (int i = 0; i < B.length; i++) {
            W[i] = B[i];
        }

        for (int i = 16; i < 68; i++) {
            W[i] = P1(W[i - 16] ^ W[i - 9] ^ bitCycleLeft(W[i - 3], 15))
                    ^ bitCycleLeft(W[i - 13], 7) ^ W[i - 6];
        }

        for (int i = 0; i < 64; i++) {
            W1[i] = W[i] ^ W[i + 4];
        }

        int arr[][] = new int[][]{W, W1};  // 二维数组传递 w w1
        return arr;
    }

   // 压缩函数 CF Compression Function
   public static byte[] CF(byte[] V, byte[] B) {
       int[] v, b;
       v = convert(V);
       b = convert(B);
       return convert(CF(v, b));
   }


    public  static int[] CF (int[] V,int[] B){
        // 寄存器 abcdefgh
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h;

        // 中间变量
        int ss1;
        int ss2;
        int tt1;
        int tt2;

        a = V[0];
        b = V[1];
        c = V[2];
        d = V[3];
        e = V[4];
        f = V[5];
        g = V[6];
        h = V[7];

        int[][] arr = expand(B);
        int[] w = arr[0];
        int[] w1 = arr[1];

        // 迭代压缩
        for (int j = 0; j < 64; j++) {
            ss1 = (bitCycleLeft(a, 12) + e + bitCycleLeft(Tj[j], j));
            ss1 = bitCycleLeft(ss1, 7);
            ss2 = ss1 ^ bitCycleLeft(a, 12);
            tt1 = FFj(a, b, c, j) + d + ss2 + w1[j];
            tt2 = GGj(e, f, g, j) + h + ss1 + w[j];
            d = c;
            c = bitCycleLeft(b, 9);
            b = a;
            a = tt1;
            h = g;
            g = bitCycleLeft(f, 19);
            f = e;
            e = P0(tt2);
        }

        int[] out = new int[8];
        out[0] = a ^ V[0];
        out[1] = b ^ V[1];
        out[2] = c ^ V[2];
        out[3] = d ^ V[3];
        out[4] = e ^ V[4];
        out[5] = f ^ V[5];
        out[6] = g ^ V[6];
        out[7] = h ^ V[7];

        return out;
    }

    private static int[] convert(byte[] arr) {
        int[] out = new int[arr.length / 4];
        byte[] tmp = new byte[4];
        for (int i = 0; i < arr.length; i += 4) {
            System.arraycopy(arr, i, tmp, 0, 4);
            out[i / 4] = bigEndianByteToInt(tmp);
        }
        return out;
    }

    private static byte[] convert(int[] arr) {
        byte[] out = new byte[arr.length * 4];
        byte[] tmp = null;
        for (int i = 0; i < arr.length; i++) {
            tmp = bigEndianIntToByte(arr[i]);
            System.arraycopy(tmp, 0, out, i * 4, 4);
        }
        return out;
    }

    /**
     * 对最后一个分组字节数据padding
     *
     * @param in
     * @param bLen 分组个数
     * @return
     */
    public static byte[] padding(byte[] in, int bLen) {
        int k = 448 - (8 * in.length + 1) % 512;
        if (k < 0) {
            k = 960 - (8 * in.length + 1) % 512;
        }
        k += 1;
        byte[] padd = new byte[k / 8];
        padd[0] = (byte) 0x80;
        long n = in.length * 8 + bLen * 512;
        byte[] out = new byte[in.length + k / 8 + 64 / 8];
        int pos = 0;
        System.arraycopy(in, 0, out, 0, in.length);
        pos += in.length;
        System.arraycopy(padd, 0, out, pos, padd.length);
        pos += padd.length;
        byte[] tmp = back(Util.longToBytes(n));
        System.arraycopy(tmp, 0, out, pos, tmp.length);
        return out;
    }
}
