package org.zchzh.commonutil.serializer;

import java.io.IOException;

/**
 * @author zengchzh
 * @date 2021/5/27
 */
public interface Serializer {

    /**
     * 序列化
     * @param object
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Object deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
