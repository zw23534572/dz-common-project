package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProtocolRequest implements Serializable{

    private String ownerCode;

    private String warehouseCode;
}
