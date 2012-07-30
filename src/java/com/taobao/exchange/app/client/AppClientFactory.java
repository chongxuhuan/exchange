/**
 * 
 */
package com.taobao.exchange.app.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ServiceException;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午10:09:26
 *
 */
public class AppClientFactory {
	static ConcurrentMap<String,IAppClient> pools = new ConcurrentHashMap<String,IAppClient>();
	
	public static void register(String platformId,IAppClient appClient)
	{
		pools.put(platformId, appClient);
	}
	
	public static IAppClient get(String platformId) throws ServiceException
	{
		if (pools.containsKey(platformId))
			return pools.get(platformId);
		else
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST + " , platformId : " + platformId);
	}

}
