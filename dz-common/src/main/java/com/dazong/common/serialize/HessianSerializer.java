package com.dazong.common.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.dazong.common.exceptions.SerializeException;

/**
 * hessian 序列化
 * @author luobinwen
 *
 */
public class HessianSerializer extends AbstractObjectSerializer {
	private boolean allowUnSerializable = true;

	@Override
	public byte[] serialize(Object[] params) {
		if (params == null || params.length == 0) {
			return new byte[0];
		}

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Hessian2Output hessianOutput = new Hessian2Output(out);
			hessianOutput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
			for (Object p : params) {
				hessianOutput.writeObject(p);
			}

			hessianOutput.flush();
			return out.toByteArray();
		} catch (Exception e) {
			throw new SerializeException("hessian参数序列化异常!", e);
		}
	}

	@Override
	public Object[] deserialize(byte[] bytes, Class<?>[] clz) {
		if (bytes == null || clz == null || clz.length == 0) {
			return new Object[] {};
		}

		try (InputStream in = new ByteArrayInputStream(bytes)) {
			Object[] result = new Object[clz.length];
			Hessian2Input hessianInput = new Hessian2Input(in);
			hessianInput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
			for (int i = 0; i < clz.length; i++) {
				result[i] = hessianInput.readObject();
			}

			return result;
		} catch (Exception e) {
			throw new SerializeException("hessian参数反序列化异常!", e);
		}
	}

	@Override
	protected <T> T doDeserialize(byte[] bytes) {
		try (InputStream in = new ByteArrayInputStream(bytes)) {
			Hessian2Input hessianInput = new Hessian2Input(in);
			hessianInput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
			return (T) hessianInput.readObject();
		} catch (Exception e) {
			throw new SerializeException("hessian参数反序列化异常!", e);
		}
	}

	@Override
	protected <T> byte[] doSerialize(T object) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Hessian2Output hessianOutput = new Hessian2Output(out);
			hessianOutput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
			hessianOutput.writeObject(object);

			hessianOutput.flush();
			return out.toByteArray();
		} catch (Exception e) {
			throw new SerializeException("hessian参数序列化异常!", e);
		}
	}

}
