package org.dean.learn.nio.byteorder;

import java.nio.ByteOrder;

/**
 * 测试当前平台的字节顺序
 * Created by zhanggang3 on 2016/3/12.
 */
public class ByteOrderTest {

    public static void main(String[] args) {

        System.out.println(ByteOrder.nativeOrder());
        System.out.println(ByteOrder.nativeOrder().toString());

    }

}
