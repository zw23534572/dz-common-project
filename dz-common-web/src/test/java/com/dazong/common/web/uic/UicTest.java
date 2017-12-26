/*package com.dazong.common.web.uic;

import java.math.BigDecimal;

import org.junit.Test;

import com.dazong.common.util.BeanJsonUtil;
import com.dazong.common.web.util.HttpInvokeException;

public class UicTest {
    
   // @Test
    public void testmember() throws HttpInvokeException{
        
        UICQueryUtil memberQueryUtil = new UICQueryUtil();
        
        MemberInfo memberInfo = memberQueryUtil.getMember("120");
        System.out.println(BeanJsonUtil.bean2Json(memberInfo));
        
    }
    
    
    //@Test
    public void testCompany() throws HttpInvokeException{
        
        UICQueryUtil coQueryUtil = new UICQueryUtil();
        coQueryUtil.setCompanyUrl("http://user.api.dazong.com/java_api/company_find/env/test");
        CompanyInfo coInfo = coQueryUtil.getCompany("88");
        if(null != coInfo)
            System.out.println(BeanJsonUtil.bean2Json(coInfo));
        
    }
    
    
    public static void main(String[] args){
        
        String json ="{sitNo:\"FMDZ-TEST-01\",amount:1}";
        
        BeanJsonUtil.json2Object(json, SitNo.class );
        
        
    }
    
    

}


class SitNo {
    
    private String sitNo;
    private BigDecimal amount ;
    
    public String getSitNo() {
        return sitNo;
    }
    public void setSitNo(String sitNo) {
        this.sitNo = sitNo;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    
}
*/