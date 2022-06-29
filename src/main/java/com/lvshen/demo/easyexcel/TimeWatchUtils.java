package com.lvshen.demo.easyexcel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-6-29 14:04
 * @since JDK 1.8
 */
@Slf4j
public class TimeWatchUtils {
    public static StopWatch start() {
        return StopWatch.createStarted();
    }

    public static void stop(StopWatch sw, String funcName) {
        sw.stop();
        log.info("方法【{}】耗时：{} ms", funcName, sw.getTime());
    }
}
