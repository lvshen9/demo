package com.lvshen.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/1 18:10
 * @since JDK 1.8
 */
public class NioServerTest {
    static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//非阻塞模式
        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("NIO启动");

        //selector多路复用
        Selector selector = Selector.open();

        while (true) {

            SocketChannel newConnection = serverSocketChannel.accept();

            if (newConnection == null) {
                continue;
            }

            newConnection.configureBlocking(false);

            //当有数据传输过来的时候，咱们再去处理---让selector去帮我们监控新连接OP_READ事件
            newConnection.register(selector, SelectionKey.OP_READ);

            selector.select();
            Set<SelectionKey> eventKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = eventKeys.iterator();
            while (true) {
                SelectionKey event = iterator.next();
                if (event.isReadable()) {
                    threadPool.submit(() -> {

                        SocketChannel connection = (SocketChannel) event.channel();

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        try {
                            connection.read(byteBuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byteBuffer.flip();//转换为读取模式

                        System.out.println(new String(byteBuffer.array()));
                    });
                }
            }
        }

    }
}
