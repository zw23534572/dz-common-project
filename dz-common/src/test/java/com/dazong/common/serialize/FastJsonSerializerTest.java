package com.dazong.common.serialize;

import org.junit.Test;

/**
 * fastjson序列化测试
 * @author luobinwen
 *
 */
public class FastJsonSerializerTest {
	
	@Test
	public void serializeTest() {

		FastJsonSerializer target = new FastJsonSerializer();

		SerializeTestCommon.serialize(target);

	}
	
	@Test
	public void deserializeTest(){
		FastJsonSerializer target = new FastJsonSerializer();

		SerializeTestCommon.deserialize(target);
	}
}
