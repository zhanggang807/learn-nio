package org.dean.learn.nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 演示使用多个缓冲区聚集后进行写操作
 * 运行完后请查看文件
 * Created by zhanggang3 on 2016/3/15.
 */
public class Marketing {

    private static final String DEMOGRAPHIC = "blahalah.txt";
    //"Leverage frictionless methodologies"

    public static void main(String[] args) throws Exception {
        int reps = 10;
        if (args.length > 0) {
            reps = Integer.parseInt(args[0]);
        }
//        reps = 20;
        FileOutputStream fos = new FileOutputStream(DEMOGRAPHIC);
        GatheringByteChannel gatherChannel = fos.getChannel();
        //Generate some brilliant marcom,er, repurposed content
        ByteBuffer[] bs = utterBS(reps);
        while (gatherChannel.write(bs) > 0) {
            int i;
            //空代码段
            //循环直到写操作返回0
        }
        System.out.println("Mindshare paradigms synergized to " + DEMOGRAPHIC);
        fos.close();
    }

    //---------------------------------------------------------------------
    //These are just representative; add your own
    private static String[] col1 = {
            "Aggregate", "Enable", "Leverage", "Facilitate", "Synergize",
            "Repurpose", "Strategize", "Reinvent", "Harness"
    };

    private static String [] col2 = { "cross-platform", "best-of-breed",
            "frictionless", "ubiquitous", "extensible", "compelling",
            "mission-critical", "collaborative", "integrated" };

    private static String [] col3 = { "methodologies", "infomediaries",
            "platforms", "schemas", "mindshare", "paradigms",
            "functionalities", "web services", "infrastructures" };

    private static String newline = System.getProperty("line.separator");
    //The Marcom-atic 9000

    private static ByteBuffer[] utterBS(int homMany) throws Exception{
        List<ByteBuffer> list = new LinkedList<ByteBuffer>();
        for (int i = 0; i < homMany; i++) {
            list.add(pickRandom(col1, " "));
            list.add(pickRandom(col2, " "));
            list.add(pickRandom(col3, newline));
        }
        ByteBuffer[] bufs = new ByteBuffer[list.size()];
        list.toArray(bufs);
        return bufs;
    }


    //the communication director
    private static Random rand = new Random();
    // pick one , make a buffer to hold it and the suffix, load it
    // with the byte equivalent of the strings (will not work properly for
    // non-Latin characters), then flip the loaded buffer so it's ready to be drained
    private static ByteBuffer pickRandom(String[] strings, String suffix) throws Exception{
        String string = strings[rand.nextInt(strings.length)];
        int total = string.length() + suffix.length();
        ByteBuffer buf = ByteBuffer.allocate(total);
        buf.put(string.getBytes("US-ASCII"));
        buf.put(suffix.getBytes("US-ASCII"));
        buf.flip();
        return buf;
    }



}
