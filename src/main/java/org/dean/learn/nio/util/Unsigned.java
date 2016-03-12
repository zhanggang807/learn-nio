package org.dean.learn.nio.util;

import java.nio.ByteBuffer;

/**
 * 代码示例 2.3
 * 向ByteBuffer 对象 中获取和存放无符号值 的工具类
 *
 * 这里所有的方法 都 是静态的，并且带有一个ByteBuffer参数
 * 由于java不提供无符号原始类型，每个从缓冲区中读出的无符号值被升到比它大的下一个基本数据类型中。
 * getUnsineddByte()返回 一个short类型， getUnsignedShort()
 * 返回一个int类型，面getUnsignedInt()返回 一个long型
 * 由于没有基本类型来存储返回的数据 ，因此没有getUnsignedLong()//注long之上再没有数据类型了
 * 如果需要，返回BigInteger的方法 可以执行。
 * 同样，存放方法要取一个大于它们所分配的类型的值。
 * putUnsignedByte取一个short开进参数，等等。
 * Created by zhanggang3 on 2016/3/12.
 */

public class Unsigned {

    public static short getUnsignedByte (ByteBuffer bb){
        return ((short) (bb.get() & 0xff));
    }

    public static void putUnsignedByte (ByteBuffer bb, int value){
        bb.put((byte) (value & 0xff));
    }

    public static short getUnsignedByte (ByteBuffer bb, int position){
        return ((short) (bb.get(position) & (short) 0xff));
    }

    public static void putUnsignedByte (ByteBuffer bb, int position, int value){
        bb.put(position, (byte) (value & 0xff));
    }

    // -----------------------------------------------------------------------------

    public static int getUnsignedShort (ByteBuffer bb){
        return (bb.getShort() & 0xffff);
    }

    public static void putUnsignedShort(ByteBuffer bb, int value){
        bb.putShort((short) (value & 0xffff));
    }

    public static int getUnsignedShort (ByteBuffer bb, int position){
        return (bb.getShort(position) & 0xffff);
    }

    public static void putUnsignedShort(ByteBuffer bb, int position, int value){
        bb.putShort(position, (short) (value & 0xffff));
    }

    // -----------------------------------------------------------------------------

    public static long getUnsignedInt(ByteBuffer bb){
        return ((long) bb.getInt() & 0xffffffffL);
    }

    public static void putUnsignedInt(ByteBuffer bb, long value){
        bb.putInt((int) (value & 0xffffffffL));
    }

    public static long getUnsignedInt(ByteBuffer bb, int position){
        return (bb.getInt(position) & 0xffffffffL);
    }

    public static void putUnsignedInt(ByteBuffer bb, int position, long value){
        bb.putInt(position, (int) (value & 0xffffffffL));
    }

}

