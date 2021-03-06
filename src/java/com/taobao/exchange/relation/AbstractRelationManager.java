/**
 * 
 */
package com.taobao.exchange.relation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.exchange.app.client.IAppClient;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.FriendSecondhandQuerySession;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.ServiceException;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午9:54:58
 *
 */
public abstract class AbstractRelationManager<C extends IAppClient> implements
		IRelationManager<C,String> {
	
	private static final Log logger = LogFactory.getLog(AbstractRelationManager.class);
	
	protected ICache<String> relationCache;
	protected C appClient;
	
	//某一个平台帐号与AZ的对应关系
	private ICache<AccountZoo> userToAccountZooCache;

	@Override
	public ICache<AccountZoo> getUserToAccountZooCache() {
		return userToAccountZooCache;
	}

	@Override
	public void setUserToAccountZooCache(
			ICache<AccountZoo> userToAccountZooCache) {
		this.userToAccountZooCache = userToAccountZooCache;
	}
	
	@Override
	public void setAppClient(C appClient) {
		this.appClient = appClient;
	}
	
	@Override
	public void setRelationCache(ICache<String> relationCache) {
		this.relationCache = relationCache;
	}

	@Override
	public ICache<String> getRelationCache() {
		return relationCache;
	}
	
	@Override 
	public List<User> getApplicationFriendsByUser(String uid) throws ServiceException
	{
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		if (userToAccountZooCache == null)
			throw new ServiceException("userToAccountZooCache is null.");
		
		//普通好友一次获取出来
		List<User> friends = this.getFriendsByUser(uid,uid,null);
		

		for (int i = friends.size()-1 ; i >= 0; i--)
		{
			User u = friends.get(i);
			
			AccountZoo az = userToAccountZooCache.get(AppClientUtil.generatePlatformUUID(u.getPlatformId(), u.getId()));
			
			if (az == null)
				friends.remove(i);
		}
		
		return friends;
		
	}
	
	@Override
	public List<User> getIndirectFriendsByUser(String uid, FriendSecondhandQuerySession friendSession)
			throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		List<User> result = new ArrayList<User>();
		
		
		int count = 0;
		int member = 0;
		
		if (friendSession == null)
		{
			//普通好友一次获取出来
			List<User> friends = this.getFriendsByUser(uid,uid,null);
			
			for(User u : friends)
			{
				List<User>  df = this.getFriendsByUser(uid,u.getId(),null);
				
				if (df != null)
				{
					for(User iu : df)
					{
						//不要把自己加入进去了
						if (iu.getId() != uid)
						{
							iu.setBridgeUser(u.getName());
							result.add(iu);
						}
					}
				}
				
				count += 1;
				member += df.size();
				
				if (member > Constants.MAX_INDIRECT_RELATION_COUNT)
				{
					logger.error("user :" +uid + "indirectRelation out of limit.");
					break;
				}
				
				if (logger.isInfoEnabled())
					logger.info("count : " + count + ", member : " + member);
			}	
		}
		else
		{
			//普通好友一次获取出来
			//fixme
			List<User> friends = this.getFriendsByUser(uid,uid,null);
			
			for(User u : friends)
			{
				count += 1;
				
				if (count <= friendSession.getFriendCursor() * friendSession.getFriendPageSize())
				{
					continue;
				}
				else
				{
					if (count > (friendSession.getFriendCursor()+1) * friendSession.getFriendPageSize())
					{
						friendSession.setFriendCursor(friendSession.getFriendCursor()+ 1);
						break;
					}
				}
				
				List<User>  df = this.getFriendsByUser(uid,u.getId(),null);
				
				if (df != null)
				{
					for(User uc : df)
					{
						//不要把自己加入进去了
						if (uc.getId() == uid)
							continue;
						
						//fixme
						//这里后续考虑如何用更小的内存容纳更多的用户信息
						if (!friendSession.needFilter(uc.getId()))
						{
							uc.setBridgeUser(u.getName());
							result.add(uc);
							member +=1;
							friendSession.addFilterEntry(uc.getId(), uc.getId());
						}
						else
						{
							u.getName();
						}
						
					}
				}
				
				if (member > Constants.MAX_INDIRECT_RELATION_COUNT)
				{
					logger.error("user :" +uid + "indirectRelation out of limit.");
					break;
				}
				
				if (logger.isInfoEnabled())
					logger.info("friend count : " + 
							(count - friendSession.getFriendCursor() * friendSession.getFriendPageSize()) 
							+ ", vaildate friend count : " + member);

			}
			
			if (count <= (friendSession.getFriendCursor()+1) * friendSession.getFriendPageSize())
			{
				friendSession.setFriendCursor(0);
				friendSession.clearFilter();
			}
			
		}
		
		
		return result;
	}

}
