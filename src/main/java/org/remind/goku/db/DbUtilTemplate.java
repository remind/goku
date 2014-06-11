package org.remind.goku.db;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
import org.remind.goku.db.sql.SqlConnection;

public class DbUtilTemplate {

	private static QueryRunner queryRunner = new QueryRunner(
			SqlConnection.getDataSource());

	private static Logger log = Logger.getLogger(DbUtilTemplate.class);

	@SuppressWarnings("rawtypes")
	private static ScalarHandler scalarHandler = new ScalarHandler() {
		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Object obj = super.handle(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}
	};

	/**
	 * sql查询返回list map
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> find(String sql, Object... params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (params == null) {
				list = (List<Map<String, Object>>) queryRunner.query(sql,
						new MapListHandler());
			} else {
				list = (List<Map<String, Object>>) queryRunner.query(sql,
						new MapListHandler(), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * sql查询返回list bean
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> find(Class<?> entityClass, String sql,
			Object... params) {
		List<T> list = new ArrayList<T>();
		try {
			if (params == null) {
				list = (List<T>) queryRunner.query(sql, new BeanListHandler(
						entityClass));
			} else {
				list = (List<T>) queryRunner.query(sql, new BeanListHandler(
						entityClass), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 统计总条数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static long count(String sql, Object... params) {
		Number num = 0;
		try {
			if (params == null) {
				num = (Number) queryRunner.query(sql, scalarHandler);
			} else {
				num = (Number) queryRunner.query(sql, scalarHandler, params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (num != null) ? num.longValue() : -1;
	}

	/**
	 * 只查询一条，返回java bean
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T findFirst(Class<T> entityClass, String sql,
			Object... params) {
		Object object = null;
		try {
			if (params == null) {
				object = queryRunner.query(sql, new BeanHandler(entityClass));
			} else {
				object = queryRunner.query(sql, new BeanHandler(entityClass),
						params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (T) object;
	}

	/**
	 * 修改
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, Object... params) {
		int affectedRows = 0;
		try {
			if (params == null) {
				affectedRows = queryRunner.update(sql);
			} else {
				affectedRows = queryRunner.update(sql, params);
			}
			log.debug("update-执行sql：" + sql);
		} catch (SQLException e) {
			log.error("执行sql出错:" + sql);
			e.printStackTrace();
		}
		return affectedRows;
	}

	/**
	 * 插入数据，并返回主键
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int insertForKeys(String sql, Object... params) {
		int key = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = SqlConnection.getDataSource().getConnection();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ParameterMetaData pmd = stmt.getParameterMetaData();
			if (params.length < pmd.getParameterCount()) {
				throw new SQLException("参数错误:" + pmd.getParameterCount());
			}
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("insertForKey.插入返回主键错误：" + sql, e);
		} finally {
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) { // 关闭声明
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) { // 关闭连接对象
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return key;
	}
	
	/**
	 * 根据表名主键删除记录
	 * @param tableName
	 * @param id
	 * @return
	 */
	public static int deleteByKey(String tableName, int id) {
		String sql = "delete from " + tableName + " where id = ?";
		try {
			return queryRunner.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
