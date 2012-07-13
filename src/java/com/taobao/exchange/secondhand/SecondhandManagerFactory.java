/**
 * 
 */
package com.taobao.exchange.secondhand;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.Constants;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午8:53:49
 *
 */
public class SecondhandManagerFactory {
	
	static ConcurrentMap<String,ISecondhandManager<?>> pools = new ConcurrentHashMap<String,ISecondhandManager<?>>();
	
	public static void register(String platformId,ISecondhandManager<?> secondhandManager)
	{
		pools.put(platformId, secondhandManager);
	}
	
	public static ISecondhandManager<?> get(String platformId) throws AppClientException
	{
		if (pools.containsKey(platformId))
			return pools.get(platformId);
		else
			throw new AppClientException(Constants.EXCEPTION_SECONDHANDMANAGER_NOT_EXIST + " , platformId : " + platformId);
	}

}
