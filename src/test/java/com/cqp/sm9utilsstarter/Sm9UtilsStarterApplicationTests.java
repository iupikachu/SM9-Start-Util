package com.cqp.sm9utilsstarter;

import com.cqp.sm9utilsstarter.SM2.SM2Service.SM2Service;
import com.cqp.sm9utilsstarter.SM2.SM2Service.SM2ServiceImpl;
import com.cqp.sm9utilsstarter.SM2.SM2SignVO;
import com.cqp.sm9utilsstarter.SM3.SM3Digestt;
import com.cqp.sm9utilsstarter.SM3.SM3Service.SM3Service;
import com.cqp.sm9utilsstarter.SM3.SM3Service.SM3ServiceImpl;
import com.cqp.sm9utilsstarter.SM4.SM4Service.SM4Service;
import com.cqp.sm9utilsstarter.SM4.SM4Service.SM4ServiceImpl;
import com.cqp.sm9utilsstarter.SM9.KGC;
import com.cqp.sm9utilsstarter.SM9.SM9;
import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;
import com.cqp.sm9utilsstarter.SM9.key.MasterKeyPair;
import com.cqp.sm9utilsstarter.SM9.key.PrivateKey;
import com.cqp.sm9utilsstarter.SM9.result.ResultCipherText;
import com.cqp.sm9utilsstarter.SM9.service.SM9Service;
import com.cqp.sm9utilsstarter.SM9.service.SM9ServiceImpl;
import com.cqp.sm9utilsstarter.SM2.SM2EncDecUtils;
import com.cqp.sm9utilsstarter.util.SM9Utils;
import com.cqp.sm9utilsstarter.util.Util;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@SpringBootTest
class Sm9UtilsStarterApplicationTests {

    @Test
    void contextLoads() throws Exception {

        String id_B = "Bob";
        String msg = "Chinese IBE standard";
        SM9Curve sm9Curve = new SM9Curve();
        KGC kgc = new KGC(sm9Curve);
        SM9 sm9 = new SM9(sm9Curve);
        MasterKeyPair encryptMasterKeyPair = kgc.genEncryptMasterKeyPair();
        System.out.println(encryptMasterKeyPair.toString());
        PrivateKey encryptPrivateKey=kgc.getPrivateKey(encryptMasterKeyPair.getPrivateKey(), id_B);
        System.out.println("-------");
        System.out.println(encryptPrivateKey.toString());
        int macKeyByteLen = 32;
        boolean isBaseBlockCipher = false;
        ResultCipherText resultCipherText = sm9.encrypt(encryptMasterKeyPair.getPublicKey(), id_B, msg.getBytes(), isBaseBlockCipher, macKeyByteLen);
        System.out.println("加密后的密文 C=C1||C3||C2:");
        System.out.println(SM9Utils.toHexString(resultCipherText.toByteArray()));
        byte[] msgd = sm9.decrypt(resultCipherText, encryptPrivateKey, id_B, isBaseBlockCipher, macKeyByteLen);
        System.out.println("解密后的明文M':");
        System.out.println(new String(msgd));
        if (SM9Utils.byteEqual(msg.getBytes(), msgd)) {
            System.out.println(("加解密成功"));
        } else {
            System.out.println(("加解密失败"));
        }
    }

