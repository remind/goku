package org.remind.goku.cache.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.remind.goku.cache.Cache;

/**
 * 默认缓存 
 * @author remind
 *
 * @param <K>
 * @param <V>
 */
public class DefaultCache<K,V> implements Cache<K,V> {

	private Map<K, V> data = new HashMap<K, V>();
	
	public V get(K key, Callable<V> valueLoader) throws Exception {
		if (!data.containsKey(key)) {
				data.put(key, valueLoader.call());
		}
		return data.get(key);
	}
}
