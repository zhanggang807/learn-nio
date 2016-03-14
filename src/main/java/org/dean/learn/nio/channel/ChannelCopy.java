package org.dean.learn.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 代码示例 3-1
 * 测试通道间数据复制
 * Created by zhanggang3 on 2016/3/14.
 */
public class ChannelCopy {

    public static final int CAPACITY = 10;//缓冲区容量

    /**
     * 这段代码从标准输入复制数据到标准输出
     * 就像cat命令一样，但是没有任何选项
     * @param String[] args
     */
    public static void main(String[] args) throws IOException {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);
        channelCopy1(source, dest);
        //调用channelCopy2也可以 channelCopy2(source, dest);
        source.close();
        dest.close();
    }

    /**
     * 这段代码从源通道复制数据并写到目的通道直到在遇到源通道上的EOF符号
     * 此实现在临时缓冲区上使用compact()方法来打包数据，如果缓冲没有完全
     * 排干的话。这会导致数据拷贝，但是最小化系统调用。它也需要 一个清除
     * 的循环确保所有数据发送完成
     * @param source
     * @param dest
     */
    private static void channelCopy1(ReadableByteChannel src, WritableByteChannel dest) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocateDirect(CAPACITY);//分配直接内存
        int count = 0;
        System.out.println("\n---before---" + buffer.toString());
        while (src.read(buffer) != -1) {//读满缓冲区并且还没有读到结束符
            System.out.println("\n---one---" + buffer.toString());
            //准备缓冲区以便排干
            buffer.flip();//limit = position; position = 0; mark = -1;
            System.out.println("\n---flip---" + buffer.toString());
            //向通道写，可能会阻塞
            dest.write(buffer);//先向通道写一个缓冲区的内容
            //如果部分传输则，继续传送剩下的
            //如果缓冲是空的，相当于clear();
            System.out.println("\n---write---" + buffer.toString());
            buffer.compact();//这句话有点不明白哦。为什么要压缩一下呢？
            System.out.println("\n---compact---" + buffer.toString());

            count++;
        }
        //EOF will leave buffer in fill state
        //这句怎么翻译？？
        buffer.flip();
        //确保缓冲区被彻底排干
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
        System.out.println("two.count = " + count);
        /**
         * 根 据这些输入信息就可以了解运行过程
         */

    }

    /**
     * 这个方法做同样的复制操作,但是在写数据之前确保临时缓冲是空的。
     * 这不需要数据拷贝，但是会造成更多的系统调用。
     * no post-loop cleanup is needed because the buffer will be empty when the loop is exited.
     * @param src
     * @param dest
     * @throws IOException
     */
    private static void channelCopy2 (ReadableByteChannel src, WritableByteChannel dest) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocateDirect(CAPACITY);//同样分配直接内存
        int count = 0;
        while (src.read (buffer) != -1) {
            System.out.println("\n---one---" + buffer.toString());
            //准备排干缓冲
            buffer.flip();
            //确保缓冲被全部排干
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }
            //确保缓冲是空的，为填充做好准备
            buffer.clear();
        }
        System.out.println("two.count = " + count);
    }


}
