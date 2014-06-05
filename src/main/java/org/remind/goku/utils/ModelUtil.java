package org.remind.goku.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class ModelUtil {

	/**
	 * 根据javaBean返回sql列与javaBean的对应关系
	 * @param clazz
	 * @return
	 */
	public static Map<String, PropertyDescriptor> getColumn(Class<?> clazz) {
		PropertyDescriptor[] p = PropertyUtils.getPropertyDescriptors(clazz); 
		Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
		for (int i = 0; i < p.length; i++) {
			if (!p[i].getName().equals("class")) {
				map.put(translatePropertyName(p[i].getName()), p[i]);
			}
		}
		return map;
	}
	
	/**
	 * 把属性名称转换为列名
	 * 1.把大写转换成小写，并在前面加上"_"
	 * @param propertyName
	 * @return
	 */
	public static String translatePropertyName(String propertyName) {
		char[] propertyNameArray = propertyName.toCharArray();
		String str = "";
		for (int i = 0; i < propertyNameArray.length; i++) {
			char c = propertyNameArray[i];
			if (c >= 'A' && c <= 'Z') {
				c = (char)(c + 32);
				str += "_";
			}
			str += String.valueOf(c);
		}
		return str;
	}
	
	/**
	 * 根据类获取表名
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class<?> clazz) {
		String className = clazz.getName().substring(clazz.getPackage().getName().length() + 1);
		return className.substring(0, 1).toLowerCase() + translatePropertyName(className.substring(1));
	}
}
