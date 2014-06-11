package org.remind.goku.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class EntityUtil {
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
	
	/**
	 * 返回有效的属性做为sql操作列
	 * 主要就是排除掉值为null的
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getValidProperty(Object obj) {
		if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new LinkedHashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (!key.equals("class")) {  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    if (value != null) {
                    	map.put(key, value); 
                    }
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
        return map;  
	}
	
}
