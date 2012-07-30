/**
 * 
 */
package com.taobao.exchange.relation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.exchange.app.client.IAppClient;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午9:54:58
 *
 */
public abstract class AbstractRelationManager<C extends IAppClient> implements
		IRelationManager<C,String,String> {
	
	private static final Log logger = LogFactory.getLog(AbstractRelationManager.class);
	
	protected ICache<String,String> relationCache;
	protected C appClient;
	
	@Override
	public void setAppClient(C appClient) {
		this.appClient = appClient;
	}
	
	@Override
	public void setRelationCache(ICache<String, String> relationCache) {
		this.relationCache = relationCache;
	}

	@Override
	public ICache<String, String> getRelationCache() {
		return relationCache;
	}
	
	@Override
	public List<User> getIndirectFriendsByUser(String uid, QuerySession session)
			throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		List<User> result = new ArrayList<User>();
		
		//普通好友一次获取出来
		List<User> friends = this.getFriendsByUser(uid,uid,null);
		
		int count = 0;
		int member = 0;
		
		if (session == null)
		{
			for(User u : friends)
			{
				List<User>  df = this.getFriendsByUser(uid,u.getId(),null);
				
				if (df != null)
					result.addAll(df);
				
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
			for(User u : friends)
			{
				count += 1;
				
				if (count <= session.getCursor() * session.getPageSize())
				{
					continue;
				}
				else
				{
					if (count > (session.getCursor()+1) * session.getPageSize())
					{
						session.setCursor(session.getCursor()+ 1);
						break;
					}
				}
				
				List<User>  df = this.getFriendsByUser(uid,u.getId(),null);
				
				if (df != null)
				{
					for(User uc : df)
					{
						if (!session.needFilter(uc.getId()))
						{
							result.add(uc);
							member +=1;
							session.addFilterEntry(uc.getId(), uc.getId());
						}
					}
				}
				
				if (member > Constants.MAX_INDIRECT_RELATION_COUNT)
				{
					logger.error("user :" +uid + "indirectRelation out of limit.");
					break;
				}
				
				if (logger.isInfoEnabled())
					logger.info("count : " + (count - session.getCursor() * session.getPageSize()) + ", member : " + member);

			}
			
			if (count <= (session.getCursor()) * session.getPageSize())
			{
				session.setCursor(0);
			}
			
		}
		
		
		return result;
	}

}
