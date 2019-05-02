package com.dazong.common.feign.client.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 
 * @author luobinwen
 *
 */
@Data
public class UserRequest {

	@JSONField(name = "user_id")
	private Long userId;

}
