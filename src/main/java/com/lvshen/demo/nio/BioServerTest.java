package com.lvshen.demo.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8082);

        while (true) {
            Socket accept = socket.accept();//可能出现阻塞
            threadPool.submit(() -> {
                byte[] request = new byte[1024];

                accept.getInputStream().read(request);//可能出现阻塞
                Thread.sleep(60000);
                System.out.println(new String(request));
                return  request.toString();
            });
        }
    }
}
