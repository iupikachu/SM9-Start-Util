package com.cqp.sm9utilsstarter.SM3.SM3Service;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM3Service.java
 * @Description TODO
 * @createTime 2021年04月22日 13:35:00
 */
public interface SM3Service {
    // 获得64位杂凑值
    String getHash(String message);
}
