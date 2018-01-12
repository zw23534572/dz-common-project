package com.dazong.common.resp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dazong.common.CommonStatus;
import com.dazong.common.IResultStatus;
import com.dazong.common.exceptions.BaseApplicationException;

/**
 * @author luobinwen
 */
public class CommonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int retCode;

    private String retMsg;

    public CommonResponse() {

    }

    public CommonResponse(IResultStatus resultStatus) {
        this(resultStatus.getCode(), resultStatus.getMessage());
    }

    public CommonResponse(BaseApplicationException e) {
        this(e.getCode(), e.getMessage());
    }

    public CommonResponse(int retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public boolean isSuccess() {
        return retCode % 1000 == CommonStatus.SUCCESS.getCode();
    }

    public int getCode() {
        return retCode;
    }

    public void setCode(int code) {
        this.retCode = code;
    }

    public String getMsg() {
        return retMsg;
    }

    public void setMsg(String msg) {
        this.retMsg = msg;
    }

    public void setMessage(String message) {
        this.retMsg = message;
    }

    public Map<String, Object> toMapModel() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", retCode);
        map.put("msg", retMsg);
        return map;
    }

}