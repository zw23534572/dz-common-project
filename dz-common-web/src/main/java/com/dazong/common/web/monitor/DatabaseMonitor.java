package com.dazong.common.web.monitor;

import com.alibaba.fastjson.JSON;
import com.dazong.common.monitor.CheckResult;
import com.dazong.common.monitor.BaseMonitor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author huqichao Created by on 17/5/25.
 *
 */
public class DatabaseMonitor extends BaseMonitor {

	private static final String COUNT = "count";
	private static final String STATUS = "status";
	private static final String CLOSE_ERROR = "close error";
	private static final String DATABASE_CONNECTION_ERROR = "database connection error";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean dzMQ;

	public DatabaseMonitor(Object obj) {
		super(obj);

		useDzMQ();
	}

	/**
	 * 检测
	 * 
	 * @return 检测结果
	 */
	@Override
	public CheckResult check() {
		CheckResult result = new CheckResult(NAME_DATABASE);
		DataSource dataSource = (DataSource) obj;
		if (dataSource != null) {
			Connection connection = null;
			Statement statement = null;
			try {
				connection = dataSource.getConnection();
				statement = connection.createStatement();
				if (dzMQ) {
					String msg = queryMQCount(connection);
					result.setErrorMsg(msg);
				} else {
					statement.execute("SELECT 1 FROM DUAL");
				}
			} catch (Exception e) {
				result.setSuccess(false);
				result.setStatus(ERROR);
				result.setErrorMsg(DATABASE_CONNECTION_ERROR);
				logger.error(DATABASE_CONNECTION_ERROR, e);
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					logger.error(CLOSE_ERROR, e);
					result.setSuccess(false);
					result.setStatus(ERROR);
					result.setErrorMsg(CLOSE_ERROR);
				}
			}
		}
		return result;
	}

	private void useDzMQ() {
		DataSource dataSource = (DataSource) obj;
		if (dataSource != null) {
			Connection conn = null;
			ResultSet rs = null;
			try {
				conn = dataSource.getConnection();
				rs = conn.getMetaData().getTables(null, null, "dz_mq_producer", null);
				if (rs.next()) {
					dzMQ = true;
				}
			} catch (SQLException e) {
				logger.error(DATABASE_CONNECTION_ERROR, e);
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (Exception e) {
					logger.error(CLOSE_ERROR, e);
				}
			}
		}
	}

	private String queryMQCount(Connection conn) throws Exception {
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(
					"select `status`, count(id) as count, 'producer' as `type` from dz_mq_producer group by status union all select `status`, count(id) as count,'consumer' as `type` from dz_mq_consumer group by status");

			int unSendCount = 0;
			int sendCount = 0;
			int unNotifyCount = 0;
			int notifyCount = 0;
			while (rs.next()) {
				if (rs.getString("type").equals("producer")) {
					if (rs.getInt(STATUS) == 0) {
						unSendCount += rs.getInt(COUNT);
					} else if (rs.getInt(STATUS) == 1) {
						sendCount += rs.getInt(COUNT);
					}
				} else if (rs.getString("type").equals("consumer")) {
					if (rs.getInt(STATUS) == 0) {
						unNotifyCount += rs.getInt(COUNT);
					} else if (rs.getInt(STATUS) == 1) {
						notifyCount += rs.getInt(COUNT);
					}
				}
			}
			Map<String, Integer> map = new HashMap<>(4);
			map.put("unSendCount", unSendCount);
			map.put("sendCount", sendCount);
			map.put("unNotifyCount", unNotifyCount);
			map.put("notifyCount", notifyCount);
			return JSON.toJSONString(map);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				logger.error(CLOSE_ERROR, e);
			}
		}
	}
}
