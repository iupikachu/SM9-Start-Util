package com.cqp.sm9utilsstarter.SM9;


import com.cqp.sm9utilsstarter.SM9.key.*;
import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;
import com.cqp.sm9utilsstarter.util.SM9Utils;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;


import java.math.BigInteger;


/**
 * @author cqp
 * @version 1.0.0
 * @ClassName KGC.java
 * @Description TODO
 * @createTime 2021年03月30日 10:23:00
 */
public class KGC {
    public SM9Curve mCurve;

    public KGC(SM9Curve curve) {
        this.mCurve = curve;
    }

    public SM9Curve getCurve() {
        return this.mCurve;
    }

    public MasterKeyPair genSignMasterKeyPair() {
        BigInteger ks = SM9Utils.genRandom(this.mCurve.random, this.mCurve.N);
        CurveElement ppubs = this.mCurve.P2.duplicate().mul(ks);
        return new MasterKeyPair(new MasterPrivateKey(ks), new MasterPublicKey(ppubs, true));
    }

    public MasterKeyPair genEncryptMasterKeyPair() {
        BigInteger ke = SM9Utils.genRandom(this.mCurve.random, this.mCurve.N);
        CurveElement ppube = this.mCurve.P1.duplicate().mul(ke);
        return new MasterKeyPair(new MasterPrivateKey(ke), new MasterPublicKey(ppube, false));
    }

    protected BigInteger T2(MasterPrivateKey privateKey, String id, byte hid) throws Exception {
//        System.out.println("进入T2");
//        System.out.println("this.mCurve.N:"+this.mCurve.N);
//        System.out.println("mCurve.N:"+mCurve.N);
        BigInteger h1 = SM9Utils.H1(id, hid, mCurve.N); // 出错
//        System.out.println("h1:"+h1.toString());
        BigInteger t1 = h1.add(privateKey.d).mod(this.mCurve.N);
        if (t1.equals(BigInteger.ZERO)) {
            throw new Exception("Need to update the master private key");
        } else {
            return privateKey.d.multiply(t1.modInverse(this.mCurve.N)).mod(this.mCurve.N);
        }
    }

    public PrivateKey genPrivateKey(MasterPrivateKey masterPrivateKey, String id, PrivateKeyType privateKeyType) throws Exception {
        if (privateKeyType == PrivateKeyType.KEY_SIGN) {
            return this.genSignPrivateKey(masterPrivateKey, id);
        } else if (privateKeyType == PrivateKeyType.KEY_KEY_EXCHANGE) {
            return this.genEncryptPrivateKey(masterPrivateKey, id, (byte)2);
        } else if (privateKeyType == PrivateKeyType.KEY_ENCRYPT) {
            return this.genEncryptPrivateKey(masterPrivateKey, id, (byte)3);
        } else {
            throw new Exception("Not support private key type");
        }
    }

    public PrivateKey getPrivateKey(MasterPrivateKey masterPrivateKey, String id) throws Exception {
            return this.genEncryptPrivateKey(masterPrivateKey, id, (byte)3);

    }

    PrivateKey genSignPrivateKey(MasterPrivateKey privateKey, String id) throws Exception {
        BigInteger t2 = this.T2(privateKey, id, (byte)1);
        CurveElement ds = this.mCurve.P1.duplicate().mul(t2);
        return new PrivateKey(ds, (byte)1);
    }

    PrivateKey genEncryptPrivateKey(MasterPrivateKey privateKey, String id, byte hid) throws Exception {
        BigInteger t2 = this.T2(privateKey, id, hid);
        CurveElement de = this.mCurve.P2.duplicate().mul(t2);
        return new PrivateKey(de, hid);
    }
}
