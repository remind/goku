package org.remind.goku.db;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.remind.goku.db.sql.SqlConnection;

public class DbUtilTemplate {

	private static QueryRunner queryRunner = new QueryRunner(
			SqlConnection.getDataSource());

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
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T findFirst(Class<T> entityClass, String sql, Object... params) {
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

}
