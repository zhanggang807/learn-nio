package org.dean.learn.nio.view;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * 代码示例 2.2
 * 测试一个字节缓冲区转换为一个字符缓冲区
 * Created by zhanggang3 on 2016/3/12.
 */
public class BufferCharView {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();

        //Load the ByteBuffer with some bytes
        byteBuffer.put(0, (byte) 0);
        byteBuffer.put(1, (byte) 'H');
        byteBuffer.put(2, (byte) 0);
        byteBuffer.put(3, (byte) 'i');
        byteBuffer.put(4, (byte) 0);
        byteBuffer.put(5, (byte) '!');
        byteBuffer.put(6, (byte) 0);

        println(byteBuffer);
        println(charBuffer);
    }

    /**
     * Print info about a buffer
     * @param buffer
     */
    private static void println(Buffer buffer) {
        System.out.println("pos = " + buffer.position() + ", limit = " + buffer.limit()
        + ", capacity = " + buffer.capacity() + ",: '" + buffer.toString() + "'");
    }

    /**
     * 程序运行结果
     * pos = 0, limit = 7, capacity = 7,: 'java.nio.HeapByteBuffer[pos=0 lim=7 cap=7]'
     * pos = 0, limit = 3, capacity = 3,: 'Hi!'
     */

}
