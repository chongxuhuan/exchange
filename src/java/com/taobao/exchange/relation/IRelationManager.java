/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.List;

import com.taobao.exchange.app.IAppClient;
import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.ICache;


/**
 * 弱关系获取接口
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
	 * 获取当前用户的好友关系
	 * @param 用户id
	 * @return
	 */
	List<User> getFriendsByUser(String uid) throws AppClientException;
	
	/**
	 * 返回当前用户的间接好友，就是朋友的朋友
	 * @param 用户id
	 * @return
	 */
	List<User> getIndirectFriendsByUser(String uid) throws AppClientException;
	
	
	
}
