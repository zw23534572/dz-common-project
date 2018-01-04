package com.dazong.common.feign.client.dto.request;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author luobinwen
 *
 */
@Data
public class SensitiveRequest {
	
	private List<String> keywords;

}
