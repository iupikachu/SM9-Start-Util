package com.cqp.sm9utilsstarter.SM9.pairing;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Point;
import it.unisa.dia.gas.jpbc.Polynomial;
import it.unisa.dia.gas.plaf.jpbc.field.gt.GTFiniteElement;
import it.unisa.dia.gas.plaf.jpbc.field.gt.GTFiniteField;
import it.unisa.dia.gas.plaf.jpbc.pairing.map.AbstractPairingMap;

import java.math.BigInteger;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9RatePairingMap.java
 * @Description TODO
 * @createTime 2021年03月30日 09:42:00
 */
public class SM9RatePairingMap extends AbstractPairingMap {
    private SM9Pairing pairingData;

    public SM9RatePairingMap(SM9Pairing pairing) {
        super(pairing);
        this.pairingData = pairing;
    }

    public Element pairing(Point P, Point Q) {
        BigInteger a = this.pairingData.t.multiply(BigInteger.valueOf(6L)).add(BigInteger.valueOf(2L));
        Point t = (Point)Q.duplicate();
        Polynomial f = (Polynomial)this.pairingData.getFq12().newOneElement();

        for(int i = a.bitLength() - 2; i >= 0; --i) {
            f.square();
            f.mul(this.line(t, P));
            t.add(t);
            if (a.testBit(i)) {
                f.mul(this.line(t, Q, P));
                t.add(Q);
            }
        }

        Point Q11 = this.fobasmiracl(Q);
        Point Q22 = this.fobasmiracl(Q11);
        f.mul(this.line(t, Q11, P));
        t.add(Q11);
        f.mul(this.line(t, (Point)Q22.negate(), P));
        t.sub(Q22);
        BigInteger q = this.pairingData.getQ();
        Element e = f.duplicate().pow(q.pow(12).subtract(BigInteger.ONE).divide(this.pairingData.getN()));
        return new GTFiniteElement(this, (GTFiniteField)this.pairingData.getGT(), e);
    }

    @Override
    public void finalPow(Element element) {
    }

    public Element line(Point A, Point B, Point C) {
        Element ax = A.getX().duplicate();
        Element ay = A.getY().duplicate();
        Element bx = B.getX().duplicate();
        Element by = B.getY().duplicate();
        Element cx = C.getX().duplicate();
        Element cy = C.getY().duplicate();
        Point lamda = (Point)ax.getField().newElement();
        lamda = (Point)ay.duplicate().sub(by).div(ax.duplicate().sub(bx));
        Element cof3 = by.duplicate().sub(lamda.duplicate().mul(bx));
        Element cof5 = lamda.duplicate().mulZn(cx);
        Polynomial result = this.pairingData.getFq12().newElement();
        Element betaInvert = this.pairingData.getNegAlphaInv();
        Point tempfp2 = (Point)ax.getField().newElement();
        tempfp2.getX().set(cy.negate());
        tempfp2.getY().setToZero();
        result.getCoefficient(0).set(tempfp2);
        result.getCoefficient(3).set(cof3.mul(betaInvert));
        result.getCoefficient(5).set(cof5.mul(betaInvert));
        return result;
    }

    public Element line(Point A, Point C) {
        Element ax = A.getX().duplicate();
        Element ay = A.getY().duplicate();
        Element cx = C.getX().duplicate();
        Element cy = C.getY().duplicate();
        Element lamda = ax.getField().newElement();
        lamda = ax.duplicate().square().mul(3).div(ay.duplicate().mul(2));
        Element cof3 = ay.duplicate().sub(lamda.duplicate().mul(ax));
        Element cof5 = lamda.duplicate().mulZn(cx);
        Polynomial result = this.pairingData.getFq12().newElement();
        Element betaInvert = this.pairingData.getNegAlphaInv();
        Point tempfp2 = (Point)ax.getField().newElement();
        tempfp2.getX().set(cy.negate());
        tempfp2.getY().setToZero();
        result.getCoefficient(0).set(tempfp2);
        result.getCoefficient(3).set(cof3.mul(betaInvert));
        result.getCoefficient(5).set(cof5.mul(betaInvert));
        return result;
    }

    public Point fobasmiracl(Point point) {
        Point px = (Point)point.getX().duplicate();
        Point py = (Point)point.getY().duplicate();
        BigInteger q = this.pairingData.getQ();
        Point x = (Point)this.pairingData.getFq2().newElement();
        x.getX().setToZero();
        x.getY().setToOne();
        x.pow(q.subtract(BigInteger.ONE).divide(BigInteger.valueOf(6L)));
        Point r = (Point)x.duplicate().invert();
        Point w = (Point)r.duplicate().square();
        px.getY().negate();
        px.mul(w);
        py.getY().negate();
        py.mul(w).mul(r);
        Point result = (Point)point.getField().newRandomElement();
        result.getX().set(px);
        result.getY().set(py);
        return result;
    }
}

