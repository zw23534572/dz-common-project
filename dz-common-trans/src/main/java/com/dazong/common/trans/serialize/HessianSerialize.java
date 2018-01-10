package com.dazong.common.trans.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.dazong.common.trans.SerializeException;

/**
 * Hessian参数序列化
 * 
 * @author hujunzhong
 *
 */
public class HessianSerialize implements IParamSerialize {

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

}
