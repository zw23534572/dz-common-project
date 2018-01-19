package com.dazong.common.mq.domian;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author huqichao
 * @create 2017-10-30 19:29
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class TableInfo implements Serializable {

    public static final String DBTYPE_MYSQL = "mysql";
    public static final String DBTYPE_H2 = "h2";

    private String dbName;

    private String tableName;

    private String tableDesc;

    public int getVersion(){
        String[] str = tableDesc.split("-");
        return Integer.valueOf(str[1]);
    }

    public String getComment(int verion){
        String[] str = tableDesc.split("-");
        return str[0] + "-" + verion;
    }
}
