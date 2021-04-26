package com.cqp.sm9utilsstarter.SM9.result;



import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;
import com.cqp.sm9utilsstarter.util.SM9Utils;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;


import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ResultCipherText.java
 * @Description TODO
 * @createTime 2021年03月30日 09:52:00
 */
public final class ResultCipherText {
   public CurveElement C1;
   public byte[] C2;
   public byte[] C3;

    public ResultCipherText(CurveElement C1, byte[] C2, byte[] C3) {
        this.C1 = C1;
        this.C2 = C2;
        this.C3 = C3;
    }

    public static ResultCipherText fromByteArray(SM9Curve curve, byte[] data) {
        int offset = 0;
        byte[] bC1 = Arrays.copyOfRange(data, offset, offset + 64);
        //int offset = offset + 64;
        offset = offset + 64;
        CurveElement C1 = curve.G1.newElement();
        C1.setFromBytes(bC1);
        byte[] bC3 = Arrays.copyOfRange(data, offset, offset + 32);
        offset += 32;
        byte[] bC2 = Arrays.copyOfRange(data, offset, data.length);
        return new ResultCipherText(C1, bC2, bC3);
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] temp = this.C1.toBytes();
        bos.write(temp, 0, temp.length);
        bos.write(this.C3, 0, this.C3.length);
        bos.write(this.C2, 0, this.C2.length);
        return bos.toByteArray();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SM9 encrypt cipher:");
        sb.append("\n");
        sb.append("C1:");
        sb.append("\n");
        sb.append(SM9Utils.toHexString(SM9Utils.G1ElementToBytes(this.C1)));
        sb.append("\n");
        sb.append("C2:");
        sb.append("\n");
        sb.append(SM9Utils.toHexString(this.C2));
        sb.append("\n");
        sb.append("C3:");
        sb.append("\n");
        sb.append(SM9Utils.toHexString(this.C3));
        sb.append("\n");
        return sb.toString();
    }
}
