package org.remind.goku.guava;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.remind.goku.model.TestUser;

public class Reflection {

	@Test
	public void test() {
		PropertyDescriptor[] p = PropertyUtils.getPropertyDescriptors(TestUser.class); 
		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i].getName());
			System.out.println(p[i].getPropertyType());
		}
	}
	
	public void test1() {
		TestUser t = new TestUser();
		t.setId(1);
	}
}
