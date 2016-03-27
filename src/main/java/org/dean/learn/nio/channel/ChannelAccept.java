package org.dean.learn.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 代码示例 3-7 演示了如何使用一个非阻塞的accept()方法
 * Test nonblocking accept() using ServerSocketChannel.
 * Start this program, then "telnet localhost 1234" to
 * connect to it.
 * Created by zhanggang3 on 2016/3/26.
 */
public class ChannelAccept {

    public static final String GREETING = "Hello I must be going. \r\n";

    public static void main(String[] args) throws Exception{
        int port = 1234; //default
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        while (true){
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                // no connections, snooze a while
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from :" + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }

}