    @Test
    void test() throws Exception {
        SM9Service sm9Service = new SM9ServiceImpl();
        String id = "Bob";
        String msg ="acising";
        ResultCipherText resultCipherText = sm9Service.encrypt(id,msg);
//        System.out.println("resultCipherText:"+resultCipherText.toString());
//        String r = resultCipherText.toString();

//        ResultCipherText resultCipherText1 = ResultCipherText.fromByteArray(sm9Curve, r.getBytes());
//        System.out.println("resultCipherText1:"+resultCipherText1.toString());
        System.out.println("===");
        String s1 = sm9Service.decrypt(id, resultCipherText);
        System.out.println("s1:"+s1);

        Map<String, Object> register1 = sm9Service.register(id);
        Map<String, Object> register2 = sm9Service.register(id);
        System.out.println("register1："+register1.get("sysPubKey"));
        System.out.println("register2："+register2.get("sysPubKey"));

//        String s2 = sm9Service.decrypt(id, resultCipherText1);
//        System.out.println(s2);
        byte[] bytes = resultCipherText.toByteArray();
        String s3 = new String(bytes);
        System.out.println("---");
        System.out.println("s3:"+s3);

        byte[] bytes3 = s3.getBytes();

        System.out.println(bytes.equals(bytes3));

//        ResultCipherText resultCipherText2 = ResultCipherText.fromByteArray(sm9Curve, bytes3);
//        System.out.println("resultCipherText2:"+resultCipherText2.toString());
//        String s4 = sm9Service.decrypt(id, resultCipherText2);
//        System.out.println("s4:"+s4.toString());
        SM9Curve sm9Curve = new SM9Curve();
        System.out.println("============");
        String encoded = Base64.getEncoder().encodeToString(bytes); // 加密
        // 传输
        byte[] decoded = Base64.getDecoder().decode(encoded);       // 解密
        ResultCipherText resultCipherText3 = ResultCipherText.fromByteArray(sm9Curve, decoded);
        System.out.println("resultCipherText3:"+resultCipherText3);
        String s5 = sm9Service.decrypt(id, resultCipherText3);
        System.out.println("s5:"+s5);

    }



    @Test
    public void sm2enc() throws IOException {
        String plainText = "ILoveYou11";
        //SM3测试
        //生成密钥对
        //generateKeyPair();
        byte[] sourceData = plainText.getBytes();

        //下面的秘钥可以使用generateKeyPair()生成的秘钥内容
        // 国密规范正式私钥
        //String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        // 国密规范正式公钥
        //String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

        String prik = "4cf170068e9c47ebdb521fb9fc62c4a55a5773fb9da33b0acf8129e28d09d205";
        String pubk = "04aabda53043e8dcb86d42f690b61a4db869821dadf9f851ec3c5c43d0c8f95a6677fdba984afc3bb010a8436b1d17cefc2011a34e01e9e801124d29ffa928d803";
        String publicKey = "04BB34D657EE7E8490E66EF577E6B3CEA28B739511E787FB4F71B7F38F241D87F18A5A93DF74E90FF94F4EB907F271A36B295B851F971DA5418F4915E2C1A23D6E";
        String privatekey = "0B1CE43098BC21B8E82B5C065EDB534CB86532B1900A49D49F3C53762D2997FA";
        prik = privatekey;
        pubk = publicKey;
        System.out.println("加密: ");
        String cipherText = SM2EncDecUtils.encrypt(Util.hexToByte(pubk), sourceData);
        //cipherText = "0452ba81cf5119c9f29c81c2be9c4a49ad8c0a33ed899b60548d21a62971a8e994cafc0e9fbc710a0a220b055804bb890833b50ac04ec4e130a5db75338c0c1d49a52a6d373076a5db370564a5cebb5300f79877003c52adf49dac16370e51e14e0754110547bb3b";
        System.out.println(cipherText);
        System.out.println("解密: ");
        plainText = new String(SM2EncDecUtils.decrypt(Util.hexToByte(prik), Util.hexToByte(cipherText)));
        System.out.println(plainText);

    }


    @Test
    public void testSM2Service() throws IOException {
        SM2Service sm2Service = new SM2ServiceImpl();
        String s = "acising";
        String cipherText = sm2Service.encrypt(s);
        String plainText = sm2Service.decrypt(cipherText);
        System.out.println(plainText);
        SM2SignVO sign = sm2Service.sign(s);
        Boolean verify = sm2Service.verify(s, sign);
        System.out.println("验签是否成功:"+verify);
    }

    @Test
    public void testSM3(){
        SM3Service sm3Service = new SM3ServiceImpl();
        String s = sm3Service.getHash("acising");
        System.out.println(s);
    }

    @Test
    public void testSM4(){
        SM4Service sm4Service = new SM4ServiceImpl();
        String message = "acising";
        String s1 = sm4Service.encrypt_ECB(message);
        System.out.println("ECB加密:"+s1);
        String s2 = sm4Service.decrypt_ECB(s1);
        System.out.println("ECB解密:"+s2);
        String s3 = sm4Service.encrypt_CBC(message);
        System.out.println("CBC加密:"+s3);
        String s4 = sm4Service.decrypt_CBC(s3);
        System.out.println("CBC解密:"+s4);
    }

}
