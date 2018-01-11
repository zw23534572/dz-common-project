package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class WarehouseRequest implements Serializable {

    private String warehouseName;
}
