package com.cqp.sm9utilsstarter.SM9;

import com.cqp.sm9utilsstarter.SM9.abstractSM9.AbstractSM9;
import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;
import com.cqp.sm9utilsstarter.SM9.key.MasterPublicKey;
import com.cqp.sm9utilsstarter.SM9.key.PrivateKey;
import com.cqp.sm9utilsstarter.SM9.result.ResultCipherText;
import com.cqp.sm9utilsstarter.gm.sm4.SM4;

import com.cqp.sm9utilsstarter.util.SM9Utils;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;
import it.unisa.dia.gas.plaf.jpbc.util.Arrays;


import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9.java
 * @Description TODO
 * @createTime 2021年03月30日 09:46:00
 */
public class SM9 extends AbstractSM9 {


    public SM9(SM9Curve curve) {
        super(curve);
    }

    /**
     *  加密
     * @param masterPublicKey
     * @param id
     * @param data
     * @param isBaseBlockCipher
     * @param macKeyByteLen
     * @return
     * @throws Exception
     */
    @Override
    public ResultCipherText encrypt(MasterPublicKey masterPublicKey, String id, byte[] data, boolean isBaseBlockCipher, int macKeyByteLen) throws Exception {
        BigInteger h1 = SM9Utils.H1(id, (byte)3, this.mCurve.N);
        CurveElement QB = this.mCurve.P1.duplicate().mul(h1).add(masterPublicKey.Q);

        CurveElement C1;
        byte[] K1;
        byte[] K2;
        do {
            BigInteger r = SM9Utils.genRandom(this.mCurve.random, this.mCurve.N);
            C1 = QB.mul(r);
            Element g = this.mCurve.pairing(masterPublicKey.Q, this.mCurve.P2);
            Element w = g.duplicate().pow(r);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] temp = SM9Utils.G1ElementToBytes(C1);
            bos.write(temp, 0, temp.length);
            temp = SM9Utils.GTFiniteElementToByte(w);
            bos.write(temp, 0, temp.length);
            temp = id.getBytes();
            bos.write(temp, 0, temp.length);
            int k1Len = 16;
            if (!isBaseBlockCipher) {
                k1Len = data.length;
            }

            byte[] K = SM9Utils.KDF(bos.toByteArray(), k1Len + macKeyByteLen);
            K1 = Arrays.copyOfRange(K, 0, k1Len);
            K2 = Arrays.copyOfRange(K, k1Len, K.length);
        } while(SM9Utils.isAllZero(K1));

        byte[] C2;
        if (isBaseBlockCipher) {
            C2 = SM4.ecbCrypt(true, K1, data, 0, data.length);
        } else {
            C2 = SM9Utils.xor(data, K1);
        }

        byte[] C3 = SM9Utils.MAC(K2, C2);
        return new ResultCipherText(C1, C2, C3);
    }

    /**
     * 解密
     * @param resultCipherText
     * @param privateKey
     * @param id
     * @param isBaseBlockCipher
     * @param macKeyByteLen
     * @return
     * @throws Exception
     */
    @Override
    public byte[] decrypt(ResultCipherText resultCipherText, PrivateKey privateKey, String id, boolean isBaseBlockCipher, int macKeyByteLen) throws Exception {
        if (!resultCipherText.C1.isValid()) {
            throw new Exception("C1 is not on G1 group");
        } else {
            Element w = this.mCurve.pairing(resultCipherText.C1, privateKey.d);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] temp = SM9Utils.G1ElementToBytes(resultCipherText.C1);
            bos.write(temp, 0, temp.length);
            temp = SM9Utils.GTFiniteElementToByte(w);
            bos.write(temp, 0, temp.length);
            temp = id.getBytes();
            bos.write(temp, 0, temp.length);
            int k1Len = 16;
            if (!isBaseBlockCipher) {
                k1Len = resultCipherText.C2.length;
            }

            byte[] K = SM9Utils.KDF(bos.toByteArray(), k1Len + macKeyByteLen);
            byte[] K1 = Arrays.copyOfRange(K, 0, k1Len);
            byte[] K2 = Arrays.copyOfRange(K, k1Len, K.length);
            if (SM9Utils.isAllZero(K1)) {
                throw new Exception("K1 is all zero");
            } else {
                byte[] M;
                if (isBaseBlockCipher) {
                    M = SM4.ecbCrypt(false, K1, resultCipherText.C2, 0, resultCipherText.C2.length);
                } else {
                    M = SM9Utils.xor(resultCipherText.C2, K1);
                }

                byte[] u = SM9Utils.MAC(K2, resultCipherText.C2);
                if (!SM9Utils.byteEqual(u, resultCipherText.C3)) {
                    throw new Exception("C3 verify failed");
                } else {
                    return M;
                }
            }
        }
    }
}
