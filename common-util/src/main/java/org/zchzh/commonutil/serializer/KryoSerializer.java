package org.zchzh.commonutil.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author zengchzh
 * @date 2021/3/10
 * 使用 kryo 序列化和反序列化
 */

public class KryoSerializer implements Serializer {

    private final KryoPool kryoPool = KryoPoolFactory.getKryoPoolInstance();

    @Override
    public <T> byte[] serialize(T object) {
        Kryo kryo = kryoPool.borrow();

        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);) {
            kryo.writeObject(output, object);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            kryoPool.release(kryo);
        }
    }

    @Override
    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        Kryo kryo = kryoPool.borrow();
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input in = new Input(byteArrayInputStream);) {
            return kryo.readObject(in, clazz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            kryoPool.release(kryo);
        }
    }
}
