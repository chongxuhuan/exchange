/**
 * 
 */
package com.taobao.exchange.dig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.exchange.relation.AccountZoo;
import com.taobao.exchange.relation.IRelationManager;
import com.taobao.exchange.relation.RelationManagerFactory;
import com.taobao.exchange.relation.User;
import com.taobao.exchange.secondhand.ISecondhandManager;
import com.taobao.exchange.secondhand.Secondhand;
import com.taobao.exchange.secondhand.SecondhandManagerFactory;
import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class FriendsDigger implements ISecondhandDigger<FirendsDigCondition> {
	
	private static final Log logger = LogFactory.getLog(FriendsDigger.class);
	
	private ICache<String,String> contextCache;
	private ICache<String,AccountZoo> accountZooCache;
	private ICache<String,AccountZoo> relationAccountZooCache;
	
	public void setRelationAccountZooCache(
			ICache<String, AccountZoo> relationAccountZooCache) {
		this.relationAccountZooCache = relationAccountZooCache;
	}


	public void setAccountZooCache(ICache<String, AccountZoo> accountZooCache) {
		this.accountZooCache = accountZooCache;
	}


	public void setContextCache(ICache<String, String> contextCache) {
		this.contextCache = contextCache;
	}
	
	
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
	public DigResult dig(FirendsDigCondition condition) throws AppClientException {
		
		if (condition == null)
			throw new AppClientException(Constants.EXCEPTION_CONDITION_IS_NULL);
		
		if (condition.getSecondHandPlatformID() == null)
			throw new AppClientException(Constants.EXCEPTION_SECONDHANDPLATFORM_IS_NULL);
		
		ISecondhandManager<?> secondhandManager = SecondhandManagerFactory.get(condition.getSecondHandPlatformID());
		
		if (secondhandManager == null)
			throw new AppClientException(Constants.EXCEPTION_SECONDHANDMANAGER_NOT_EXIST);
		
		DigResult result = new DigResult();
		String qSession;
		
		//这里设计还需要考虑，暂时留个口子
		String session_user = null;
		String session_secondhand = null;
		
		if (condition.getQuery_session() == null)
			qSession = new StringBuilder().append(UUID.randomUUID().toString())
				.append("--").append(System.currentTimeMillis()).toString();
		else
		{
			qSession = condition.getQuery_session();
		
			//get session detail
			if (contextCache != null && contextCache.get(qSession) != null)
			{
				String[] cs = StringUtils.splitByWholeSeparator(contextCache.get(qSession), Constants.CONTEXT_SPLIT);
				
				for(String c : cs)
				{
					if (c.startsWith(Constants.CONTEXT_SECONDHAND))
					{
						session_secondhand = c.substring(Constants.CONTEXT_SECONDHAND.length());
						continue;
					}
					
					if (c.startsWith(Constants.CONTEXT_USER))
					{
						session_user = c.substring(Constants.CONTEXT_USER.length());
						continue;
					}
				}
			}
		}

		if (condition.getSecondHandPlatformID() != null && condition.getSecondHandUID() != null
				&& accountZooCache != null && relationAccountZooCache != null)
		{
			
			List<User> users = null;
			List<Secondhand> secondhands = new ArrayList<Secondhand>();
			StringBuilder queryContext = new StringBuilder();
			result.setSecondhands(secondhands);
			int counter = 0;
			
			AccountZoo az = accountZooCache.get(AppClientUtil.generatePlatformUUID(condition.getSecondHandPlatformID(), condition.getSecondHandUID()));
			
			if (az != null && az.getRelationAccounts() != null && az.getRelationAccounts().size() > 0)
			{
				for(User _user : az.getRelationAccounts())
				{
					IRelationManager<?,?,?> relationManager = RelationManagerFactory.get(_user.getPlatformId());
					
					if (relationManager == null)
					{
						if (relationManager == null)
							throw new AppClientException(Constants.EXCEPTION_RELATIONMANAGER_NOT_EXIST
									+ " platform id : " + _user.getPlatformId());
					}
					
					if (condition.isIndirectRelation())
						users = relationManager.getIndirectFriendsByUser(_user.getId());
					else
						users = relationManager.getFriendsByUser(_user.getId(),_user.getId());
					
					if (users == null || (users != null && users.size() == 0))
						return result;

					for(User u : users)
					{
						AccountZoo z = relationAccountZooCache.get(AppClientUtil.generatePlatformUUID(u.getPlatformId(), u.getId()));
						
						if (z == null)
							continue;
						
						Secondhand[] ss = secondhandManager.list(z.getSecondHandUID());
						
						if (ss == null || (ss != null && ss.length == 0))
							continue;
						
						for(Secondhand _s : ss)
						{
							boolean passCheck = checkSecondhandByCondition(_s,condition);
							
							if (passCheck)
							{
								secondhands.add(_s);
								counter += 1;
							
								if (counter == condition.getPage_size())
								{
									queryContext.append(Constants.CONTEXT_SECONDHAND).append(_s.getItem_id());
									queryContext.append(Constants.CONTEXT_SPLIT);
									break;
								}
							}
						}
						
						if (counter == condition.getPage_size())
						{
							queryContext.append(Constants.CONTEXT_USER).append(u.getId());
							queryContext.append(Constants.CONTEXT_SPLIT);
							break;
						}
					}
				}
			}
			
			
			if (contextCache != null)
			{
				if (queryContext.length() > 0)
				{
					contextCache.put(qSession, queryContext.toString());
					result.setQuery_session(qSession);
				}
			}
			else
				logger.warn("ContextCache is null!!!");
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

}
