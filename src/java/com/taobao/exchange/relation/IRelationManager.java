/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.List;

import com.taobao.exchange.app.IAppClient;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.ICache;


/**
 * 关系获取接口
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface IRelationManager <C extends IAppClient,K,V>{
	
	/**
	 * 设置支持获取关系数据的应用客户端
	 * @param appClient
	 */
	void setAppClient(C appClient);
	
	/**
	 * 注入cache支持关系缓存
	 * @param cache
	 */
	void setRelationCache(ICache<K,V> cache);
	
	/**
	 * 获取内置关系缓存
	 * @return
	 */
	ICache<K,V> getRelationCache();

	/**
	 * 获取当前用户的好友关系
	 * @param 授权的用户Id
	 * @param 要查询好友的用户id
	 * @return
	 */
	List<User> getFriendsByUser(String sessionUid,String uid) throws ServiceException;
	
	/**
	 * 返回当前用户的间接好友，就是朋友的朋友(支持到两级)
	 * @param 用户id
	 * @return
	 */
	List<User> getIndirectFriendsByUser(String uid) throws ServiceException;
	
	
	
}
