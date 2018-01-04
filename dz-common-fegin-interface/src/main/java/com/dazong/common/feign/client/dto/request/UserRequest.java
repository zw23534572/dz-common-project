package com.dazong.common.feign.client.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author luobinwen
 *
 */
@Data
public class UserRequest {

	@JSONField(name = "user_id")
	@JsonProperty("user_id")
	private Long userId;

}
