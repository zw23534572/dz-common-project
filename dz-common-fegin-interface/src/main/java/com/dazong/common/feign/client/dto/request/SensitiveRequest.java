package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.util.List;

/**
 * 
 * @author luobinwen
 *
 */
@Data
public class SensitiveRequest {
	
	private List<String> keywords;

}
