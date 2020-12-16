package com.lvshen.demo.catchexception;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Description: 统一接口返回
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-16 10:27
 * @since JDK 1.8
 */
//@RestControllerAdvice(basePackages = "com.xxx.controller")
public class ControllerResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 支持所有的返回值类型
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        } else {
            // 所有没有返回　Result　结构的结果均认为是成功的
            return success(body);
        }
    }

    /**
     * 设置成功返回结果
     */
    private <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage("SUCCESS");
        result.setData(data);
        return result;
    }
}