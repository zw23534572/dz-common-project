package com.dazong.common.trans.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dazong.common.trans.DzTransactionException;

/**
 * java序列化实现
 * @author hujunzhong
 *
 */
public class JavaObjectSerialize implements IParamSerialize {

	@Override
	public byte[] serialize(Object[] params) {
		if(params == null || params.length == 0){
			return null;
		}
		
		ByteArrayOutputStream out = null;
		try{
			out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			for (Object p : params) {
				oos.writeObject(p);
			}
			
			oos.flush();
			return out.toByteArray();
		}catch (Exception e) {
			throw new DzTransactionException("java object参数序列化异常!", e);
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
			ObjectInputStream ois = new ObjectInputStream(in);
			for (int i = 0; i < clz.length; i++) {
				result[i] = ois.readObject();
			}
			
			return result;
		}catch (Exception e) {
			throw new DzTransactionException("java object参数反序列化异常!", e);
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
