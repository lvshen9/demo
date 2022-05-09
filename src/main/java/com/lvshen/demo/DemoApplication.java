package com.lvshen.demo;

import com.lvshen.demo.autoidempotent.AutoIdempotentInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.lvshen.demo.authenticatedstreams.mapper"})
public class DemoApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
/*
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口幂等性拦截器
        registry.addInterceptor(autoIdempotentInterceptor());
        super.addInterceptors(registry);
    }

    @Bean
    public AutoIdempotentInterceptor autoIdempotentInterceptor() {
        return new AutoIdempotentInterceptor();
    }

}
