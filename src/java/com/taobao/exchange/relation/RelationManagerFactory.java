/**
 * 
 */
package com.taobao.exchange.relation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

/**
 * 关系管理类注册工厂
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
	
	public static IRelationManager<?,?,?> get(String platformId) throws ServiceException
	{
		if (pools.containsKey(platformId))
			return pools.get(platformId);
		else
			throw new ServiceException(Constants.EXCEPTION_RELATIONMANAGER_NOT_EXIST + " , platformId : " + platformId);
	}

}
