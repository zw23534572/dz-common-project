package com.dazong.common.web.support.monitor;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.beans.PropertyVetoException;


/**
 * @author manson.zhou on 2018/1/23.
 */
public class DatabaseMonitorTest {

    @Test
    public void check() throws PropertyVetoException {
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setDriverClass("com.mysql.jdbc.Driver");
        source.setUser("maoyi");
        source.setPassword("6qjiaVv6!nlz1JSo");
        source.setJdbcUrl("jdbc:mysql://172.16.21.15:3306/message_platform");
        DatabaseMonitor databaseMonitor = new DatabaseMonitor(source);
        databaseMonitor.check();
    }
}
