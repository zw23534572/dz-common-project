package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.util.List;

/**
 *  @author yanghui
 */
@Data
public class AuthRequest {
	
	private Long userId;
	
	List<String> privList;
	
}
