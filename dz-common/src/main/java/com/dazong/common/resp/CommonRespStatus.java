package com.dazong.common.resp;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public enum CommonRespStatus {
    
    SUCCESS(200,"成功"),
    FAIL(400,"处理失败！{0}"),
    ERROR(500,"系统错误！{0}");
    
    CommonRespStatus(int code,String codeDesc){
        this.code = code;
        this.codeDesc = codeDesc;
    }
    
    private int code;
    private String codeDesc;

    public int getCode() {
        return code;
    }
    
    public String getCodeDesc() {
        return codeDesc;
    }
    /** 执行错误信息格式化 */
    public String getCodeDesc(Object... msg) {
        if(null != msg && msg.length > 0){
           return MessageFormat.format(codeDesc, msg);
        }
        return codeDesc;
    }

    public static CommonRespStatus getStatusByCode(int code){
        CommonRespStatus[]  statuses = CommonRespStatus.values();
        for(CommonRespStatus as : statuses){
            if(as.code == code){
                return as;
            }
        }
        throw new IllegalArgumentException("编码状态未定义！[status]="+code);
    }
    
    public Map<Integer, String> convert2Map(){
        CommonRespStatus[]  statuses = CommonRespStatus.values();
        Map<Integer, String> statusMap = new HashMap<Integer, String>();
        for(CommonRespStatus as : statuses){
            statusMap.put(as.code, as.codeDesc);
        }
        return statusMap;
    }
    
    public CommonResponse wapperResponse(){
    	return new CommonResponse(code,codeDesc);
    }
    
    public CommonResponse wapperResponse(String msg){
    	return new CommonResponse(code,msg);
    }
}