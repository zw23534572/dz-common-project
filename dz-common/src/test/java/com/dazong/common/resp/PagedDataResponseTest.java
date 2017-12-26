package com.dazong.common.resp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PagedDataResponseTest {

    @Test
    public void testTotalPageCalculate() {


        PagedDataResponse<Object> target = new PagedDataResponse<Object>(1, 20, 21);
        assertThat(target.getTotal_page()).isEqualTo(2);

        target = new PagedDataResponse<Object>(1, 20, 20);
        assertThat(target.getTotal_page()).isEqualTo(1);

        target = new PagedDataResponse<Object>(1, 20, 19);
        assertThat(target.getTotal_page()).isEqualTo(1);

        target = new PagedDataResponse<Object>(1, 19, 20);
        assertThat(target.getTotal_page()).isEqualTo(2);

        target = new PagedDataResponse<Object>(1, Integer.MAX_VALUE, 20);
        assertThat(target.getTotal_page()).isEqualTo(1);

        target = new PagedDataResponse<Object>(1, Integer.MAX_VALUE, 0);
        assertThat(target.getTotal_page()).isEqualTo(0);
        
        target = new PagedDataResponse<Object>(1, 0, 20);
        assertThat(target.getTotal_page()).isEqualTo(1);
    }
}
