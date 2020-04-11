package com.lvshen.demo.autoidempotent;


import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/11 11:29
 * @since JDK 1.8
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public ServerResponse testIdempotence() {
        return ServerResponse.success("testIdempotence: success");
    }
}
