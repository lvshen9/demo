package com.lvshen.demo.function;

import java.util.function.Consumer;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-11-29 11:18
 * @since JDK 1.8
 */
public interface PresentOrElseHandler<T extends Object> {
    void presentOrElseHandle(Consumer<? super T> action, Runnable emptyAction);
}
