package com.cqp.sm9utilsstarter.SM2.SM2Service;

import com.cqp.sm9utilsstarter.SM2.SM2EncDecUtils;
import com.cqp.sm9utilsstarter.SM2.SM2SignVO;
import com.cqp.sm9utilsstarter.SM2.SM2SignVerUtils;
import com.cqp.sm9utilsstarter.util.Util;


import java.io.IOException;


/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM2ServiceImpl.java
 * @Description TODO
 * @createTime 2021年04月21日 09:28:00
 */

public class SM2ServiceImpl implements SM2Service{
    // 秘钥可以使用 generatekeyPair()生成
    // 公钥
    final String pubk = "04BB34D657EE7E8490E66EF577E6B3CEA28B739511E787FB4F71B7F38F241D87F18A5A93DF74E90FF94F4EB907F271A36B295B851F971DA5418F4915E2C1A23D6E";
    // 私钥
    final String prik = "0B1CE43098BC21B8E82B5C065EDB534CB86532B1900A49D49F3C53762D2997FA";

    /**
     * 加密
     * @param plainText
     * @return
     * @throws IOException
     */
    @Override
    public String encrypt(String plainText)  {
        byte[] sourceData = plainText.getBytes();
        String cipherText = null;
        try {
            cipherText = SM2EncDecUtils.encrypt(Util.hexToByte(pubk), sourceData);
            return cipherText;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  解密
     * @param cipherText
     * @return
     */
    @Override
    public String decrypt(String cipherText)  {

        String palinText = null;
        try {
            palinText = new String(SM2EncDecUtils.decrypt(Util.hexToByte(prik), Util.hexToByte(cipherText)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palinText;
    }

    @Override
    public SM2SignVO sign(String message) {
        byte [] sourceData = message.getBytes();
        try {
            // 生成签名
            SM2SignVO sign = SM2SignVerUtils.Sign2SM2(Util.hexStringToBytes(prik),sourceData);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public Boolean verify(String message, SM2SignVO sign) {
        byte [] sourceData = message.getBytes();
        SM2SignVO verify = SM2SignVerUtils.VerifySignSM2(Util.hexStringToBytes(pubk), sourceData, Util.hexStringToBytes(sign.getSm2_signForSoft()));
        return verify.isVerify();
    }
}
