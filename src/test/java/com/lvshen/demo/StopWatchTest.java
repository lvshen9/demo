package com.lvshen.demo;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-6-22 16:28
 * @since JDK 1.8
 */
public class StopWatchTest {

    @Test
    public void test() throws InterruptedException {
        // 创建一个 StopWatch 实例并开始计时
        StopWatch sw = StopWatch.createStarted();

        // 休眠1秒
        Thread.sleep(1000);

        sw.stop();
        // 1002ms
        System.out.printf("耗时：%dms.\n", sw.getTime());
    }
}
