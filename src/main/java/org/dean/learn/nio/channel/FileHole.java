package org.dean.learn.nio.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 代码示例 3-3 可能会产生一个文件空洞
 * Created by zhanggang3 on 2016/3/15.
 */
public class FileHole {

    public static void main(String[] args) throws IOException {
        //create a temp file, open for writing, and set a FileChannel
        File temp = File.createTempFile("holy", null);
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        FileChannel channel = file.getChannel();
        //create a working buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        putData(0, byteBuffer, channel);
        putData(5000000, byteBuffer, channel);
        putData(50000, byteBuffer, channel);
        // size will report the largest position written, but
        // there are two holes in this file. The file will
        // not consume 5MB on disk (unless the filesystem is
        // extremely brain-damaged)
        System.out.println("Wrote temp file '" + temp.getPath() + "', size = " + channel.size());
        channel.close();
        file.close();
    }

    private static void putData(int position, ByteBuffer buffer, FileChannel channel) throws IOException{
        String string = "*<-- location " + position;
        buffer.clear();
        buffer.put(string.getBytes("US-ASCII"));
        buffer.flip();
        channel.position(position);
        channel.write(buffer);
    }

}


/**
 * 当磁盘上一个文件的分配空间小于它的文件大小时会出现“文件空洞”。
 * 对于内容稀疏的文件，大多数现代文件系统只为实际写入的数据分配磁
 * 盘空间（更准确地说，只为那些写入数据的文件系统页分配空间）。假
 * 如数据被写入到文件中非连续的位置上，这将导致文件出现在逻辑上不
 * 包含数据的区域（即“空洞”）
 *
 * 如果该文件被顺序读取的话，所有空洞都会被“0”填充但不占用磁盘空
 * 间。读取该文件的进程会看到5,000,021个字节，大部分字节都以“0”表
 * 示。试试在该文件上运行strings命令，看看您会得到什么。再试试将文件
 * 大小的值提高到50或100MB，看看您的全部磁盘空间消耗以及顺序扫描该文
 * 件所需时间会发生何种变化（前者不会改变，但是后者将有非常大的增加）。
 */
