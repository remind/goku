package org.remind.goku.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置参数默认值
 * @author remind
 *
 */
@Target(ElementType.PARAMETER)  
@Retention(RetentionPolicy.RUNTIME)     
@Documented    
@Inherited    
public @interface Default {
	
	public String value();
	
}
