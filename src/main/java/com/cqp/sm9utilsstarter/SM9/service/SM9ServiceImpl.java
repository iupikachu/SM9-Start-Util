package com.cqp.sm9utilsstarter.SM9.service;

import com.cqp.sm9utilsstarter.SM9.KGC;
import com.cqp.sm9utilsstarter.SM9.SM9;
import com.cqp.sm9utilsstarter.SM9.SM9Utils.ObjectStorage;
import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;
import com.cqp.sm9utilsstarter.SM9.key.MasterKeyPair;
import com.cqp.sm9utilsstarter.SM9.key.MasterPublicKey;
import com.cqp.sm9utilsstarter.SM9.key.PrivateKey;
import com.cqp.sm9utilsstarter.SM9.key.PrivateKeyType;
import com.cqp.sm9utilsstarter.SM9.result.ResultCipherText;
import com.cqp.sm9utilsstarter.util.SM9Utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9ServiceImpl.java
 * @Description TODO
 * @createTime 2021年04月07日 09:50:00
 */
public class SM9ServiceImpl implements SM9Service{

    private final static HashMap<String, MasterKeyPair> keyMap = new HashMap<>();

    private SM9Curve sm9Curve = new SM9Curve();

    KGC kgc = new KGC(sm9Curve);

    /**
     * 加密
     * @param IBE_Identify
     * @param msg
     * @return
     * @throws Exception
     */
    @Override
    public ResultCipherText encrypt(String IBE_Identify, String msg) throws Exception {


        SM9 sm9 = new SM9(sm9Curve);
        MasterKeyPair encryptMasterKeyPair = kgc.genEncryptMasterKeyPair();
        keyMap.put(IBE_Identify,encryptMasterKeyPair);
        PrivateKey encryptPrivateKey = kgc.genPrivateKey(encryptMasterKeyPair.getPrivateKey(),IBE_Identify, PrivateKeyType.KEY_ENCRYPT);
        int macKeyByteLen = 32;
        boolean isBaseBlockCipher = false;
        ResultCipherText resultCipherText = sm9.encrypt(encryptMasterKeyPair.getPublicKey(), IBE_Identify, msg.getBytes(), isBaseBlockCipher, macKeyByteLen);
        // 测试是否加密正确
        byte[] msgd = sm9.decrypt(resultCipherText, encryptPrivateKey, IBE_Identify, isBaseBlockCipher, macKeyByteLen);
        if (SM9Utils.byteEqual(msg.getBytes(), msgd)) {
            System.out.println("加密成功");
            return resultCipherText;
        }else{
            System.out.println("加密失败");
            return null;
        }

    }

    /**
     * 解密
     * @param IBE_Identify
     * @param resultCipherText
     * @return
     */
    @Override
    public String decrypt(String IBE_Identify, ResultCipherText resultCipherText) throws Exception {


        SM9 sm9 = new SM9(sm9Curve);
        MasterKeyPair encryptMasterKeyPair = keyMap.get(IBE_Identify);
        boolean isBaseBlockCipher = false;
        int macKeyByteLen = 32;

        PrivateKey encryptPrivateKey = kgc.genPrivateKey(encryptMasterKeyPair.getPrivateKey(), IBE_Identify, PrivateKeyType.KEY_ENCRYPT);
        byte[] msg = sm9.decrypt(resultCipherText, encryptPrivateKey, IBE_Identify, isBaseBlockCipher, macKeyByteLen);
        return new String(msg);
    }

    /**
     * 注册
     * @param IBE_Identify
     * @return
     */
    @Override
    public Map<String, Object> register(String IBE_Identify) throws Exception {

        // 判断有无注册
        Object d = ObjectStorage.USERDOMAIN.get(IBE_Identify);
        if( d != null){
            return null;
        }
        // 加密公钥
        MasterKeyPair encryptMasterKeyPair = kgc.genEncryptMasterKeyPair();
        MasterPublicKey publicKey = encryptMasterKeyPair.getPublicKey();

        // 用户公钥就是他自己的身份标识 IBE_Identify
        // 用户私钥
        PrivateKey encryptPrivateKey = kgc.genPrivateKey(encryptMasterKeyPair.getPrivateKey(), IBE_Identify, PrivateKeyType.KEY_ENCRYPT);

        ObjectStorage.USERDOMAIN.put(IBE_Identify+"privateKey",encryptPrivateKey);

        Map<String, Object> result = new HashMap<>();
        result.put("sysPubKey",publicKey.toByteArray());
        result.put("userPrikey",encryptPrivateKey.toByteArray());
        return result;
    }
}
