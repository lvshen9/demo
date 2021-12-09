package com.lvshen.demo.function;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-11-29 10:51
 * @since JDK 1.8
 */
public class ValidateUtils {

    public static ExceptionFunciton isTrue(boolean flag) {
        return (errorMessage -> {
            if (flag) {
                throw new RuntimeException(errorMessage);
            }
        });
    }

    public static BranchHandler isTrueOrFalse(boolean flag) {
        return ((trueHandle, falseHandle) -> {
            if (flag) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        });
    }

    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str) {


        return (consumer, runnable) -> {
            if (str == null || str.length() == 0) {
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }
}
