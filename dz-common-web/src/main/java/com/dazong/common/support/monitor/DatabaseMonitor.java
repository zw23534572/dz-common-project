package com.dazong.common.support.monitor;

import com.alibaba.fastjson.JSON;
import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import com.dazong.common.monitor.BaseMonitor;
import com.dazong.common.monitor.CheckResult;

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
			try (Connection connection = dataSource.getConnection();
					Statement statement = connection.createStatement()) {

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
			}
		}
		return result;
	}

	private void useDzMQ() {
		DataSource dataSource = (DataSource) obj;
		if (dataSource != null) {
			try (Connection conn = dataSource.getConnection();
					ResultSet rs = conn.getMetaData().getTables(null, null, "dz_mq_producer", null)) {

				if (rs.next()) {
					dzMQ = true;
				}
			} catch (SQLException e) {
				logger.error(DATABASE_CONNECTION_ERROR, e);
			}
		}
	}

	private String queryMQCount(Connection conn) {
		String sql = "select `status`, count(id) as count, 'producer' as `type` from dz_mq_producer group by status union all select `status`, count(id) as count,'consumer' as `type` from dz_mq_consumer group by status";

		try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {

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
		}
		catch(Exception e){
			throw new PlatformException(CommonStatus.ERROR, e);
		}
		
	}
}
