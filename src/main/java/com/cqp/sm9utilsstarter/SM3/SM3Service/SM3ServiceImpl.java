package com.cqp.sm9utilsstarter.SM3.SM3Service;

import com.cqp.sm9utilsstarter.SM3.SM3Digestt;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM3ServiceImpl.java
 * @Description TODO
 * @createTime 2021年04月22日 13:42:00
 */
public class SM3ServiceImpl implements SM3Service{
    @Override
    public String getHash(String message) {

        byte[] md = new byte[32];
        byte[] msg = message.getBytes();
        SM3Digestt sm3 = new SM3Digestt();
        sm3.update(msg, 0, msg.length);
        sm3.doFinal(md, 0);
        String hash = new String(Hex.encode(md));
        return hash;

    }

}
