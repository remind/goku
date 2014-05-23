package org.remind.goku.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 线程变量上下文
 * 需要用到ThreadLocal变量的都从这里拿
 * 主要是为了统一管理
 * 放倒一起释放 
 * @author remind
 *
 */
public class ThreadLocalContext {

	private static ThreadLocal<ThreadLocalContext> local = new ThreadLocal<ThreadLocalContext>();
	private Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
	
	private static Logger logger = Logger.getLogger(ThreadLocalContext.class);

	/**
	 * 返回ThreadLocalContext
	 * @return
	 */
	public static ThreadLocalContext get() {
		if (local.get() == null) {
			local.set(new ThreadLocalContext());
		}
		return local.get();
	}

	/**
	 * 得到线程变量
	 * 一个线程同一个类只有一个实例
	 * @param cls
	 * @return
	 */
	public Object getSingleThreadLocalVar(Class<?> cls) {
		if (!map.containsKey(cls)) {
			try {
				logger.debug(Thread.currentThread().getId() + " - 创建线程变量：" + cls.getName());
				map.put(cls, cls.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map.get(cls);
	}
	
	/**
	 * 清除所有线程变量
	 */
	public static void remove() {
		logger.debug("释放线程变量：" + Thread.currentThread().getId());
		local.remove();
	}
}
