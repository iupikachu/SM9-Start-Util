package com.cqp.sm9utilsstarter.gm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName GMProvider.java
 * @Description TODO
 * @createTime 2021年03月30日 10:11:00
 */
public class GMProvider {
    private static Provider sProvider = null;

    public static Provider getProvider() {
        if (sProvider == null) {
            sProvider = new BouncyCastleProvider();
        }

        return sProvider;
    }

    private GMProvider() {
    }
}
