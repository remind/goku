package com.remind.rmvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
	
	public Test() {
		System.out.println("test");
	}

	public void aa() {
		System.out.println("aa");
	}
	
	public static void main(String[] args) {
		String className = "com.remind.rmvc.Test";
		try {
			Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(className);
			Object o = cls.newInstance();
			Method method = cls.getMethod("aa");
			method.invoke(o);
			method.invoke(o);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
