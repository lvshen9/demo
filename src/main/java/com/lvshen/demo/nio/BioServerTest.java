package com.lvshen.demo.nio;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.UUID;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/1 18:02
 * @since JDK 1.8
 */
public class BioServerTest {
    static ExecutorService threadPool = Executors.newCachedThreadPool();
    List<String> lists = Lists.newCopyOnWriteArrayList();
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8082);

        while (true) {
            Socket accept = socket.accept();//可能出现阻塞
            threadPool.submit(() -> {
                byte[] request = new byte[1024];

                accept.getInputStream().read(request);//可能出现阻塞
                Thread.sleep(60000);
                System.out.println(new String(request));
                return request.toString();
            });
        }
    }

    @Test
    public void testAsync() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                lists.add("hello");
            }).start();
        }
    }


}
