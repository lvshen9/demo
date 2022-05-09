package com.lvshen.demo.arithmetic.snowflake;

import io.netty.util.NetUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/10/30 15:54
 * @since JDK 1.8
 */

@ConditionalOnMissingBean(IdGenerator.class)
public class SnowFlakeGenerator implements IdGenerator {
    public SnowFlakeGenerator() {
    }

    @Override
    public String nextId() {
        return String.valueOf(next());
    }

    private static long workId;
    // private long baseTimestamp = DateUtils.parseDate("2018-01-01 00:00:00").getTime();

    private long baseTimestamp;

    {
        try {
            baseTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-09 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列号位数:7 支持每毫秒128个数字
     */
    private static long sequenceBits = 7L;
    /**
     * 节点，IP地址最后16位
     */
    private static long workerIdBits = 8L;
    /**
     * 时间位数，支持到2082年
     */
    // private long timeBits = 41L;

    private static final long TIMESTAMP_LEFT_SHIFT = sequenceBits + workerIdBits;
    private static final long WORKER_ID_LEFT_SHIFT = sequenceBits;

    /**
     * 上次生成ID的时间截
     */
    private volatile long lastTimestamp = -1L;
    /**
     * 毫秒内序列(0~127)
     */
    private volatile long sequence = 0L;

    static {
        try {
            byte[] address = InetAddress.getLocalHost().getAddress();
            workId = (long) (address[3] & 0xff);
            workId = workId << WORKER_ID_LEFT_SHIFT;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public synchronized long next() {

        long nowTimestamp = timeGen();
        if (nowTimestamp < lastTimestamp) {
            throw new IllegalStateException("系统时钟有误");
        }
        // 当前时间戳跟上次的时间戳一样，毫秒内生成序列
        if (lastTimestamp == nowTimestamp) {

            /* 生成序列的掩码，127 (1111111=0x7f=127) */
            long sequenceMask = 127L;
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                nowTimestamp = timeGen();
                while (nowTimestamp <= lastTimestamp) {
                    nowTimestamp = timeGen();
                }
                lastTimestamp = nowTimestamp;
            }

        } else {
            sequence = 0;
            lastTimestamp = nowTimestamp;
        }
        //Date formatDate = DateTimeUtils.getFormatDate("2027-09-03");
        //nowTimestamp = formatDate.getTime();
        //同一服务器上面的不同应用可能生成同一id
        long id2Long = ((nowTimestamp - baseTimestamp) << TIMESTAMP_LEFT_SHIFT) | workId | sequence;

        return id2Long;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
