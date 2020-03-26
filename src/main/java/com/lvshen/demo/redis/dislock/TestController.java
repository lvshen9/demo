package com.lvshen.demo.redis.dislock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daify
 * @date 2019-08-11
 */
@RestController
public class TestController {

    @Autowired
    private RedisLockUtils utils;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "test/{key}",method = RequestMethod.GET)
    public String test(@PathVariable("key")String key) {
        RedisLock lock = utils.getLock(key, 10000L, 5000L);

        return lock == null ? "获取失败" : "获取成功";
    }

    @RequestMapping(value = "nuTest",method = RequestMethod.GET)
    public String nuTest() {
        redisTemplate.delete("redisLock");
        redisTemplate.delete("redisLock2");
        redisTemplate.delete("redisLock3");
        return "成功";
    }


}
