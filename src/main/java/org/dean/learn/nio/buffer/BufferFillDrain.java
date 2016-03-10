package org.dean.learn.nio.buffer;

import java.nio.CharBuffer;

/**
 * 学习缓冲区的填充和释放 代码清单2.1
 * Created by zhanggang3 on 2016/3/10.
 */
public class BufferFillDrain {

    private static String[] strings = {
            "A random string value",
            "The product of an infinite number of monkyes",
            "Hey hey we're the Monkees",
            "Opening act for the Mokees: Jimi Hendrix",
            "'Scuse me while I kikss the fly",
            "Help me! Help me!"
    };

    private static int index;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CharBuffer buffer = CharBuffer.allocate(100);
        while (fillBuffer(buffer)) {
            System.out.println("---- two ----");
            buffer.flip();
            drainBuffer(buffer);
            buffer.clear();
        }
        System.out.print("耗时:");
        System.out.print((System.currentTimeMillis() - start));
        System.out.print((System.currentTimeMillis() - start) / 1000);
        System.out.println("");
    }

    /***
     *  释放缓冲区
     */
    private static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()){
            System.out.print(buffer.get());
        }
        System.out.println(" ");
    }

    /**
     *  填充缓冲区
     */
    private static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length){
            return false;
        }
        String string = strings[index++];
        for (int i = 0; i < string.length(); i++){
            buffer.put((char)string.charAt(i));
        }
        System.out.println("---- one ----");
        return true;
    }

}
