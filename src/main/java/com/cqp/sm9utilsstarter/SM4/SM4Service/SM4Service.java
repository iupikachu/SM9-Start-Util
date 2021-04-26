package com.cqp.sm9utilsstarter.SM4.SM4Service;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM4Service.java
 * @Description TODO
 * @createTime 2021年04月22日 14:04:00
 */
public interface SM4Service {

    // encrypt ECB加密
    String encrypt_ECB(String message);

    // decrypt ECB解密
    String decrypt_ECB(String cipherText);

    // encrypt CBC加密
    String encrypt_CBC(String message);

    // decrypt CBC解密
    String decrypt_CBC(String cipherText);
}
