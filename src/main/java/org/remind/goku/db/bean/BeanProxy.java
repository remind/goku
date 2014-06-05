package org.remind.goku.db.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * bean代理类
 * 记录哪些属性被修改过
 * @author remind
 *
 */
public class BeanProxy implements MethodInterceptor{

	private Object targetBean;
	
	private List<String> changeProperty = new ArrayList<String>();
	
	public BeanProxy(Object targetBean) {
		this.targetBean = targetBean;
	}
	  
    /** 
     * 创建代理对象 
     * @param target 
     * @return 
     */  
    public Object getInstance() {  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.targetBean.getClass());  
        enhancer.setCallback(this);  
        return enhancer.create();  
    }  
    
	/**
	 * @return the changeProperty
	 */
	public List<String> getChangeProperty() {
		return changeProperty;
	}

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		String methodName = arg1.getName();
		if (methodName.startsWith("set")) {
			changeProperty.add(methodName.substring(3));
		}
		arg3.invokeSuper(arg0, arg2);  
		return null;
	}
}
