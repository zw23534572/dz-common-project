package com.dazong.common.trans.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.dazong.common.trans.DzTransactionException;

public class HessianSerialize implements IParamSerialize {
	
	private boolean allowUnSerializable = true;

	@Override
	public byte[] serialize(Object[] params) {
		if(params == null || params.length == 0){
			return null;
		}
		
		ByteArrayOutputStream out = null;
		try{
			out = new ByteArrayOutputStream();
			Hessian2Output hessianOutput = new Hessian2Output(out);
			hessianOutput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
			for (Object p : params) {
				hessianOutput.writeObject(p);
			}
			
			hessianOutput.flush();
			return out.toByteArray();
		}catch (Exception e) {
			throw new DzTransactionException("hessian参数序列化异常!", e);
		}finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public Object[] deserialize(byte[] bytes, Class<?>[] clz) {
		if(bytes == null || clz == null || clz.length == 0){
			return new Object[]{};
		}
		
		InputStream in = new ByteArrayInputStream(bytes);
		try{
			Object[] result = new Object[clz.length];
			Hessian2Input hessianInput = new Hessian2Input(in);
			hessianInput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
			for (int i = 0; i < clz.length; i++) {
				result[i] = hessianInput.readObject();
			}
			
			return result;
		}catch (Exception e) {
			throw new DzTransactionException("hessian参数反序列化异常!", e);
		}finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
