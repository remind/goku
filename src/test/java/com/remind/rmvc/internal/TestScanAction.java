package com.remind.rmvc.internal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.remind.rmvc.GlobalConfig;
import com.remind.rmvc.internal.action.ActionInfo;
import com.remind.rmvc.test.controller.blog.PageController;

public class TestScanAction {

	private ScanAction scanAction = new ScanAction();
	
	
	@Test
	public void testStart() {
		Set<ActionInfo> actions = scanAction.getActionByPackage(GlobalConfig.getControllerPattern());
		Assert.assertTrue(actions.size() > 0);
	}
}
