package com.cqp.sm9utilsstarter.SM4.SM4Service;

import com.cqp.sm9utilsstarter.SM4.SM4Utils;
import com.cqp.sm9utilsstarter.util.Util;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM4ServiceImpl.java
 * @Description TODO
 * @createTime 2021年04月22日 14:09:00
 */
public class SM4ServiceImpl implements SM4Service{

    SM4Utils sm4 = new SM4Utils();

    @Override
    public String encrypt_ECB(String message) {

        sm4.secretKey = "64EC7C763AB7BF64E2D75FF83A319918";
        sm4.hexString = true;
        String cipherText = sm4.encryptData_ECB(message);
        return cipherText;
    }

    @Override
    public String decrypt_ECB(String cipherText) {
        sm4.secretKey = "64EC7C763AB7BF64E2D75FF83A319918";
        sm4.hexString = true;
        String plainText = sm4.decryptData_ECB(cipherText);
        return plainText;
    }

    @Override
    public String encrypt_CBC(String message) {
        sm4.iv = "31313131313131313131313131313131"; // 初始变量
        String cipherText = sm4.encryptData_CBC(message);
        return cipherText;
    }

    @Override
    public String decrypt_CBC(String cipherText) {
        sm4.iv = "31313131313131313131313131313131";
        String plainText = sm4.decryptData_CBC(cipherText);
        return plainText;
    }
}
