package com.dazong.common.serialize;

import static org.assertj.core.api.Assertions.assertThat;

import com.dazong.common.CommonStatus;
import com.dazong.common.IObjectSerializer;
import com.dazong.common.exceptions.BusinessException;
import com.dazong.common.resp.DataResponse;
import com.dazong.common.resp.PageResult;

/**
 * 序列化测试工具
 * @author luobinwen
 *
 */
public class SerializeTestCommon {

	public static void serialize(IObjectSerializer serializer) {

		byte[] bytes = serializer.serialize(1);
		assertThat(bytes).isNotNull().isNotEmpty();

		bytes = serializer.serialize(1L);
		assertThat(bytes).isNotNull().isNotEmpty();

		bytes = serializer.serialize("1");
		assertThat(bytes).isNotNull().isNotEmpty();

		bytes = serializer.serialize(1D);
		assertThat(bytes).isNotNull().isNotEmpty();

		bytes = serializer.serialize(new BusinessException(CommonStatus.ERROR));
		assertThat(bytes).isNotNull().isNotEmpty();

		DataResponse<String> stringResponse = new DataResponse<>(CommonStatus.ERROR);
		stringResponse.setData("1");
		bytes = serializer.serialize(stringResponse);
		assertThat(bytes).isNotNull().isNotEmpty();

		DataResponse<PageResult> pagingResponse = new DataResponse<>(CommonStatus.ERROR);
		pagingResponse.setData(new PageResult(1, 1, 2));
		bytes = serializer.serialize(pagingResponse);
		assertThat(bytes).isNotNull().isNotEmpty();

		bytes = serializer.serialize(new String[] { "1", "2" });
		assertThat(bytes).isNotNull().isNotEmpty();

		bytes = serializer.serialize(new Object[] { stringResponse, pagingResponse });
		assertThat(bytes).isNotNull().isNotEmpty();
	}

	public static void deserialize(IObjectSerializer serializer) {

		byte[] bytes = serializer.serialize(1);
		Object obj = serializer.deserialize(bytes, Integer.class);
		assertThat(obj).isNotNull();

		bytes = serializer.serialize(1L);
		obj = serializer.deserialize(bytes, Long.class);
		assertThat(obj).isNotNull();

		bytes = serializer.serialize("1");
		obj = serializer.deserialize(bytes, String.class);
		assertThat(obj).isNotNull().isEqualTo("1");

		bytes = serializer.serialize(1D);
		obj = serializer.deserialize(bytes, Double.class);
		assertThat(obj).isNotNull();

		bytes = serializer.serialize(new BusinessException(CommonStatus.ERROR));
		obj = serializer.deserialize(bytes, BusinessException.class);
		assertThat(obj).isNotNull().isInstanceOf(BusinessException.class);

		DataResponse<String> stringResponse = new DataResponse<>(CommonStatus.ERROR);
		stringResponse.setData("1");
		bytes = serializer.serialize(stringResponse);
		obj = serializer.deserialize(bytes, DataResponse.class);
		assertThat(obj).isNotNull().isInstanceOf(DataResponse.class);
		assertThat(((DataResponse<String>)obj).getData()).isEqualTo("1");

		DataResponse<PageResult> pagingResponse = new DataResponse<>(CommonStatus.ERROR);
		pagingResponse.setData(new PageResult(1, 1, 2));
		bytes = serializer.serialize(pagingResponse);
		obj = serializer.deserialize(bytes, DataResponse.class);
		assertThat(obj).isNotNull().isInstanceOf(DataResponse.class);

		bytes = serializer.serialize(new String[] { "1", "2" });
		Object[] strlist = serializer.deserialize(bytes, new Class[] { String.class, String.class });
		assertThat(strlist).isNotNull().hasSize(2);
		assertThat(strlist[0]).isInstanceOf(String.class).isEqualTo("1");

		bytes = serializer.serialize(new Object[] { stringResponse, pagingResponse });
		Object[] objList = serializer.deserialize(bytes, new Class[] { DataResponse.class, DataResponse.class });
		assertThat(objList).isNotNull().hasSize(2);
	}

}
