package com.lvshen.demo.autoidempotent;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 21:23
 * @since JDK 1.8
 */
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Resource
    private AutoIdempotentInterceptor autoIdempotentInterceptor;

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoIdempotentInterceptor);
        super.addInterceptors(registry);
    }
}
