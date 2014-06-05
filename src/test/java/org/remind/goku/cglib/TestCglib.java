package org.remind.goku.cglib;

import java.lang.reflect.Method;

import org.remind.goku.model.TestUser;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TestCglib implements MethodInterceptor{

	private Object target;  
	  
    /** 
     * 创建代理对象 
     *  
     * @param target 
     * @return 
     */  
    public Object getInstance(Object target) {  
        this.target = target;  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        enhancer.setCallback(this);  
        return enhancer.create();  
    }  
    
	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		System.out.println("事物开始:" + arg1.getName());  
		arg3.invokeSuper(arg0, arg2);  
        System.out.println("事物结束"); 
		return null;
	}

	public static void main(String[] args) {
		TestCglib test = new TestCglib();
		TestUser user = new TestUser();
		TestUser u = (TestUser) test.getInstance(user);
		u.setId(1);
	}
}
