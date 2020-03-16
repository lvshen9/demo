package com.lvshen.demo.annotation;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/16 14:37
 * @since JDK 1.8
 */
@Component
public class BeanUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setFieldValueForCol(Collection col) throws Exception {
        Class<?> clazz = col.iterator().next().getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> cache = new HashMap<>();
        for (Field needField : fields) {
            NeedSetValue needSetValue = needField.getAnnotation(NeedSetValue.class);
            if (needSetValue == null) {
                continue;
            }
            needField.setAccessible(true);

            Object bean = this.applicationContext.getBean(needSetValue.beanClass());
            Method method = needSetValue.beanClass().getMethod(needSetValue.method(), clazz.getDeclaredField(needSetValue.param()).getType());

            Field paramField = clazz.getDeclaredField(needSetValue.param());
            paramField.setAccessible(true);

            Field targetField = null;
            boolean needInnerField = StringUtils.isNotEmpty(needSetValue.targetFiled());

            String keyPrefix = needSetValue.beanClass() + "-" + needSetValue.method() + "-" + needSetValue.targetFiled() + "-";

            for (Object o : col) {
                Object paramValue = paramField.get(o);
                if (paramValue == null) {
                    continue;
                }

                Object value = null;
                String key = keyPrefix + paramValue;

                if (cache.containsKey(key)) {
                    value = cache.get(key);
                } else {
                    value = method.invoke(bean, paramValue);

                    if (needInnerField) {
                        if (value != null) {
                            if (targetField == null) {
                                targetField = value.getClass().getDeclaredField(needSetValue.targetFiled());
                                targetField.setAccessible(true);
                            }
                            value = targetField.get(value);
                        }
                    }
                    cache.put(key, value);
                }
                needField.set(o,value);
            }
        }
    }


}
