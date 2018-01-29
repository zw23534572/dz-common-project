package com.dazong.common.serialize;

import org.junit.Test;

/**
 * jdk序列化测试
 * @author luobinwen
 *
 */
public class JdkSerializerTest {
	@Test
	public void serializeTest() {

		JdkSerializer target = new JdkSerializer();

		SerializeTestCommon.serialize(target);

	}
	
	@Test
	public void deserializeTest(){
		JdkSerializer target = new JdkSerializer();

		SerializeTestCommon.deserialize(target);
	}
}
