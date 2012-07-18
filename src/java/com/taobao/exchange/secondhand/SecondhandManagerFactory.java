/**
 * 
 */
package com.taobao.exchange.secondhand;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

/**
 * 二手管理类工厂
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-12
 *
 */
public class SecondhandManagerFactory {
	
	static ConcurrentMap<String,ISecondhandManager<?>> pools = new ConcurrentHashMap<String,ISecondhandManager<?>>();
	
	public static void register(String platformId,ISecondhandManager<?> secondhandManager)
	{
		pools.put(platformId, secondhandManager);
	}
	
	public static ISecondhandManager<?> get(String platformId) throws ServiceException
	{
		if (pools.containsKey(platformId))
			return pools.get(platformId);
		else
			throw new ServiceException(Constants.EXCEPTION_SECONDHANDMANAGER_NOT_EXIST + " , platformId : " + platformId);
	}

}
