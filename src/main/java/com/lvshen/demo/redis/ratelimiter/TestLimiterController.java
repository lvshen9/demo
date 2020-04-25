package com.lvshen.demo.redis.ratelimiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/25 12:38
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class TestLimiterController {

    private static final String MESSAGE = "{\"code\":\"400\",\"msg\":\"FAIL\",\"desc\":\"触发限流\"}";

    AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 10s限制请求5次
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("ratelimiter")
    @RateLimiter(key = "ratedemo:1.0.0", limit = 5, expire = 10, message = MESSAGE)
    public String sendPayment(HttpServletRequest request) throws Exception {
        int count = atomicInteger.incrementAndGet();
        return "正常请求" + count;
    }

}
