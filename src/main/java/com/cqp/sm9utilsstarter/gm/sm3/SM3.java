package com.cqp.sm9utilsstarter.gm.sm3;

import com.cqp.sm9utilsstarter.gm.GMProvider;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM3.java
 * @Description TODO
 * @createTime 2021年03月30日 10:15:00
 */
public class SM3 {
    public static final int DIGEST_SIZE = 32;

    private SM3() {
    }

    public static MessageDigest getInstance() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("src/lib/SM3", GMProvider.getProvider());
    }
}
