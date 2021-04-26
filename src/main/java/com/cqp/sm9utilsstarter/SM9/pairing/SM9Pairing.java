package com.cqp.sm9utilsstarter.SM9.pairing;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.field.poly.PolyModField;
import it.unisa.dia.gas.plaf.jpbc.pairing.f.TypeFPairing;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9Pairing.java
 * @Description TODO
 * @createTime 2021年03月30日 09:41:00
 */
public class SM9Pairing extends TypeFPairing {
    protected BigInteger t;

    public SM9Pairing(PairingParameters curveParams) {
        super(curveParams);
    }

    public SM9Pairing(SecureRandom random, PairingParameters curveParams) {
        super(random, curveParams);
    }

    @Override
    protected void initParams() {
        super.initParams();
        this.t = this.curveParams.getBigInteger("t");
    }

    @Override
    protected void initMap() {
        this.pairingMap = new SM9RatePairingMap(this);
    }

    public BigInteger getN() {
        return this.r;
    }

    public Field getFq2() {
        return this.Fq2;
    }

    public PolyModField getFq12() {
        return this.Fq12;
    }

    public BigInteger getQ() {
        return this.q;
    }

    public Element getNegAlphaInv() {
        return this.negAlphaInv;
    }

    public PairingParameters getPairingParameters() {
        return this.curveParams;
    }
}
