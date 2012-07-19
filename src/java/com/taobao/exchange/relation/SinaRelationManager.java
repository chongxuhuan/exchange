/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taobao.exchange.app.SinaAppClient;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;

/**
 * 新浪关系管理实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-11
 *
 */
public class SinaRelationManager implements IRelationManager<SinaAppClient,String,String> {

	private static final Log logger = LogFactory.getLog(SinaRelationManager.class);
	
	SinaAppClient appClient;
	ICache<String,String> relationCache;
	
	/* 
	 * 获得直接好友
	 */
	@Override
	public List<User> getFriendsByUser(String sessionUid,String uid) throws ServiceException {
		
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		List<User> result = new ArrayList<User>();
		String jsonResult = null;
		Gson gson = new Gson();	
		
		if (relationCache != null)
		{
			jsonResult = relationCache.get(uid);
		}
		
		if (jsonResult == null)
		{
			Map<String,Object> params = new HashMap<String,Object>();
			GetUsersResponse usersResponse;
			int cursor = 0;
			params.put("trim_status", 0);
			params.put("count", 200);
			
			do
			{
				params.put("cursor", cursor);
				params.put("uid", uid);
				
				if (appClient.getAuthKeeper().take(uid)
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
				
				usersResponse = gson.fromJson(jsonResult, GetUsersResponse.class);
				
				if (usersResponse.getTotal_number() > 0)
				{
					for (User u : usersResponse.getUsers())
					{
						u.setPlatformId(appClient.getOpenPlatformEntry().getId());
						result.add(u);
					}
					
					cursor = usersResponse.getNext_cursor();
				}
			}
			while(usersResponse != null && cursor > 0);
			
			if (result.size() > 0)
				relationCache.put(uid,gson.toJson(result));
				
		} 
		else
		{
			result = gson.fromJson(jsonResult, new TypeToken<List<User>>(){}.getType());
		}
		
		return result;
	}

	
	/* 
	 * 返回当前用户的间接好友，就是朋友的朋友(支持到两级)
	 */
	@Override
	public List<User> getIndirectFriendsByUser(String uid) throws ServiceException{
		
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		List<User> result = new ArrayList<User>();
		
		List<User> friends = this.getFriendsByUser(uid,uid);
		
		int count = 0;
		int member = 0;
		
		for(User u : friends)
		{
			List<User>  df = this.getFriendsByUser(uid,u.getId());
			
			if (df != null)
				result.addAll(df);
			
			count += 1;
			member += df.size();
			
			if (logger.isInfoEnabled())
				logger.info("count : " + count + ", member : " + member);
		}	
		return result;
		
	}


	@Override
	public void setAppClient(SinaAppClient appClient) {
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
	
	

}
