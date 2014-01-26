package com.remind.rmvc.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.remind.rmvc.test.controller.blog.PageController;
import com.remind.rmvc.utils.ClassUtil;
import com.remind.rmvc.utils.ClassUtil.ClassFilter;

public class TestClassUtil {

	@Test
	public void testgetClasses() {
		Set<Class<?>> classes = ClassUtil.getClasses("com.remind.rmvc.test.controller", new ClassFilter() {
			@Override
			public boolean accept(Class<?> cls) {
				if (cls.getName().endsWith("Controller")) {
					return true;
				}
				return false;
			}
		});
		Assert.assertTrue(classes.size() > 0);
	}
	
	@Test
	public void testGetMethodParam() {
		Class<?> cls = PageController.class;
		try {
			Method method = cls.getDeclaredMethod("tag", String.class);
			Map<String, Class<?>> map = ClassUtil.getMethodParam(cls, method);
			Assert.assertEquals(String.class, map.get("name"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
