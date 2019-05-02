package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lori.li
 */
@Data
public class WarehouseRequest implements Serializable {

    private String warehouseName;
}
