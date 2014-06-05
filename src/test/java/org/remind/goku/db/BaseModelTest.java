package org.remind.goku.db;

import java.util.Date;

import org.junit.Test;
import org.remind.goku.model.TestUser;

public class BaseModelTest {

	
	public void test() {
		TestUser u = new TestUser();
		u.setId(1);
		u.setUserName("aavvdd");
		u.setAge(0);
		u.setCreateTime(new Date());
		u.insert();
	}
}

