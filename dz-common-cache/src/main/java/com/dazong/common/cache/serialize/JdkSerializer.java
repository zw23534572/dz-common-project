package com.dazong.common.cache.serialize;

import java.io.*;

/**
 * @author huqichao
 * @date 2018-01-19 11:32
 **/
public class JdkSerializer extends AbstractObjectSerializer {
    @Override
    public <T> T doDeserialize(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            ObjectInputStream inputStream = new ObjectInputStream(bin);
            Object object = inputStream.readObject();
            inputStream.close();
            bin.close();
            return (T) object;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    protected <T> byte[] doSerialize(T object) {
        try {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(byteArrayOS);
            stream.writeObject(object);
            stream.close();
            byteArrayOS.close();
            return byteArrayOS.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
