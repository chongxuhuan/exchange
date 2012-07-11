/**
 * 
 */
package com.taobao.exchange.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午6:07:45
 *
 */
public class MemCache implements ICache<String,String> {

	ConcurrentMap<String,String> innerCache = new ConcurrentHashMap<String,String>();
	
	@Override
	public void put(String name, String value) {
		innerCache.put(name, value);
	}


	@Override
	public String get(String name) {
		return innerCache.get(name);
	}

	
	@Override
	public String remove(String name) {
		return innerCache.remove(name);
	}

}
