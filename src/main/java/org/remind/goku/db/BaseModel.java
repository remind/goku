package org.remind.goku.db;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.remind.goku.db.bean.BeanWrapper;
import org.remind.goku.utils.ModelUtil;

import com.google.common.base.Joiner;

public abstract class BaseModel {

	private Map<String, PropertyDescriptor> column;
	
	private String tableName;
	
	private String columnStr;
	
	protected BaseModel() {
		init();
	}
	
	private void init() {
		column = ModelUtil.getColumn(this.getClass());
		tableName = ModelUtil.getTableName(this.getClass());
		columnStr = Joiner.on(",").join(column.keySet().iterator());
	}
	
	/**
	 * 插入数据库
	 * @return
	 */
	public boolean insert() {
		List<String> columnList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		for(String key : column.keySet()) {
			columnList.add(key);
			try {
				valueList.add(BeanUtils.getProperty(this, column.get(key).getName()));
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		System.out.println(valueList);
//		String sql = "insert into " + tableName + "(" + columnStr + ") values(" + valueStr + ")";
//		System.out.println(sql);
		return true;
	}
	
	
	
}
