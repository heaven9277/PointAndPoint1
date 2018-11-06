package com.example.zhw.piontandpiont2.Util;

import java.nio.ByteBuffer;

public class BufferChange {
    /**
     * String 转换 ByteBuffer
     * @param str
     * @return
     */
    public static ByteBuffer getByteBuffer(String str){
        return ByteBuffer.wrap(str.getBytes());
    }
}
