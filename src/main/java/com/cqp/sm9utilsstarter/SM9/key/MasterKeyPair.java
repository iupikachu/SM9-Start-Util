package com.cqp.sm9utilsstarter.SM9.key;

import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName MasterKeyPair.java
 * @Description TODO
 * @createTime 2021年03月30日 10:24:00
 */
public class MasterKeyPair {
    MasterPrivateKey prikey;
    MasterPublicKey pubkey;

    public MasterKeyPair(MasterPrivateKey privateKey, MasterPublicKey publicKey) {
        this.prikey = privateKey;
        this.pubkey = publicKey;
    }

    public static MasterKeyPair fromByteArray(SM9Curve curve, byte[] source) {
        int len = 32;
        byte[] bPrikey = Arrays.copyOfRange(source, 0, len);
        byte[] bPubkey = Arrays.copyOfRange(source, len, source.length);
        return new MasterKeyPair(MasterPrivateKey.fromByteArray(bPrikey), MasterPublicKey.fromByteArray(curve, bPubkey));
    }

    public MasterPrivateKey getPrivateKey() {
        return this.prikey;
    }

    public MasterPublicKey getPublicKey() {
        return this.pubkey;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] temp = this.prikey.toByteArray();
        bos.write(temp, 0, temp.length);
        temp = this.pubkey.toByteArray();
        bos.write(temp, 0, temp.length);
        return bos.toByteArray();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SM9 Master key pair:");
        sb.append("\n");
        sb.append(this.prikey.toString());
        sb.append("\n");
        sb.append(this.pubkey.toString());
        return sb.toString();
    }
}
