package org.remind.goku.cache;

import java.util.concurrent.Callable;

/**
 * 缓存
 * @author remind
 *
 * @param <K>	缓存键值类型
 * @param <V>	缓存对象类型
 */
public interface Cache<K,V> {

	/**
	 * 先到缓存中取，如果没有就执行valueLoader
	 * @param key	缓存标识
	 * @param valueLoader	加载类
	 * @return
	 * @throws Exception
	 */
	public V get(K key, Callable<V> valueLoader)  throws Exception;
}
