/**
 * 
 */
package com.taobao.exchange.app;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 授权保存接口的内存版本实现，不可用于生产环境，内存占用会大，且无法持久化
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-12
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
