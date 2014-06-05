package org.remind.goku.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.remind.goku.db.sql.SqlConnection;
import org.remind.goku.db.sql.SqlParam;

/**
 * sql基本操作类
 * @author remind
 *
 */
public class Db {

	private static Logger logger = Logger.getLogger(Db.class);
	
	/**
	 * 根据sql返回多条
	 * 
	 * @param sql
	 *            sql语句
	 * @param sqlParam
	 *            sql参数
	 * @return
	 */
	public static List<Map<String, Object>> find(String sql, SqlParam sqlParam) {
		Connection conn = SqlConnection.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			logger.info("执行sql:" + sql);
			if (sqlParam != null) {
				logger.info("参数" + sqlParam.getValues());
				ps = SqlParam.build(ps, sqlParam);
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnName(i).toLowerCase(),
							rs.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 无参查询，返回多条
	 * @param sql
	 * @return
	 */
	public static List<Map<String, Object>> find(String sql) {
		Connection conn = SqlConnection.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			logger.info("执行sql:" + sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnName(i).toLowerCase(),
							rs.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 返回一条
	 * @param sql
	 * @param sqlParam
	 * @return
	 */
	public static Map<String, Object> findSingle(String sql, SqlParam sqlParam) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = SqlConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			logger.info("执行sql:" + sql);
			if (sqlParam != null) {
				logger.info("参数" + sqlParam.getValues());
				ps = SqlParam.build(ps, sqlParam);
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnName(i).toLowerCase(),
							rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 无参返回一条
	 * @param sql
	 * @return
	 */
	public static Map<String, Object> findSingle(String sql) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = SqlConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			logger.info("执行sql:" + sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnName(i).toLowerCase(),
							rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 插入一条数据，只提示是否插入成功
	 * 
	 * @param sql
	 * @param sqlParam
	 * @return
	 */
	public static boolean insert(String sql, SqlParam sqlParam) {
		Connection conn = SqlConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			logger.info("执行sql:" + sql);
			logger.info("参数" + sqlParam.getValues());
			ps = SqlParam.build(ps, sqlParam);
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Table table(String tableName) {
		return new Table(tableName);
	}
	
	/**
	 * 关闭连接
	 */
	public static void close() {
		try {
			SqlConnection.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
