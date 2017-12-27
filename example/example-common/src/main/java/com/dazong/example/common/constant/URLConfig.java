package com.dazong.example.common.constant;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huqichao
 * @create 2017-10-16 20:35
 **/
@Configuration
@ConfigurationProperties(prefix = "url.config")
public class URLConfig {

    @Value("${url.config.php.user.center}")
    @NotBlank
    private String userCenter;

    public String getUserCenter() {
        return userCenter;
    }

    public void setUserCenter(String userCenter) {
        this.userCenter = userCenter;
    }
}
