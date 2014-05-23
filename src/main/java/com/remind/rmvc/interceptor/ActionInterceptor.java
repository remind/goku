package com.remind.rmvc.interceptor;

import com.remind.rmvc.internal.action.ActionInfo;
import com.remind.rmvc.internal.action.ActionResult;

/**
 * action级别的拦截器接口
 * 通过注解可以配置在controller或者方法上面
 * @author remind
 *
 */
public interface ActionInterceptor {

	/**
	 * 前置
	 * @return
	 */
	public boolean PreHandle(ActionInfo actionInfo);
	
	/**
	 * 后置
	 * @return
	 */
	public ActionResult PostHandle(ActionResult actionResult);
}
