/**
 * 
 */
package com.taobao.exchange.app;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午9:49:16
 *
 */
public class MemAuthKeeper implements IAuthKeeper {

	ConcurrentMap<String,AppAuthEntity> authpools = new ConcurrentHashMap<String,AppAuthEntity>();
	
	@Override
	public boolean store(String uid, AppAuthEntity authEntity) {
		
		authpools.put(uid, authEntity);
		return true;
	}

	@Override
	public AppAuthEntity take(String uid) {
		
		return authpools.get(uid);
	}

}
