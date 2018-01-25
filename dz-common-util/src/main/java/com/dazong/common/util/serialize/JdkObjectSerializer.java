package com.dazong.common.util.serialize;


import java.io.*;

/**
 * 基于jdk默认的串行器
 * @author Sam
 * @version 1.0.0
 */
public class JdkObjectSerializer extends AbstractObjectSerializer implements ObjectSerializer {

    @Override
    public <T> byte[] doSerialize(T object) {
        ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T doDeserialize(byte[] bytes,Class<T> type) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))){
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException(e);
        }
    }
}
