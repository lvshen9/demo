package com.lvshen.demo.annotation.easyexport.utils;

import com.lvshen.demo.annotation.easyexport.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:21
 * @since JDK 1.8
 */
public class ScUtils {
    public static String getCurrentSystem() {
        String unKnown = "未知系统";
        try {
            String property = PropertiesUtil.getProperty("spring.application.name");
            return StringUtils.isEmpty(property) ? unKnown : property;
        } catch (Exception e) {
            return unKnown;
        }
    }
}
