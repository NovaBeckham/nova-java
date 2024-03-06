package com.nova.service;

import java.util.Map;

@SuppressWarnings("all")
public interface RedisService {

    void set(String key, Object value, long time);

    void set(String key, Object value);

    Object get(String key);

    Boolean expire(String key, long time);

    Long incrExpire(String key, long time);

    Object hGet(String key, String hashKey);

    Boolean hSet(String key, String hashKey, Object value, long time);

    Map<Object, Double> zAllScore(String key);

}

