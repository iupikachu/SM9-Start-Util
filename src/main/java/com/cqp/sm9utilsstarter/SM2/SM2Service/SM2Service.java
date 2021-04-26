package com.cqp.sm9utilsstarter.SM2.SM2Service;

import com.cqp.sm9utilsstarter.SM2.SM2SignVO;

import java.io.IOException;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM2Service.java
 * @Description TODO
 * @createTime 2021年04月21日 09:19:00
 */
public interface SM2Service {

    // 加密
    String encrypt(String plainText) throws IOException;

    // 解密
    String decrypt(String cipherText) throws IOException;

    // 签名
    SM2SignVO sign(String message);

    // 验签
    Boolean verify(String message ,SM2SignVO sign);
}
