package com.lvshen.demo.annotation.easyexport.utils;

import com.lvshen.demo.catchexception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:32
 * @since JDK 1.8
 */
public class CurrentRuntimeContext {

    public static String getTenantId() {
        return getTenantId(false);
    }
    public static String getTenantId(boolean validate) {
        return getContextVal("x-tenant-id", validate);
    }
    private static String getContextVal(String headerName, boolean validate) {
        String value = ThreadLocalContext.getStringValue(headerName);
        if (value == null) {
            HttpServletRequest request = getRequest();
            if (request != null) {
                value = getRequest().getHeader(headerName);
            }
        }

        if (validate && StringUtils.isBlank(value)) {
            throw new BusinessException(500, "无法获取上下文[" + headerName + "]信息");
        } else {
            return value;
        }
    }
    public static HttpServletRequest getRequest() {
        if (ThreadLocalContext.exists("_ctx_request_")) {
            return (HttpServletRequest)ThreadLocalContext.get("_ctx_request_");
        } else {
            return RequestContextHolder.getRequestAttributes() != null ? ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest() : null;
        }
    }

    public static AuthUser getCurrentUser() {
        return getCurrentUser(false);
    }
    private static AuthUser getCurrentUser(boolean check) {
        AuthUser user = (AuthUser)ThreadLocalContext.get("_ctx_currentUser_");
        if (user == null) {
            HttpServletRequest request = getRequest();
            if (request == null) {
                if (check) {
                    throw new BusinessException("can't get [HttpServletRequest] object");
                }

                return null;
            }

            String headerString = request.getHeader("x-auth-user");
            if (StringUtils.isBlank(headerString)) {
                if (check) {
                    throw new BusinessException(401, "requestHeader[x-auth-user] is blank");
                }

                return null;
            }

            user = AuthUser.decode(headerString);
            ThreadLocalContext.set("_ctx_currentUser_", user);
        }

        return user;
    }
}
