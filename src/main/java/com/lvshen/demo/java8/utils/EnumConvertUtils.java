package com.lvshen.demo.java8.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 18:55
 * @since JDK 1.8
 */
@Slf4j
public class EnumConvertUtils {
    private static final String EMPTY_STRING = StringUtils.EMPTY;

    /**
     * 枚举转map结合value作为map的key,description作为map的value
     *
     * @param enumT
     * @param methodNames
     * @return enum
     */
    public static <T> Map<Object, String> enumToMap(Class<T> enumT,
                                                    String... methodNames) {
        Map<Object, String> enumMap = Maps.newHashMap();
        if (!enumT.isEnum()) {
            return enumMap;
        }
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return enumMap;
        }
        int count = methodNames.length;
        /**默认接口value方法*/
        String valueMethod = "getValue";
        /**默认接口typeName方法*/
        String desMethod = "getDesc";
        /**扩展方法*/
        if (count >= SystemConstant.ONE_INT && StringUtils.isNotBlank(methodNames[0])) {
            valueMethod = methodNames[0];
        }
        if (count == SystemConstant.TWO_INT && StringUtils.isNotBlank(methodNames[1])) {
            desMethod = methodNames[1];
        }

        for (T anEnum : enums) {
            try {
                /**获取value值*/
                Object resultValue = getMethodValue(valueMethod, anEnum);
                if (EMPTY_STRING.equals(resultValue)) {
                    continue;
                }
                /**获取typeName描述值*/
                Object resultDes = getMethodValue(desMethod, anEnum);
                /**如果描述不存在获取属性值*/
                if (EMPTY_STRING.equals(resultDes)) {
                    resultDes = anEnum;
                }
                enumMap.put(resultValue, resultDes + EMPTY_STRING);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return enumMap;
    }

    /**
     * 根据反射，通过方法名称获取方法值，忽略大小写的
     *
     * @param methodName
     * @param obj
     * @param args
     * @return return value
     */
    private static <T> Object getMethodValue(String methodName, T obj, Object... args) {
        Object result = EMPTY_STRING;
        try {
            /********************************* start *****************************************/
            /**获取方法数组，这里只要共有的方法*/
            Method[] methods = obj.getClass().getMethods();
            if (methods.length <= 0) {
                return result;
            }
            Method method = null;
            for (int i = 0, len = methods.length; i < len; i++) {
                /**忽略大小写取方法*/
                if (methods[i].getName().equalsIgnoreCase(methodName)) {
                    /**如果存在，则取出正确的方法名称*/
                    method = methods[i];
                    break;
                }
            }
            /*************************** end ***********************************************/
            if (method == null) {
                return result;
            }
            /**方法执行*/
            result = method.invoke(obj, args);
            if (result == null) {
                result = EMPTY_STRING;
            }
            /**返回结果*/
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过value值获取对应的描述信息
     *
     * @param value
     * @param enumT
     * @param methodNames
     * @return enum description
     */
    public static <T> Object getEnumDescriotionByValue(Object value, Class<T> enumT, String... methodNames) {
        /**不是枚举则返回EMPTY_STRING*/
        if (!enumT.isEnum()) {
            return EMPTY_STRING;
        }
        /**获取枚举的所有枚举属性，似乎这几句也没啥用，一般既然用枚举，就一定会添加枚举属性*/
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return EMPTY_STRING;
        }
        int count = methodNames.length;
        /**默认获取枚举value方法，与接口方法一致*/
        String valueMethod = "getValue";
        /**默认获取枚举getTypeName方法*/
        String desMethod = "getDesc";
        if (count >= SystemConstant.ONE_INT && !EMPTY_STRING.equals(methodNames[0])) {
            valueMethod = methodNames[0];
        }
        if (count == SystemConstant.TWO_INT && !EMPTY_STRING.equals(methodNames[1])) {
            desMethod = methodNames[1];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T t = enums[i];
            try {
                /**获取枚举对象value*/
                Object resultValue = getMethodValue(valueMethod, t);
                if (resultValue.toString().equals(value + EMPTY_STRING)) {
                    /**存在则返回对应描述*/
                    Object resultDes = getMethodValue(desMethod, t);
                    return resultDes;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return EMPTY_STRING;
    }

    /**
     * 转换成List
     *
     * @param enumT
     * @param methodNames
     * @return List<String>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<String> getEnumToList(Class<T> enumT, String... methodNames) {
        Map<Object, String> map = enumToMap(enumT);
        List<String> result = new ArrayList(map.values());
        return result;
    }

    /**
     * 通过枚举value或者自定义值及方法获取枚举属性值
     *
     * @param value
     * @param enumT
     * @param methodNames
     * @return enum key
     */
    public static <T> String getEnumKeyByValue(Object value, Class<T> enumT, String... methodNames) {
        if (!enumT.isEnum()) {
            return EMPTY_STRING;
        }
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return EMPTY_STRING;
        }
        int count = methodNames.length;
        /**默认方法*/
        String valueMathod = "getValue";
        /**独立方法*/
        if (count >= 1 && !EMPTY_STRING.equals(methodNames[0])) {
            valueMathod = methodNames[0];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T tobj = enums[i];
            try {
                Object resultValue = getMethodValue(valueMathod, tobj);
                /**存在则返回对应值*/
                if (resultValue != null && resultValue.toString().equals(value + EMPTY_STRING)) {
                    return tobj + EMPTY_STRING;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return EMPTY_STRING;
    }
}
