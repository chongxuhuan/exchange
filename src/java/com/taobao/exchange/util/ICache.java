/**
 * 
 */
package com.taobao.exchange.util;

/**
 * 普通缓存接口定义
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-10
 *
 */
public interface ICache<K,V> {

	void put(K name, V value);
	
	V get(K name);
	
	V remove(K name);
	
}
