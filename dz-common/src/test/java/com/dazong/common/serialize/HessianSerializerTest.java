package com.dazong.common.serialize;

import org.junit.Test;

/**
 * hessian序列化测试
 * @author luobinwen
 *
 */
public class HessianSerializerTest {
	@Test
	public void serializeTest() {

		HessianSerializer target = new HessianSerializer();

		SerializeTestCommon.serialize(target);

	}
	
	@Test
	public void deserializeTest(){
		HessianSerializer target = new HessianSerializer();

		SerializeTestCommon.deserialize(target);
	}
}
