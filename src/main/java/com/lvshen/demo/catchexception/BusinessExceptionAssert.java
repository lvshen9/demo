package com.lvshen.demo.catchexception;

import org.springframework.lang.Nullable;


/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-16 10:30
 * @since JDK 1.8
 */
public class BusinessExceptionAssert {

    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new BusinessException(String.valueOf(errorMessage));
        }
    }

    //无code
    /*public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1) {
        if (!b) {
            throw new BusinessException(Strings.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2) {
        if (!b) {
            throw new BusinessException(Strings.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2, @Nullable Object p3) {
        if (!b) {
            throw new BusinessException(Strings.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3}));
        }
    }

    public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4) {
        if (!b) {
            throw new BusinessException(Strings.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3, p4}));
        }
    }

    //需要传入code
    public static void checkArgument(boolean b, int code, @Nullable String errorMessageTemplate, @Nullable Object p1) {
        if (!b) {
            throw new BusinessException(code, Strings.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static void checkArgument(boolean b, int code, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2) {
        if (!b) {
            throw new BusinessException(code, Strings.lenientFormat(errorMessageTemplate, new Object[]{p1, p2}));
        }
    }

    public static void checkArgument(boolean b, int code, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2, @Nullable Object p3) {
        if (!b) {
            throw new BusinessException(code, Strings.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3}));
        }
    }

    public static void checkArgument(boolean b, int code, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4) {
        if (!b) {
            throw new BusinessException(code, Strings.lenientFormat(errorMessageTemplate, new Object[]{p1, p2, p3, p4}));
        }
    }*/


    public static void checkArgument(boolean expression, int code, @Nullable Object errorMessage) {
        if (!expression) {
            throw new BusinessException(code, String.valueOf(errorMessage));
        }
    }

    //无code

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new BusinessException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, int code, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new BusinessException(code, String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }
}