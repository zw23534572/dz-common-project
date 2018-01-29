package com.dazong.common.util;

import com.dazong.common.exceptions.ArgumetException;
import com.dazong.common.exceptions.BusinessException;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author: zisong.wang
 * @date: 2018/1/26
 */
public class AssertTest {

    @Test
    public void test(){
        try {
            String str = null;
            Assert.notNull(str,"str");
            assertThat(true).isFalse();
        }catch (ArgumetException e){
            System.out.println("异常捕获: "+e.getMessage());
        }

        try {
            Map map = null;
            Assert.notEmpty(map,"map");
            assertThat(true).isFalse();
        }catch (BusinessException e){
            System.out.println("异常捕获: "+e.getMessage());
        }
    }

}
