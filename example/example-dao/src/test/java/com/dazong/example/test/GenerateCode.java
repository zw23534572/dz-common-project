package com.dazong.example.test;

import com.dazong.persistence.generatecode.AutoGenerator;
import com.dazong.persistence.generatecode.ConfigGenerator;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-27 16-56
 */
public class GenerateCode {
    public static void main(String[] args){
        ConfigGenerator configGenerator = new ConfigGenerator();

        configGenerator.setDbUrl("jdbc:mysql://172.16.21.15:3306/dzrule");
        configGenerator.setDbUser("maoyi");
        configGenerator.setDbPassword("6qjiaVv6!nlz1JSo");
        configGenerator.setDbSchema("dzrule");

        configGenerator.setBasePackage("com.dazong.example.dao");
        configGenerator.setGenerateTableName("user");

        new AutoGenerator(configGenerator).run();

    }
}
