package org.remind.goku.db.bean;

import java.util.List;

/**
 * bean的包装类
 * @author remind
 *
 */
public class BeanWrapper<T> {
	
	private BeanProxy beanProxy;

	public BeanWrapper(T object) {
		beanProxy = new BeanProxy(object);
	}
	
	/**
	 * 获得代理bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getInstance() {
		return (T)this.beanProxy.getInstance();
	}
	
	/**
	 * 获得被修改过的属性名称
	 * @return
	 */
	public List<String> getChangeProperty() {
		return this.beanProxy.getChangeProperty();
	}

}
