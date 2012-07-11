/**
 * 
 */
package com.taobao.exchange.util;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午5:54:18
 *
 */
public interface ICache<K,V> {

	void put(K name, V value);
	
	V get(K name);
	
	V remove(K name);
	
}
