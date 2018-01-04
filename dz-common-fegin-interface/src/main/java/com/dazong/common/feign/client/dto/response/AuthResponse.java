package com.dazong.common.feign.client.dto.response;

import java.util.Map;

import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class AuthResponse {
	
	private Map<String,Boolean> dataList;

}
