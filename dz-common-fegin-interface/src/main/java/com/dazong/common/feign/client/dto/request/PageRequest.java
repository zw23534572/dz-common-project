package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable{

    private Integer page;

    private Integer pageSize;
}
