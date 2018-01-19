package com.dazong.common.mq.manager;

import com.dazong.common.mq.domian.TableInfo;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author huqichao
 * @date 2017-10-30 19:49
 **/
@Component
public class DBManager {

    private Logger logger = LoggerFactory.getLogger(DBManager.class);

    private String dbType;

    @Autowired
    private DataSource dataSource;

    private String getDBType(Connection conn) throws SQLException {
        return conn.getMetaData().getDatabaseProductName();
    }

    public TableInfo selectTable(String dbName, String tableName) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            if (dbType == null){
                dbType = getDBType(conn);
            }
            String[] types = { "TABLE" };
            rs = conn.getMetaData().getTables(null, null, "%", types);
            while (rs.next()) {
                String name = rs.getString("TABLE_NAME");
                String schema = rs.getString("TABLE_SCHEMA");
                String comment;
                if (dbType.equalsIgnoreCase(TableInfo.DBTYPE_H2)){
                    comment = rs.getString("REMARKS");
                } else {
                    comment = rs.getString("TABLE_COMMENT");
                }
                if (schema.equals(dbName) && tableName.equals(name)){
                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setDbName(dbName);
                    tableInfo.setTableName(tableName);
                    tableInfo.setTableDesc(comment);

                    return tableInfo;
                }
            }
            return null;
        } finally {
            close(rs, null, conn);
        }
    }

    private void close(ResultSet rs, Statement pstmt, Connection conn) {
        try {
            if (rs != null){
                rs.close();
            }
            if (pstmt != null){
                pstmt.close();
            }
            if (conn != null){
                conn.close();
            }
        } catch (Exception e) {
            logger.error("close", e);
        }
    }

    public void executeSqlFile(Reader reader) throws SQLException {
        executeSqlFile(reader, false, null, 0);
    }

    public void executeSqlFile(Reader reader, boolean updateVersion, TableInfo tableInfo, int version) throws SQLException {
        ScriptRunner runner = null;
        try {
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            runner = new ScriptRunner(conn);
            runner.setFullLineDelimiter(false);
            runner.setDelimiter(";");
            runner.runScript(reader);
            if (updateVersion){
                updateTableVersion(conn, tableInfo, version);
            }
            conn.commit();
        } finally {
            if (runner != null){
                runner.closeConnection();
            }
        }
    }

    private void updateTableVersion(Connection conn, TableInfo tableInfo, int version) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = String.format("ALTER TABLE `%s`.`%s` COMMENT='%s';",
                tableInfo.getDbName(), tableInfo.getTableName(), tableInfo.getComment(version));
        if (dbType.equalsIgnoreCase(TableInfo.DBTYPE_H2)){
            sql = String.format("COMMENT ON TABLE `%s` IS '%s';", tableInfo.getTableName(), tableInfo.getComment(version));
        }

        stmt.execute(sql);
        close(null, stmt, null);
    }


    public String sqlPath(){
        if (TableInfo.DBTYPE_H2.equalsIgnoreCase(dbType)){
            return "META-INF/sql/h2";
        } else {
            return "META-INF/sql/mysql";
        }
    }
}
