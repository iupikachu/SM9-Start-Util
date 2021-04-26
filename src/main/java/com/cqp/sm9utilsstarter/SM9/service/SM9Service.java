package com.cqp.sm9utilsstarter.SM9.service;

import com.cqp.sm9utilsstarter.SM9.result.ResultCipherText;

import java.util.Map;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9Service.java
 * @Description TODO
 * @createTime 2021年04月07日 08:41:00
 */

public interface SM9Service {

    // 加密服务
    ResultCipherText encrypt(String IBE_Identify,String msg) throws Exception;

    // 解密服务
    String decrypt(String IBE_Identify,ResultCipherText resultCipherText) throws Exception;

    // 注册
    Map<String, Object> register(String IBE_Identify) throws Exception;

}
