package com.cqp.sm9utilsstarter.SM9.curve;



import com.cqp.sm9utilsstarter.SM9.pairing.SM9Pairing;
import com.cqp.sm9utilsstarter.util.SM9Utils;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveField;
import it.unisa.dia.gas.plaf.jpbc.field.gt.GTFiniteField;



import java.math.BigInteger;
import java.security.SecureRandom;

public class SM9Curve {
    public SecureRandom random;
    public BigInteger N;
    public CurveField G1;
    public CurveField G2;
    public GTFiniteField GT;
    public SM9Pairing sm9Pairing;
    public CurveElement P1;
    public CurveElement P2;
    protected static final byte HID_SIGN = 1;
    protected static final byte HID_KEY_EXCHANGE = 2;
    protected static final byte HID_ENCRYPT = 3;

    public SM9Curve() {
        this(new SecureRandom());
    }

    public SM9Curve(SecureRandom random) {
        this.random = random;
        PairingParameters parameters = SM9CurveParameters.createSM9PropertiesParameters();
        this.sm9Pairing = new SM9Pairing(random, parameters);
        this.N = this.sm9Pairing.getN();
        this.G1 = (CurveField)this.sm9Pairing.getG1();
        this.G2 = (CurveField)this.sm9Pairing.getG2();
        this.GT = (GTFiniteField)this.sm9Pairing.getGT();
        this.P1 = this.G1.newElement();
        this.P1.setFromBytes(SM9CurveParameters.P1_bytes);
        this.P2 = this.G2.newElement();
        this.P2.setFromBytes(SM9CurveParameters.P2_bytes);

    }

    public Element pairing(CurveElement p1, CurveElement p2) {
        return this.sm9Pairing.pairing(p1, p2);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String newLine = "\n";
        PairingParameters pairingParameters = this.sm9Pairing.getPairingParameters();
        sb.append("----------------------------------------------------------------------");
        sb.append(newLine);
        sb.append("SM9 curve parameters:");
        sb.append(newLine);
        sb.append("b:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(pairingParameters.getBigInteger("b"))));
        sb.append(newLine);
        sb.append("t:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(pairingParameters.getBigInteger("t"))));
        sb.append(newLine);
        sb.append("q:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(pairingParameters.getBigInteger("q"))));
        sb.append(newLine);
        sb.append("N:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(pairingParameters.getBigInteger("r"))));
        sb.append(newLine);
        sb.append("beta:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(pairingParameters.getBigInteger("beta"))));
        sb.append(newLine);
        sb.append("alpha0:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(pairingParameters.getBigInteger("alpha0"))));
        sb.append(newLine);
        sb.append("alpha1:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.BigIntegerToBytes(pairingParameters.getBigInteger("alpha1"))));
        sb.append(newLine);
        sb.append("P1:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.G1ElementToBytes(this.P1)));
        sb.append(newLine);
        sb.append("P2:\n");
        sb.append(SM9Utils.toHexString(SM9Utils.G2ElementToByte(this.P2)));
        sb.append("----------------------------------------------------------------------");
        sb.append(newLine);
        return sb.toString();
    }
}
