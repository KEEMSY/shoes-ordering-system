package com.shoes.ordering.system.common.redis.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // string (opsForValue)
    public void setStringOps(String key, String value, long ttl, TimeUnit unit){
        redisTemplate.opsForValue().set(key, value, ttl, unit);
    }

    public String getStringOps(String key){
        return redisTemplate.opsForValue().get(key);
    }

    // list (opsForList)
    public void setListOps(String key, List<String> values){
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    public List<String> getListOps(String key){
        Long len = redisTemplate.opsForList().size(key);
        return len == 0 ? new ArrayList<>() : redisTemplate.opsForList().range(key, 0, len-1);
    }

    // hash (opsForHash)
    public void setHashOps(String key, HashMap<String, String> value){
        redisTemplate.opsForHash().putAll(key, value);
    }

    public String getHashOps(String key, String hashKey){
        return redisTemplate.opsForHash().hasKey(key, hashKey) ? (String) redisTemplate.opsForHash().get(key, hashKey) : new String();
    }

    // set (opsForSet)
    public void setSetOps(String key, String... values){
        redisTemplate.opsForSet().add(key, values);
    }

    public Set<String> getSetOps(String key){
        return redisTemplate.opsForSet().members(key);
    }

}