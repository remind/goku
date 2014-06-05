package org.remind.goku.utils;

import org.junit.Assert;
import org.junit.Test;

public class ModelUtilTest {

	@Test
	public void translatePropertyName() {
		Assert.assertEquals("id", ModelUtil.translatePropertyName("id"));
		Assert.assertEquals("user_name", ModelUtil.translatePropertyName("userName"));
		Assert.assertEquals("id1", ModelUtil.translatePropertyName("id1"));
		Assert.assertEquals("user_name1", ModelUtil.translatePropertyName("userName1"));
	}
	
}
