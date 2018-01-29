package com.dazong.common.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dazong.common.exceptions.SerializeException;

/**
 * jdk提供的序列化
 * @author luobinwen
 *
 */
public class JdkSerializer extends AbstractObjectSerializer {

	@Override
	public byte[] serialize(Object[] params) {
		if (params == null || params.length == 0) {
			return new byte[0];
		}

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			ObjectOutputStream oos = new ObjectOutputStream(out);
			for (Object p : params) {
				oos.writeObject(p);
			}

			oos.flush();
			return out.toByteArray();
		} catch (Exception e) {
			throw new SerializeException("java object参数序列化异常!", e);
		}
	}

	@Override
	public Object[] deserialize(byte[] bytes, Class<?>[] clz) {
		if (bytes == null || clz == null || clz.length == 0) {
			return new Object[] {};
		}

		try (InputStream in = new ByteArrayInputStream(bytes)) {
			Object[] result = new Object[clz.length];
			ObjectInputStream ois = new ObjectInputStream(in);
			for (int i = 0; i < clz.length; i++) {
				result[i] = ois.readObject();
			}

			return result;
		} catch (Exception e) {
			throw new SerializeException("java object参数反序列化异常!", e);
		}
	}

	@Override
	protected <T> T doDeserialize(byte[] bytes) {
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
			throw new SerializeException("java object参数序列化异常!", e);
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
			throw new SerializeException("java object参数序列化异常!", e);
		}
	}

}
