package org.dean.learn.nio.channel;

import java.io.FileInputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 代码示例 3-6
 * Test channel transfer. This is a very simplistic concatenation
 * program. It takes a list of file names as arguments, opens each
 * in turn and transfers (copies) their content to the given ---
 * WriteableByteChannel (in this case, stdout).
 * Created by zhanggang3 on 2016/3/23.
 */
public class ChannelTransfer {

    public static void main(String[] args) throws Exception{
        if (args.length == 0) {
            System.err.println("Usage : filename ...");
            return;
        }
        catFiles(Channels.newChannel(System.out), args);
    }

    // Concatenate the content of each of the named files to
    // the given channnel. A very dumb version of 'cat'.
    private static void catFiles(WritableByteChannel target, String[] files) throws Exception{
        for (String file : files) {
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            channel.transferTo(0, channel.size(), target);//其它是输出到标准输出上
            channel.close();
            fis.close();
        }
    }

}
