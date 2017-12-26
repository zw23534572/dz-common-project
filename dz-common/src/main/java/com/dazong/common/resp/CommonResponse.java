package com.dazong.common.resp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author luobinwen
 *
 */
public class CommonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int retCode;
    
    private String retMsg;
    
    public CommonResponse(){
        
    }
    
    public CommonResponse(int retCode,String retMsg){
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
    
    public boolean isSuccess(){
        return retCode % 1000 == CommonRespStatus.SUCCESS.getCode();
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

    public Map<String, Object> toMapModel(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("retCode", retCode);
        map.put("retMsg", retMsg);
        map.put("code", retCode);
        map.put("msg", retMsg);
        return map;
    }


}