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

    /**
     *
     * @param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("ratelimiter")
    @RateLimiter(needUserLimit = true)
    public String testLimit() {
        return "限流注解测试专用";
    }

}
