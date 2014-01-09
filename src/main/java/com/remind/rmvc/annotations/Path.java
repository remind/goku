package com.remind.rmvc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 访问路径匹配
 * 这个配置的路径只用于方法上面
 * 在匹配的时候会加上包路径及controller名
 * 如配置的controller包路径为com.remind.sample.controller
 * 现有一个controller在com.remind.sample.controller.blogController
 * 里面有一个index方法配置的路径为"/java"
 * 那么配置的URL就为/blog/java以及/blog/java/
 * 在匹配的时候对于最后的"/",加与不加都会匹配
 * @author remind
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Path {
	
	/**
	 * 匹配路径
	 * @return
	 */
	public String value() default "";

}
