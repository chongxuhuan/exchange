/**
 * 
 */
package com.taobao.exchange.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 内存版的缓存实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012－7－12
 *
 */
public class MemCache<K,V> implements ICache<K,V> {

	ConcurrentMap<K,V> innerCache = new ConcurrentHashMap<K,V>();
	
	@Override
	public void put(K name, V value) {
		innerCache.put(name, value);
	}


	@Override
	public V get(K name) {
		return innerCache.get(name);
	}

	
	@Override
	public V remove(K name) {
		return innerCache.remove(name);
	}

}
