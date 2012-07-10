/**
 * 
 */
package com.taobao.exchange.app;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9 下午8:54:45
 *
 */
public abstract class AppClient implements IAppClient{
	
	ConcurrentMap<String,AppAuthEntity> authPools;
	
	public AppClient()
	{
		super();
		
		authPools = new ConcurrentHashMap<String,AppAuthEntity>();
	}

	/**
	 * 增加授权信息到client中
	 * @param auth
	 */
	public void addAuthToClient(AppAuthEntity auth)
	{
		authPools.put(auth.getUid(), auth);
	}

}
