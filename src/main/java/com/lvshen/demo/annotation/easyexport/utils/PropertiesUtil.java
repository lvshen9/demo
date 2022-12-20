package com.lvshen.demo.annotation.easyexport.utils;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:29
 * @since JDK 1.8
 */
public class PropertiesUtil {
    private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
    private static final String DEFAULT_PROPERTIES="application.properties";

    /**
     * 获取properties属性值
     * @param str
     * @return
     */
    public static String getProperty(String str){
        try {
            Resource resource = new ClassPathResource(DEFAULT_PROPERTIES);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            return props.getProperty(str);
        }catch (IOException e){
            logger.info(e.getMessage());
        }
        return null;
    }

}
