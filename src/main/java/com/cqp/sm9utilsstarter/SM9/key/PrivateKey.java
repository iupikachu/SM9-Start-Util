package com.cqp.sm9utilsstarter.SM9.key;

import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;
import com.cqp.sm9utilsstarter.util.SM9Utils;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;


import java.io.ByteArrayOutputStream;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName PrivateKey.java
 * @Description TODO
 * @createTime 2021年03月30日 10:27:00
 */
public class PrivateKey {
   public CurveElement d;
   public   byte hid;

    public PrivateKey(CurveElement point, byte hid) {
        this.d = point;
        this.hid = hid;
    }

    public static PrivateKey fromByteArray(SM9Curve curve, byte[] source) {
        byte hid = source[0];
        CurveElement d;
        if (hid == 1) {
            d = curve.G1.newElement();
        } else {
            d = curve.G2.newElement();
        }

        d.setFromBytes(source, 1);
        return new PrivateKey(d, hid);
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(this.hid);
        byte[] temp = this.d.toBytes();
        bos.write(temp, 0, temp.length);
        return bos.toByteArray();
    }

    @Override
    public String toString() {
        return this.hid == 1 ? "SM9 private key:\n" + SM9Utils.toHexString(SM9Utils.G1ElementToBytes(this.d)) : "SM9 private key:\n" + SM9Utils.toHexString(SM9Utils.G2ElementToByte(this.d));
    }
}
