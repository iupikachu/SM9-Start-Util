package com.cqp.sm9utilsstarter.SM9.curve;



import com.cqp.sm9utilsstarter.util.Hex;
import it.unisa.dia.gas.plaf.jpbc.pairing.parameters.PropertiesParameters;

import java.math.BigInteger;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9CurveParameters.java
 * @Description TODO
 * @createTime 2021年03月30日 09:36:00
 */
public class SM9CurveParameters {
    public static final int nBits = 256;
    public static final int nbeta = -2;
    public static final int eid = 4;
    public static final BigInteger b = BigInteger.valueOf(5L);
    public static final BigInteger t = new BigInteger("600000000058F98A", 16);
    public static final BigInteger q = new BigInteger("B640000002A3A6F1D603AB4FF58EC74521F2934B1A7AEEDBE56F9B27E351457D", 16);
    public static final BigInteger N = new BigInteger("B640000002A3A6F1D603AB4FF58EC74449F2934B18EA8BEEE56EE19CD69ECF25", 16);
    public static final BigInteger beta = new BigInteger("B640000002A3A6F1D603AB4FF58EC74521F2934B1A7AEEDBE56F9B27E351457B", 16);
    public static final BigInteger alpha0;
    public static final BigInteger alpha1;
    public static final BigInteger P1_x;
    public static final BigInteger P1_y;
    public static final BigInteger P2_x_a;
    public static final BigInteger P2_x_b;
    public static final BigInteger P2_y_a;
    public static final BigInteger P2_y_b;
    public static byte[] P1_bytes;
    public static byte[] P2_bytes;

    public SM9CurveParameters() {
    }

    public static PropertiesParameters createSM9PropertiesParameters() {
        PropertiesParameters params = new PropertiesParameters();
        params.put("type", "f");
        params.put("q", q.toString());
        params.put("r", N.toString());
        params.put("b", b.toString());
        params.put("beta", beta.toString());
        params.put("alpha0", alpha0.toString());
        params.put("alpha1", alpha1.toString());
        params.put("t", t.toString());
        return params;
    }

    static {
        alpha0 = BigInteger.ZERO;
        alpha1 = new BigInteger("B640000002A3A6F1D603AB4FF58EC74521F2934B1A7AEEDBE56F9B27E351457C", 16);
        P1_x = new BigInteger("93DE051D62BF718FF5ED0704487D01D6E1E4086909DC3280E8C4E4817C66DDDD", 16);
        P1_y = new BigInteger("21FE8DDA4F21E607631065125C395BBC1C1C00CBFA6024350C464CD70A3EA616", 16);
        P2_x_a = new BigInteger("3722755292130B08D2AAB97FD34EC120EE265948D19C17ABF9B7213BAF82D65B", 16);
        P2_x_b = new BigInteger("85AEF3D078640C98597B6027B441A01FF1DD2C190F5E93C454806C11D8806141", 16);
        P2_y_a = new BigInteger("A7CF28D519BE3DA65F3170153D278FF247EFBA98A71A08116215BBA5C999A7C7", 16);
        P2_y_b = new BigInteger("17509B092E845C1266BA0D262CBEE6ED0736A96FA347C8BD856DC76B84EBEB96", 16);
        P1_bytes = Hex.decode("93DE051D62BF718FF5ED0704487D01D6E1E4086909DC3280E8C4E4817C66DDDD21FE8DDA4F21E607631065125C395BBC1C1C00CBFA6024350C464CD70A3EA616");
        P2_bytes = Hex.decode("3722755292130B08D2AAB97FD34EC120EE265948D19C17ABF9B7213BAF82D65B85AEF3D078640C98597B6027B441A01FF1DD2C190F5E93C454806C11D8806141A7CF28D519BE3DA65F3170153D278FF247EFBA98A71A08116215BBA5C999A7C717509B092E845C1266BA0D262CBEE6ED0736A96FA347C8BD856DC76B84EBEB96");
    }
}

