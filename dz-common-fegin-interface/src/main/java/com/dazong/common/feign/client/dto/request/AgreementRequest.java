package com.dazong.common.feign.client.dto.request;

import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class AgreementRequest {
	
	private String companyIds;
	
	private String warehouseCode;
	
	private String url;

}
