/**
 * 
 */
package com.taobao.exchange.relation.renren;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taobao.exchange.app.client.RenRenAppClient;
import com.taobao.exchange.relation.AbstractRelationManager;
import com.taobao.exchange.relation.User;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.FriendSecondhandQuerySession;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午10:18:18
 *
 */
public class RenRenRelationManager extends AbstractRelationManager<RenRenAppClient> {
	
	private static final Log logger = LogFactory.getLog(RenRenRelationManager.class);

	@Override
	public List<User> getFriendsByUser(String sessionUid, String uid,
			QuerySession session) throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		List<User> result = new ArrayList<User>();
		
		String jsonResult = null;
		Gson gson = new Gson();	
		
		if (relationCache != null && session == null)
		{
			jsonResult = relationCache.get(uid);
		}
		
		if (jsonResult == null)
		{
			Map<String,Object> params = new HashMap<String,Object>();
			User[] usersResponse;
			int cursor = 1;
			
			if (session != null)
				params.put("count", session.getPageSize());
			
			do
			{
				if (session !=  null)
					params.put("page", session.getCursor());
				else
					params.put("page", cursor);
				
				jsonResult = appClient.api(sessionUid, "POST","friends.getFriends", null, params);
				
				if (jsonResult == null || (jsonResult != null && jsonResult.indexOf("\"error_code\":") >= 0))
				{
					throw new ServiceException(jsonResult);
				}
				
				usersResponse = gson.fromJson(jsonResult, User[].class);
				
				if (usersResponse != null && usersResponse.length > 0)
				{
					for (User u : usersResponse)
					{
						u.setPlatformId(appClient.getOpenPlatformEntry().getId());
						result.add(u);
					}
					
					if (session != null)
					{
						session.setCursor(cursor +1);
						break;
					}
					else
						cursor = cursor +1;
				}
			}
			while(usersResponse != null && usersResponse != null
					&& usersResponse.length > 0
					&& result.size() < Constants.MAX_RELATION_COUNT);
			
			if (result.size() >= Constants.MAX_RELATION_COUNT)
			{
				logger.error("user : " + uid + " relation out of limit!");
			}
			
			if (relationCache != null && result.size() > 0 && session == null)
				relationCache.put(uid,gson.toJson(result));
		}
		else
		{
			result = gson.fromJson(jsonResult, new TypeToken<List<User>>(){}.getType());
		}
		
		return result;
	}
	
	@Override
	public List<User> getIndirectFriendsByUser(String uid, FriendSecondhandQuerySession session)
			throws ServiceException 
	{
		throw new java.lang.UnsupportedOperationException("Renren not support getIndirectFriendsByUser method");
	}

}
