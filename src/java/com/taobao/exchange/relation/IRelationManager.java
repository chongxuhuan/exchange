/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.List;

import com.taobao.exchange.app.client.IAppClient;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.ICache;


/**
 * 关系获取接口
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface IRelationManager <C extends IAppClient,V>{
	
	/**
	 * 设置支持获取关系数据的应用客户端
	 * @param appClient
	 */
	void setAppClient(C appClient);
	
	/**
	 * 注入cache支持关系缓存
	 * @param cache
	 */
	void setRelationCache(ICache<V> cache);
	
	/**
	 * 获取内置关系缓存
	 * @return
	 */
	ICache<V> getRelationCache();

	/**
	 * 获取当前用户的好友关系
	 * @param 授权的用户Id
	 * @param 要查询好友的用户id
	 * @param 查询分页用的上下文，每次查询会修改这个对象作为下次查询的输入(注意用了这个就不会用缓存)
	 * @return
	 */
	List<User> getFriendsByUser(String sessionUid,String uid,QuerySession session) throws ServiceException;
	
	/**
	 * 返回当前用户的间接好友，就是朋友的朋友(支持到两级)
	 * @param 用户id
	 * @param 查询分页用的上下文，每次查询会修改这个对象作为下次查询的输入
	 * @return
	 */
	List<User> getIndirectFriendsByUser(String uid,QuerySession session) throws ServiceException;
	
	
	
}
