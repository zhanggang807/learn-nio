package org.dean.learn.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

/**
 * 代码示例 3-9 显示了如何使用DatagramChannel发送请求到多个地址上的时间服务器
 * Request time service, per RFC 868. RFC 868(http://www.ietf.org/rfc/rfc0868.txt)
 * is a very simple time protocol whereby one system can request the current time
 * from another system. Most Linux, BSD and Solaris systems provide RFC 868 time
 * service on port 37. This simple program will inter-operate with those. The
 * National Institute of Standards and Technology (NIST) operates a public time
 * server at time.nist.gov.
 *
 * The RFC 868 protocol specifies a 32 bit unsigned value be sent, representing
 * the number of seconds sine Jan 1, 1900. The Java epoch begins on Jan 1, 1970
 * (same as Unix) so an adjustment is made by adding or subtracting 2,208,988,800
 * as appropriate. To avoid shifting and masking ,a four-byte slice of an eight-
 * byte buffer is used to send/receive. But getLong() is done on the full eight
 * bytes to get a long value;
 *
 * When run, this program will issue time requests to each hostname given on the
 * command line, then enter a loop to receive packets. Note that some requests or
 * replies may be lost, which means this code could block forever.
 *
 * Created by Dean on 2016/3/27.
 */
public class TimeClient {

    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;

    protected int port = DEFAULT_TIME_PORT;
    protected List remoteHosts;
    protected DatagramChannel channel;

    public TimeClient(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("Usage : [-p port] host ...");
        }
        parseArgs(args);
        this.channel = DatagramChannel.open();
    }

    protected InetSocketAddress receivePacket(DatagramChannel channel, ByteBuffer buffer) throws Exception{
        buffer.clear();
        // Receive an unsigned 32-bit, big-endian value
        return (InetSocketAddress) channel.receive(buffer);
    }

    // Send time requests to all the supplied hosts
    protected void (){

    }



    public static void main(String[] args) {
    }

}
