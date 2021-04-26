package com.cqp.sm9utilsstarter.SM9.key;

import com.cqp.sm9utilsstarter.util.SM9Utils;


import java.math.BigInteger;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName MasterPrivateKey.java
 * @Description TODO
 * @createTime 2021年03月30日 10:25:00
 */
public class MasterPrivateKey {
    public BigInteger d;

    public MasterPrivateKey(BigInteger d) {
        this.d = d;
    }

    public static MasterPrivateKey fromByteArray(byte[] source) {
        BigInteger d = new BigInteger(1, source);
        return new MasterPrivateKey(d);
    }

    public byte[] toByteArray() {
        return SM9Utils.BigIntegerToBytes(this.d, 32);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("sm9 master private key:");
        sb.append("\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(this.d, 32)));
        return sb.toString();
    }
}
