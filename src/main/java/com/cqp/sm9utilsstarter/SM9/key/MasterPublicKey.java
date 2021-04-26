package com.cqp.sm9utilsstarter.SM9.key;

import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;
import com.cqp.sm9utilsstarter.util.SM9Utils;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;


import java.io.ByteArrayOutputStream;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName MasterPublicKey.java
 * @Description TODO
 * @createTime 2021年03月30日 10:00:00
 */
public class MasterPublicKey {
    public CurveElement Q;
    boolean isSignKey;

    public MasterPublicKey(CurveElement point, boolean isSignKey) {
        this.Q = point;
        this.isSignKey = isSignKey;
    }

    public static MasterPublicKey fromByteArray(SM9Curve curve, byte[] source) {
        boolean isSignKey = false;
        if (source[0] != 0) {
            isSignKey = true;
        }

        CurveElement Q;
        if (isSignKey) {
            Q = curve.G2.newElement();
        } else {
            Q = curve.G1.newElement();
        }

        Q.setFromBytes(source, 1);
        return new MasterPublicKey(Q, isSignKey);
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (this.isSignKey) {
            bos.write(1);
        } else {
            bos.write(0);
        }

        byte[] temp = this.Q.toBytes();
        bos.write(temp, 0, temp.length);
        return bos.toByteArray();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("sm9 master public key:");
        sb.append("\n");
        if (this.isSignKey) {
            sb.append(SM9Utils.toHexString(SM9Utils.G2ElementToByte(this.Q)));
        } else {
            sb.append(SM9Utils.toHexString(SM9Utils.G1ElementToBytes(this.Q)));
        }

        return sb.toString();
    }
}
