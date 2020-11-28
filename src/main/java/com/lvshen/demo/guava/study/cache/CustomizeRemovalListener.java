package com.lvshen.demo.guava.study.cache;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-25 16:57
 * @since JDK 1.8
 */
public class CustomizeRemovalListener implements RemovalListener<String, Province> {
    @Override
    public void onRemoval(RemovalNotification<String, Province> removalNotification) {
        String reason = String.format("key=%s,value=%s,reason=%s", removalNotification.getKey(), removalNotification.getValue(), removalNotification.getCause());
        System.out.println(reason);
    }
}
