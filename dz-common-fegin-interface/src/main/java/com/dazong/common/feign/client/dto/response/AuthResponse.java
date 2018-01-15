package com.dazong.common.feign.client.dto.response;

import lombok.Data;

import java.util.Map;

/**
 *  @author yanghui
 */
@Data
public class AuthResponse {
	
	private Map<String,Boolean> dataList;

}
