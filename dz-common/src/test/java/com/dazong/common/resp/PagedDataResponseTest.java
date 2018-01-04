package com.dazong.common.resp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PagedDataResponseTest {

    @Test
    public void testTotalPageCalculate() {


        PageResult<String> target = new PageResult<String>(1, 20, 21);
        assertThat(target.getTotalPage()).isEqualTo(2);

        target = new PageResult<String>(1, 20, 20);
        assertThat(target.getTotalPage()).isEqualTo(1);

        target = new PageResult<String>(1, 20, 19);
        assertThat(target.getTotalPage()).isEqualTo(1);

        target = new PageResult<String>(1, 19, 20);
        assertThat(target.getTotalPage()).isEqualTo(2);

        target = new PageResult<String>(1, Integer.MAX_VALUE, 20);
        assertThat(target.getTotalPage()).isEqualTo(1);

        target = new PageResult<String>(1, Integer.MAX_VALUE, 0);
        assertThat(target.getTotalPage()).isEqualTo(0);
        
        target = new PageResult<String>(1, 0, 20);
        assertThat(target.getTotalPage()).isEqualTo(1);
    }
}
