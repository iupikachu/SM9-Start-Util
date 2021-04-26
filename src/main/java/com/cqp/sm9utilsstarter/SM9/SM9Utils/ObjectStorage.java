package com.cqp.sm9utilsstarter.SM9.SM9Utils;

import com.cqp.sm9utilsstarter.SM9.SM9Utils.ExpiryMap;
import com.cqp.sm9utilsstarter.SM9.curve.SM9Curve;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 相关静态存储
 * @author: SongQijie
 * @date: Created in 12:23 上午 2020/4/20
 * @version: 1.0
 **/
public class ObjectStorage {
    public static Map<String, SM9Curve> PKGPARAMETERS = new ConcurrentHashMap<String, SM9Curve>();

    public static ExpiryMap<String, Object> USERDOMAIN = ExpiryMap.getInstance();

    public static String PASSWORD = "acising";

}
