/**
 * 
 */
package com.taobao.exchange.dig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.taobao.exchange.relation.AccountZoo;
import com.taobao.exchange.relation.IRelationManager;
import com.taobao.exchange.relation.RelationManagerFactory;
import com.taobao.exchange.relation.User;
import com.taobao.exchange.secondhand.ISecondhandManager;
import com.taobao.exchange.secondhand.Secondhand;
import com.taobao.exchange.secondhand.SecondhandManagerFactory;
import com.taobao.exchange.util.FriendSecondhandQuerySession;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;

/**
 * 好友二手商品挖掘实现类
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class FriendsDigger implements ISecondhandDigger<FirendsDigCondition> {
	
	private static final Log logger = LogFactory.getLog(FriendsDigger.class);
	
	private ICache<String,AccountZoo> accountZooCache;//对应帐号体系缓存，主要保存各种AZ
	//某一个平台帐号与AZ的对应关系
	private ICache<String,AccountZoo> userToAccountZooCache;


	public ICache<String, AccountZoo> getUserToAccountZooCache() {
		return userToAccountZooCache;
	}


	public void setUserToAccountZooCache(
			ICache<String, AccountZoo> userToAccountZooCache) {
		this.userToAccountZooCache = userToAccountZooCache;
	}


	public void setAccountZooCache(ICache<String, AccountZoo> accountZooCache) {
		this.accountZooCache = accountZooCache;
	}

	
	/* 
	 * 检查搜索出来的二手是否符合过滤条件，通常用于好友的二手搜索以后再次需要过滤
	 */
	@Override
	public boolean checkSecondhandByCondition(Secondhand s, FirendsDigCondition condition)
	{
		boolean result = true;
		
		if(condition.getCat_id() != -1 && (Integer.valueOf(s.getCat_id()) != condition.getCat_id()))
			return false;
			
		if(condition.getStuff_status() != -1 && (Integer.valueOf(s.getStuff_status()) != condition.getStuff_status()))
			return false;
		
		if(condition.getOffline() != -1 && (Integer.valueOf(s.getOfflined()) != condition.getOffline()))
			return false;
		
		if(condition.getDivision_id() != -1 && (Integer.valueOf(s.getDivision_id()) != condition.getDivision_id()))
			return false;
		
		if (condition.getStart_price() != -1 && (s.getPrice() < condition.getStart_price() ))
			return false;
		
		if (condition.getEnd_price() != -1 && (s.getPrice() > condition.getEnd_price() ))
			return false;
		
		if(condition.getHas_phone() != -1 && (s.getPhone() == null))
			return false;
		
		
		return result;
	}


	@Override
	public DigResult dig(FirendsDigCondition condition) throws ServiceException {
		
		if (condition == null)
			throw new ServiceException(Constants.EXCEPTION_CONDITION_IS_NULL);
		
		if (condition.getSecondHandPlatformID() == null)
			throw new ServiceException(Constants.EXCEPTION_SECONDHANDPLATFORM_IS_NULL);
		
		ISecondhandManager<?> secondhandManager = SecondhandManagerFactory.get(condition.getSecondHandPlatformID());
		
		if (secondhandManager == null)
			throw new ServiceException(Constants.EXCEPTION_SECONDHANDMANAGER_NOT_EXIST);
		
		DigResult result = new DigResult();

		//基于好友的二手商品挖掘
		if (condition.getPlatformID() != null && condition.getUid() != null
				&& accountZooCache != null && userToAccountZooCache != null)
		{
			diggerFromFriendsCycle(result,condition,secondhandManager);
		}
		else
		{
			List<Secondhand> secondhands = new ArrayList<Secondhand>();
			result.setSecondhands(secondhands);
			Secondhand[] ss = secondhandManager.commonSearch(condition);
			
			if (ss != null && ss.length > 0)
			{
				for(Secondhand s : ss)
				{
					secondhands.add(s);
				}
			}
		}
		
		return result;
		
	}
	
	protected void diggerFromFriendsCycle(DigResult result,FirendsDigCondition condition,
			ISecondhandManager<?> secondhandManager) throws ServiceException
	{
		
		List<User> users = null;
		List<Secondhand> secondhands = new ArrayList<Secondhand>();
		result.setSecondhands(secondhands);
		int counter = 0;
		int round = 0;
		
		AccountZoo az = accountZooCache.get(AppClientUtil.generatePlatformUUID(condition.getPlatformID(), condition.getUid()));
		
		if (az == null)
			return;
		
		Collection<User> relations = az.getAllRelation();
		
		if (az != null && relations != null && relations.size() > 0)
		{
			for(User _user : relations)
			{
				IRelationManager<?,?,?> relationManager = RelationManagerFactory.get(_user.getPlatformId());
				
				if (relationManager == null)
				{
					if (relationManager == null)
						throw new ServiceException(Constants.EXCEPTION_RELATIONMANAGER_NOT_EXIST
								+ " platform id : " + _user.getPlatformId());
				}
				
				if (condition.isIndirectRelation())
				{
					FriendSecondhandQuerySession qs;
					
					if(condition.getContext() != null)
						qs = condition.getContext();
					else
					{
						qs = new FriendSecondhandQuerySession();
						qs.setCursor(0);
						qs.setPageSize(20);
						
						condition.setContext(qs);
					}
					
					if (qs.getRound() > round)
					{
						round += 1;
						continue;
					}
					
					do
					{
						users = relationManager.getIndirectFriendsByUser(_user.getId(),qs);
						
						if (users == null || (users != null && users.size() == 0))
						{
							if (logger.isWarnEnabled())
								logger.warn("user :" + _user.getId() + " has no friends.");
						}
						
						counter = getSecondhandsFromUsers(users,secondhandManager,condition
									,secondhands,counter);
						
						//如果在本轮好友中获得足够数据，下次还需要从这轮用户里面获取
						if(counter >= condition.getQsession().getPageSize())
						{
							qs.setCursor(qs.getCursor() -1);
							qs.clearFilter();
							break;
						}
						
					}
					while(qs.getCursor() > 0);
				}
				else
				{
					users = relationManager.getFriendsByUser(_user.getId(),_user.getId(),null);
					
					if (users == null || (users != null && users.size() == 0))
					{
						if (logger.isWarnEnabled())
							logger.warn("user :" + _user.getId() + " has no friends.");
						continue;
					}
						
					counter = getSecondhandsFromUsers(users,secondhandManager,condition,secondhands,counter);
				}
				
				if (counter >= condition.getQsession().getPageSize())
				{
					break;
				}

				round += 1;
			}
			
			if (condition.getContext() != null)
				condition.getContext().setRound(round);
		}
	}
	
	
	int getSecondhandsFromUsers(List<User> users,ISecondhandManager<?> secondhandManager,
			FirendsDigCondition condition,List<Secondhand> secondhands,
			int counter) throws ServiceException
	{
		int start = 0;
		
		if (condition.getContext() != null && condition.getContext().getUid() != null)
		{
			start  = this.getUserIndex(condition.getContext().getUid(), users);
		}
		
		for(int i = start; i < users.size(); i++)
		{
			User u = users.get(i);
			
			AccountZoo z = userToAccountZooCache.get(AppClientUtil.generatePlatformUUID(u.getPlatformId(), u.getId()));
			
			if (z == null || (z != null && z.getSecondhandAccount() == null))
				continue;
			
			Secondhand[] ss = secondhandManager.list(z.getSecondhandAccount().getId());
			
			if (ss == null || (ss != null && ss.length == 0))
				continue;
			
			int sstart = 0;
			
			if (condition.getContext()!= null && condition.getContext().getSecondhandId() != null)
			{
				sstart = getSecondhandIndex(condition.getContext().getSecondhandId(),ss) + 1;
			}
			
			for(int j = sstart; j < ss.length; j++)
			{
				Secondhand _s = ss[j];
				
				boolean passCheck = checkSecondhandByCondition(_s,condition);
				
				if (passCheck)
				{
					//设置二手拥有者相关信息
					if (condition.isIndirectRelation())
						_s.setIndirect(true);
					else
						_s.setIndirect(false);
					
					if (!secondhandManager.getAppClient().getAuthEntityByUid(z.getSecondhandAccount().getId()).getRelationConfig().isHideSecondhandUserInfo())
						_s.setRelationOwner(u.getName());
					
					secondhands.add(_s);
					counter += 1;
				
					if (counter == condition.getQsession().getPageSize())
					{
						if (condition.getContext() != null)
							condition.getContext().setSecondhandId(_s.getItem_id());
						
						break;
					}
				}
			}
			
			if (counter == condition.getQsession().getPageSize())
			{
				if (condition.getContext() != null)
					condition.getContext().setUid(u.getId());
				
				break;
			}
		}
		
		return counter;
	}
	
	int getUserIndex(String uid,List<User> users)
	{
		int result = 0;
		
		for (User u : users)
		{
			if(u.getId().equals(uid))
				return result;
			
			result += 1;
		}
		
		return 0;
	}
	
	int getSecondhandIndex(String sid,Secondhand[] secondhands)
	{
		int result = 0;
		
		for (Secondhand s : secondhands)
		{
			if(s.getItem_id().equals(sid))
				return result;
			
			result += 1;
		}
		
		return 0;
	}
	
}
