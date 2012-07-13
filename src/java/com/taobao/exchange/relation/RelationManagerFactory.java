/**
 * 
 */
package com.taobao.exchange.relation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.Constants;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午8:44:28
 *
 */
public class RelationManagerFactory {
	
	static ConcurrentMap<String,IRelationManager<?,?,?>> pools = new ConcurrentHashMap<String,IRelationManager<?,?,?>>();
	
	public static void register(String platformId,IRelationManager<?,?,?> relationManager)
	{
		pools.put(platformId, relationManager);
	}
	
	public static IRelationManager<?,?,?> get(String platformId) throws AppClientException
	{
		if (pools.containsKey(platformId))
			return pools.get(platformId);
		else
			throw new AppClientException(Constants.EXCEPTION_RELATIONMANAGER_NOT_EXIST + " , platformId : " + platformId);
	}

}
