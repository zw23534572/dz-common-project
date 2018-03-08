package com.dazong.persistence.generatecode;

import com.dazong.persistence.entity.DbTableFieldInfo;
import com.dazong.persistence.entity.DbTableInfo;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 15-14
 */
public class AutoGenerator {


    public AutoGenerator(ConfigGenerator config) {
        this.config = config;
    }

    private ConfigGenerator config;

    /**
     * run 执行
     */
    public void run() {

        /**
         * 检查文件夹是否存在
         */
        File gf = new File(config.getSaveDir());
        if (!gf.exists()) {
            gf.mkdirs();
        }

        /**
         * 开启生成映射关系
         */
        generateCode();

        /**
         * 自动打开生成文件的目录
         * <p>
         * 根据 osName 执行相应命令
         * </p>
         */
//        try {
//            String osName = System.getProperty("os.name");
//            if (osName != null) {
//                if (osName.contains("Mac")) {
//                    Runtime.getRuntime().exec("open " + config.getSaveDir());
//                } else if (osName.contains("Windows")) {
//                    Runtime.getRuntime().exec("cmd /c start " + config.getSaveDir());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(" generate success! ");

    }


    public void generateCode() {

        DBHelper.getConnection(config
                .getDbDriverName(), config.getDbUrl(), config.getDbUser(), config.getDbPassword());

        String tableNames = config.getGenerateTableName();
        for (String tableName : tableNames.split(",")) {
            System.out.println("开始生成表" + tableName);

            //设置返回的时的数据
            Map<String, Object> data = createRenderData(tableName);
            //根据需求生成，不需要的注掉，模板有问题的话可以自己修改。
            buildMapper(data);
            buildEntity(data);
            System.out.println(tableName + "生成完成");
        }
    }


    /**
     * 生成 mapper
     *
     * @param data
     */
    public void buildMapper(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templatePath = "mapper.java";
        String outputPath = config.getMapperPath() + upperModelName + "Mapper.java";
        Output.createFile(templatePath, outputPath, data);

        String templateXMLPath = "mapper.xml";
        String outputXMLPath = config.getMapperPath() + upperModelName + "Mapper.xml";
        Output.createFile(templateXMLPath, outputXMLPath, data);
    }

    /**
     * 生成实体类
     *
     * @param data
     */
    public void buildEntity(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "entity.java";
        String outputName = config.getEntityPath() + upperModelName + ".java";
        Output.createFile(templateName, outputName, data);
    }

    /**
     * 生成 service类
     *
     * @param data
     */
    public void buildService(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "service.java";
        String outputName = config.getServicePath() + upperModelName + "Service.java";
        Output.createFile(templateName, outputName, data);

        String templateImplName = "serviceImpl.java";
        String outputImplName = config.getServiceImplPath() + upperModelName + "ServiceImpl.java";
        Output.createFile(templateImplName, outputImplName, data);
    }

    /**
     * 生成controller类
     *
     * @param data
     */
    public void buildController(Map<String, Object> data) {
        String upperModelName = String.valueOf(data.get("upperModelName"));
        String templateName = "controller.java";
        String outputName = config.getControllerPath() + upperModelName + "Controller.java";
        Output.createFile(templateName, outputName, data);
    }

    /**
     * 渲染模板的数据
     *
     * @param tableName
     * @return
     */
    public Map<String, Object> createRenderData(String tableName) {
        Map<String, Object> data = new HashMap();
        data.put("date", new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new java.util.Date()));
        data.put("author", config.getAuthor());
        data.put("basePackage", config.getBasePackage());
        data.put("baseRequestMapping", tableNameConvertMappingPath(tableName));
        data.put("config", config);
        String upperModelName = convertUpperCamel(tableName);
        data.put("upperModelName", upperModelName);
        data.put("lowerModelName", tableNameConvertLowerCamel(tableName));
        data.put("tableName", tableName);

        //赋值表信息
        DbTableInfo dbTableInfo = getTableInfo(config.getDbSchema(), tableName);
        data.put("entity", dbTableInfo);

