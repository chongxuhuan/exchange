/**
 * 
 */
package com.taobao.exchange.app;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9 ����8:54:45
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
	 * ������Ȩ��Ϣ��client��
	 * @param auth
	 */
	public void addAuthToClient(AppAuthEntity auth)
	{
		authPools.put(auth.getUid(), auth);
	}

}
