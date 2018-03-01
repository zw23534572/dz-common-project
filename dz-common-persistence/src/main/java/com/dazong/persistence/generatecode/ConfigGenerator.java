package com.dazong.persistence.generatecode;

import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
@Data
public class ConfigGenerator {

    public static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径

    /**
     * 保存的路径
     */
    private String saveDir = PROJECT_PATH + "/output";

    /**
     * 实体包名
     */
    private String basePackage = "com.test";
    /**
     * 驱动
     */
    private String dbDriverName = "com.mysql.jdbc.Driver";
    /**
     * 用户名
     */
    private String dbUser;
    /**
     * 数据库名
     */
    private String dbSchema;
    /**
     * 用户密码
     */
    private String dbPassword;
    /**
     * 数据库url
     */
    private String dbUrl;
    /**
     * 需要生成的表
     */
    private String generateTableName;
    /**
     * 作者名称
     */
    private String author;

    /**
     * 忽略的字段
     */
    private String[] ignoreFieldArr = new String[]{"id", "create_time", "create_user", "update_time", "update_user", "yn"};

    /**
     * 包名： 实体
     */
    public String getEntityPackage() {
        return basePackage + ".entity";
    }

    /**
     * 包路径： 实体
     */
    public String getEntityPath() {
        return getSaveDir() + packageConvertPath(getEntityPackage());
    }

    /**
     * 包名： mapper
     */
    public String getMapperPackage() {
        return basePackage + ".mapper";
    }

    /**
     * 包路径： mapper
     */
    public String getMapperPath() {
        return getSaveDir() + packageConvertPath(getMapperPackage());
    }

    /**
     * 包名： service
     */
    public String getServicePackage() {
        return basePackage + ".service";
    }

    /**
     * 包路径： service
     */
    public String getServicePath() {
        return getSaveDir() + packageConvertPath(getServicePackage());
    }

    /**
     * 包名： service impl
     */
    public String getServiceImplPackage() {
        return basePackage + ".service.impl";
    }

    /**
     * 包路径： service impl
     */
    public String getServiceImplPath() {
        return getSaveDir() + packageConvertPath(getServiceImplPackage());
    }

    /**
     * 包名： controller
     */
    public String getControllerPackage() {
        return basePackage + ".service.impl";
    }

    /**
     * 包路径： controller
     */
    public String getControllerPath() {
        return getSaveDir() + packageConvertPath(getControllerPackage());
    }

    /**
     * 报名与路径转换
     *
     * @param packageName
     * @return
     */
    private String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}