        //赋值字段信息
        List<DbTableFieldInfo> dbTableFieldInfoList = getEntityList(tableName, dbTableInfo);
        data.put("items", dbTableFieldInfoList);
        return data;
    }

    /**
     * 表名大小写转换
     *
     * @param dbName
     * @return
     */
    private String tableNameConvertLowerCamel(String dbName) {
        StringBuilder result = new StringBuilder();
        if (dbName != null && dbName.length() > 0) {
            dbName = dbName.toLowerCase();//兼容使用大写的表名
            boolean flag = false;
            for (int i = 0; i < dbName.length(); i++) {
                char ch = dbName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    private String convertUpperCamel(String dbName) {
        return convertUpperCamel(dbName, true);
    }

    private String convertUpperCamel(String dbName, boolean firstUpper) {
        String camel = tableNameConvertLowerCamel(dbName);
        if (firstUpper) {
            return camel.substring(0, 1).toUpperCase() + camel.substring(1);
        } else {
            return camel.substring(0, 1).toLowerCase() + camel.substring(1);
        }
    }

    private String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }


    public DbTableInfo getTableInfo(String schemaName, String tableName) {

        String sql = String.format("SELECT * FROM `information_schema`.`TABLES` WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME ='%s'", schemaName, tableName);
        ResultSet rs = DBHelper.execQuery(sql);

        //数据库读取表的结构
        DbTableInfo dbTableInfo = new DbTableInfo();
        try {
            while (rs.next()) {
                String comment = rs.getString("TABLE_COMMENT");
                dbTableInfo.setComment(comment);

                dbTableInfo.setNamespace(config.getEntityPackage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbTableInfo;
    }


    public List<DbTableFieldInfo> getEntityList(String tableName, DbTableInfo dbTableInfo) {

        String sql = String.format("SHOW FULL FIELDS FROM %s", tableName);
        ResultSet rs = DBHelper.execQuery(sql);

        //数据库读取表的结构
        List<DbTableFieldInfo> entityList = new ArrayList();
        DbTableFieldInfo dbTableFieldInfo;
        try {
            while (rs.next()) {
                String columnName = rs.getString("field");
                String columnType = rs.getString("type");
                String comment = rs.getString("comment");
                String isNullable = rs.getString("null");
                String key = rs.getString("key");

                boolean firstUpper = false;
                dbTableFieldInfo = new DbTableFieldInfo();
                dbTableFieldInfo.setColumn(columnName);
                dbTableFieldInfo.setColumnType(columnType.toUpperCase());

                convertColumnLength(dbTableFieldInfo);

                dbTableFieldInfo.setProperty(convertUpperCamel(columnName, firstUpper));
                dbTableFieldInfo.setPropertyType(dataTypeConvertJavaType(dbTableFieldInfo.getColumnType()));
                dbTableFieldInfo.setComment(comment);

                //忽略字段不做validation操作
                if (!ignoreField(columnName)) {
                    if (isNullable.equals("YES")) {
                        dbTableFieldInfo.setIsNull(Boolean.TRUE);
                    } else {
                        dbTableFieldInfo.setIsNull(Boolean.FALSE);
                    }
                }

                //设置主键
                if (key.equals("PRI") && dbTableInfo.getKeyColumn() == null) {
                    dbTableInfo.setKeyColumn(dbTableFieldInfo.getColumn());
                    dbTableInfo.setKeyProperty(dbTableFieldInfo.getProperty());
                }
                entityList.add(dbTableFieldInfo);
            }
            dbTableInfo.setDbTableFieldInfoList(entityList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entityList;
    }

    /**
     * 获取字段的长度
     *
     * @return
     */
    public void convertColumnLength(DbTableFieldInfo dbTableFieldInfo) {
        if (dbTableFieldInfo.getColumnType().indexOf("(") >= 0) {
            String columnType = dbTableFieldInfo.getColumnType().substring(0, dbTableFieldInfo.getColumnType().indexOf("("));
            String columnLengh = dbTableFieldInfo.getColumnType().substring(dbTableFieldInfo.getColumnType().indexOf("("), dbTableFieldInfo.getColumnType().indexOf(")"));
            dbTableFieldInfo.setColumnType(columnType);
            dbTableFieldInfo.setColumnLength(columnLengh);
        }
    }

    /**
     * 将dbType 转换为 javaType
     *
     * @return
     */
    private String dataTypeConvertJavaType(String dbType) {
        //全部变成小写开始
        dbType = dbType.toLowerCase();
        if (dbType.equals("bigint")) {
            return "Long";
        } else if (dbType.equals("varchar") || dbType.equals("text") || dbType.equals("char")) {
            return "String";
        } else if (dbType.equals("tinyint")) {
            return "Byte";
        } else if (dbType.equals("int")) {
            return "Integer";
        } else if (dbType.equals("datetime") || dbType.equals("timestamp")) {
            return "Date";
        } else if (dbType.equals("bit")) {
            return "Boolean";
        } else if (dbType.indexOf("decimal") > -1) {
            return "java.math.BigDecimal";
        } else if (dbType.indexOf("blob") > -1) {
            return "byte[]";
        } else if (dbType.indexOf("float") > -1) {
            return "Float";
        } else if (dbType.indexOf("double") > -1) {
            return "Double";
        }
        return "other";
    }

    /**
     * 忽略的字段
     *
     * @param fieldName
     * @return
     */
    private boolean ignoreField(String fieldName) {
        for (String item : config.getIgnoreFieldArr()) {
            if (item.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }


}
