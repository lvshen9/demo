package com.lvshen.demo.arithmetic.tradno;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/23 9:54
 * @since JDK 1.8
 */
public class NumberUtils {
    public NumberUtils() throws ParseException {
    }

    public String nextId() {
        return String.valueOf(next());
    }

    private static long WORKER_ID;
    private static long PORT_ID;

    private long baseTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2005-06-09").getTime();

    /** 序列号位数:7 支持每毫秒128个数字 */
    private static long sequenceBits = 7L;
    /** 节点，IP地址最后16位 */
    private static long workerIdBits = 8L;
    /** 端口最后2位 */
    private static long portBits = 7L;

    private static final long TIMESTAMP_LEFT_SHIFT = sequenceBits + portBits + workerIdBits;
    private static final long WORKER_ID_LEFT_SHIFT = sequenceBits + portBits;
    private static final long PORT_ID_LEFT_SHIFT = sequenceBits;


    /** 上次生成ID的时间截 */
    private volatile long lastTimestamp = -1L;
    /** 毫秒内序列(0~127) */
    private volatile long sequence = 0L;

    static {
        long port = 80;
        byte[] address = new byte[0];
        try {
            address = InetAddress.getLocalHost().getAddress();
            ServerSocket socket = new ServerSocket(0);
            port = socket.getLocalPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        WORKER_ID = (long) (address[3] & 0xff);
        WORKER_ID = WORKER_ID << WORKER_ID_LEFT_SHIFT;

        if(port > 99) {
            // 最后2位
            port = port % 100;
        }
        PORT_ID = (long) (port);
        PORT_ID = PORT_ID << PORT_ID_LEFT_SHIFT;
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
        return ((nowTimestamp - baseTimestamp) << TIMESTAMP_LEFT_SHIFT) | WORKER_ID | PORT_ID | sequence;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(new NumberUtils().nextId());
    }
}
