package org.remind.goku.db.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 数据源连接
 * 暂定直接用druid
 * @author remind
 *
 */
public class SqlConnection {

	private static DataSource dataSource;

	/**
	 * 初始化数据源
	 * @param properties
	 */
	public static void initDataSource(Properties properties) {
		try {
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据链接
	 * @return
	 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
