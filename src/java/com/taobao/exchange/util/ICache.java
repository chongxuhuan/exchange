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
public interface ICache<V> {

	void put(String name, V value);
	
	V get(String name);
	
	V remove(String name);
	
	void clear();
	
}
