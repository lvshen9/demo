package com.lvshen.demo.configure;

import cn.hutool.core.lang.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/10/25 17:56
 * @since JDK 1.8
 */
@Configuration
public class BeanConfigurer {
    @Bean("snowflake")
    Snowflake snowflakeIdWorker() {
        return new Snowflake(1, 1);
    }
}
