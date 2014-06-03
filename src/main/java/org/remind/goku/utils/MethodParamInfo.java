package org.remind.goku.utils;

/**
 * 参数信息
 * @author remind
 *
 */
public class MethodParamInfo {

	/**
	 * 参数名
	 */
	private String name;
	
	/**
	 * 参加类型
	 */
	private Class<?> type;
	
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	public MethodParamInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
