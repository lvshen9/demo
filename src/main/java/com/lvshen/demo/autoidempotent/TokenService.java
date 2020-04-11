package com.lvshen.demo.autoidempotent;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 21:00
 * @since JDK 1.8
 */
public interface TokenService {
    /**
     * 创建token
     * @return
     */
    String createToken();

    /**
     * 检验token
     * @param request
     * @return
     */
    boolean checkToken(HttpServletRequest request) throws Exception;
}
