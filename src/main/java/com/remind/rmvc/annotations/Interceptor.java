package com.remind.rmvc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.remind.rmvc.interceptor.ActionInterceptor;

/**
 * 可以设置在类及方法上
 * 前置时类会优先
 * 后置时类会最后
 * @author remind
 *
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptor {
	/**
	 * 可以是多个，按顺序执行
	 * @return
	 */
	Class<? extends ActionInterceptor>[] value();
}
