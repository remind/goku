package org.remind.goku.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.remind.goku.utils.MethodParamInfo;

import com.google.common.collect.Maps;

/**
 * 根据模式串匹配实际url路径
 * @author remind
 *
 */
public class PathMatcher {
	
	/**
	 * 配置的模式串
	 */
	private String pattern;
	
	private List<MethodParamInfo> paramList = new ArrayList<MethodParamInfo>();
	
	/**
	 * 实际url路径
	 */
	private String path;
	
	/**
	 * url中的变量值
	 */
	private Map<String, Object> variable = Maps.newConcurrentMap();
	
	/**
	 * url 分隔符
	 */
	public static final char DEFAULT_SEPARATE = '/';
	
	
	/**
	 * 根据模式串匹配url
	 * @param pattern	模式串
	 * @param path	url实际路径
	 */
	public PathMatcher(String pattern, String path, List<MethodParamInfo> list) {
		this.pattern = pattern;
		this.path = path;
		this.paramList = list;
	}

	/**
	 * 开始匹配
	 * 这个可能还是得再换成 ant path更可靠
	 * @return <p>true:匹配成功</p> <p>false:没有匹配上</p>
	 */
	public boolean doMatch() {
		if (pattern.equals(path)) { //相等
			return true;
		}
		
		if ((pattern.equals("") && !path.equals("")) || ((path.equals("") && !pattern.equals("")))) {
			return false;
		}
		
		char[] patternArray = pattern.toCharArray();
		char[] pathArray = path.toCharArray();
		int i = 0;
		int j = 0;
		int patternLen = pattern.length();
		int pathLen = path.length();
		while (true) {
			if (patternArray[i] != '{') {
				if (patternArray[i] == pathArray[j]) {
					i++;
					j++;
				} else {
					break;
				}
			} else {
				int m = pattern.indexOf('}', i);
				int n = path.indexOf(DEFAULT_SEPARATE, j);
				if (n == -1) {
					n = pathLen - 1;
				} else {
					n -= 1;
				}
				String paramName = pattern.substring(i + 1, m);
				String paramValue = path.substring(j, n + 1);
				
				variable.put(paramName, paramValue);
				
				i = m + 1;
				j = n + 1;
			}
			
			if (i < patternLen && j == pathLen) { //为了兼容有默认值的，当前只处理了默认值在最后面的情况
				for (int m = 0; m < paramList.size(); m++) {
					MethodParamInfo paramInfo = paramList.get(m);
					if (paramInfo.getDefaultValue() != null) {
						String s = pattern.replaceAll("\\{"+ paramInfo.getName() +"\\}", "");
						if (s.endsWith(String.valueOf(DEFAULT_SEPARATE))) {
							s = s.substring(0, s.length() - 1);
						}
						patternArray = s.toCharArray();
						patternLen = pathArray.length;
						variable.put(paramInfo.getName(), paramInfo.getDefaultValue());
					}
				}
			}
			
			if ((i == patternLen && j < pathLen) || (i < patternLen && j == pathLen)) {
				break;
			} else if (i == patternLen && j == pathLen) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 获取变量值
	 * @return
	 */
	public Map<String, Object> getVariable() {
		return variable;
	}
	
	/**
	 * 拼接两个路径
	 * @param pattern1
	 * @param pattern2
	 * @return
	 */
	public static String combine(String pattern1, String pattern2) {
		String result = pattern1 + pattern2;
		return filter(result);
	}
	
	/**
	 * 对路径进行处理
	 * 1.去掉多余的斜线
	 * 2.去掉最后一个斜线
	 * @param pattern
	 * @return
	 */
	public static String filter(String path) {
		String result = "";
		char[] pathArray = path.toCharArray();
		for (int i = 0; i < path.length(); i++) {
			if (pathArray[i] != DEFAULT_SEPARATE || !result.endsWith(String.valueOf(DEFAULT_SEPARATE))) {
				result += String.valueOf(pathArray[i]);
			}
		}
		if (result.endsWith(String.valueOf(DEFAULT_SEPARATE))) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	public static void main(String[] args) {
		String s = "hot/{page}";
		System.out.println(s.replaceAll("\\{page\\}", ""));
	}
}
