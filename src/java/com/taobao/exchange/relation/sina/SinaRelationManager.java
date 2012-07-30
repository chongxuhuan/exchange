/**
 * 
 */
package com.taobao.exchange.relation.sina;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taobao.exchange.app.SinaAppClient;
import com.taobao.exchange.relation.AbstractRelationManager;
import com.taobao.exchange.relation.User;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

/**
 * 新浪关系管理实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-11
 *
 */
public class SinaRelationManager extends AbstractRelationManager<SinaAppClient> {

	private static final Log logger = LogFactory.getLog(SinaRelationManager.class);
	
	/* 
	 * 获得直接好友
	 */
	@Override
	public List<User> getFriendsByUser(String sessionUid,String uid,QuerySession session) throws ServiceException {
		
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
			SinaGetUsersResponse usersResponse;
			int cursor = 0;
			params.put("trim_status", 0);
			
			if (session != null)
				params.put("count", session.getPageSize());
			else
				params.put("count", 200);
			
			do
			{
				if (session !=  null)
					params.put("cursor", session.getCursor());
				else
					params.put("cursor", cursor);
				
				params.put("uid", uid);
				
				
				if (appClient.getAuthEntityByUid(sessionUid)
						.getRelationConfig().getRelationLevel() == Constants.RELATION_LEVEL_ONEWAY)
				{
					jsonResult = appClient.api(sessionUid, "GET","friendships/friends", null, params);
				}
				else
				{
					jsonResult = appClient.api(sessionUid, "GET","friendships/friends/bilateral", null, params);
				}

				if (jsonResult == null || (jsonResult != null && jsonResult.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
				{
					throw new ServiceException(jsonResult);
				}
				
				usersResponse = gson.fromJson(jsonResult, SinaGetUsersResponse.class);
				
				if (usersResponse.getTotal_number() > 0)
				{
					for (User u : usersResponse.getUsers())
					{
						u.setPlatformId(appClient.getOpenPlatformEntry().getId());
						result.add(u);
					}
					
					if (session != null)
					{
						session.setCursor(usersResponse.getNext_cursor());
						break;
					}
					else
						cursor = usersResponse.getNext_cursor();
				}
			}
			while(usersResponse != null && cursor > 0 && result.size() < Constants.MAX_RELATION_COUNT);
			
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

}
