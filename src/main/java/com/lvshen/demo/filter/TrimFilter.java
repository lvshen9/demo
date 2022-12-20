package com.lvshen.demo.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 15:41
 * @since JDK 1.8
 */
public class TrimFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        //自定义TrimRequestWrapper，在这里实现参数去空
        TrimRequestWrapper requestWrapper = new TrimRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, httpServletResponse);
    }
}
