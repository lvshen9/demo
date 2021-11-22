package com.lvshen.demo.concurrent;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-11-19 14:15
 * @since JDK 1.8
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class AtomicTest {

    private static AtomicLong atomicLong = new AtomicLong();
    private static LongAdder longAdder = new LongAdder();

    @Benchmark
    @Threads(10)
    public void atomicLongAdd() {
        atomicLong.getAndIncrement();
    }

    @Benchmark
    @Threads(10)
    public void longAdderAdd() {
        longAdder.increment();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(AtomicTest.class.getSimpleName()).build();
        new Runner(options).run();
    }
}
