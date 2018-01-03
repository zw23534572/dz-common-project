package com.dazong.common.feign.client.dto.request;

import java.util.List;

import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class AuthRequest {
	
	private Long userId;
	
	List<String> privList;
	
}
