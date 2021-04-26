package com.cqp.sm9utilsstarter.gm.sm4;

import com.cqp.sm9utilsstarter.gm.GMProvider;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM4.java
 * @Description TODO
 * @createTime 2021年03月30日 10:11:00
 */
public class SM4 {
    public static final int KEY_BYTE_LENGTH = 16;
    public static final String ALGORITHM_NAME = "SM4";

    private SM4() {
    }

    public static byte[] ecbCrypt(boolean isEncrypt, byte[] key, byte[] data, int offset, int length) throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "SM4");
            Cipher cipher = Cipher.getInstance(getAlgorithm() + "/ECB/PKCS5Padding", getCipherProvider());
            if (isEncrypt) {
                cipher.init(1, secretKey);
            } else {
                cipher.init(2, secretKey);
            }

            byte[] cipherText = cipher.doFinal(data);
            return cipherText;
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException var8) {
            throw new Exception("SM4 ECB crypt failed." + var8.getMessage());
        }
    }

    public static Provider getCipherProvider() {
        return GMProvider.getProvider();
    }

    public static String getAlgorithm() {
        return "SM4";
    }
}
