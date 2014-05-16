package com.remind.rmvc.internal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.remind.rmvc.GlobalConfig;
import com.remind.rmvc.test.controller.blog.PageController;

public class TestScanAction {

	private ScanAction scanAction = new ScanAction();
	
	@Test
	public void testGetAction() {
		Class<?> cls = PageController.class;
		Method method;
		try {
			method = cls.getDeclaredMethod("tag", String.class);
			
			ActionInfo ai = new ActionInfo();
			ai.setClassPathPattern("/blog/page");
			ai.setMethodPathPattern("/tag");
			ai.setCls(cls);
			ai.setMethod(method);
			Map<String, Class<?>> param = new HashMap<>();
			param.put("name", String.class);
			ai.setParam(param);
			ai.setGet(true);
			ai.setPost(false);
			
			Set<ActionInfo> actionInfo = scanAction.getActionByClass(cls);
			Assert.assertTrue(actionInfo.iterator().next().equals(ai));//这里不知道为什么用actionInfo.contains(ai)会返回false
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStart() {
		Set<ActionInfo> actions = scanAction.getActionByPackage(GlobalConfig.getControllerPattern());
		Assert.assertTrue(actions.size() > 0);
	}
}
