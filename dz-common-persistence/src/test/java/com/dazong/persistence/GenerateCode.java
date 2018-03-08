package com.dazong.persistence;

import com.dazong.persistence.generatecode.AutoGenerator;
import com.dazong.persistence.generatecode.ConfigGenerator;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 16-08
 */
public class GenerateCode {

    public static void main(String[] args) {
        ConfigGenerator configGenerator = new ConfigGenerator();

        configGenerator.setDbUrl("jdbc:mysql://172.16.21.15:3306/yunying");
        configGenerator.setDbUser("maoyi");
        configGenerator.setDbPassword("6qjiaVv6!nlz1JSo");
        configGenerator.setDbSchema("yunying");

        configGenerator.setBasePackage("com.dazong.persistence.mybatis");
        configGenerator.setGenerateTableName("cash_price_config");

        new AutoGenerator(configGenerator).run();
    }

}
