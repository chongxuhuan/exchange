/**
 * 
 */
package com.taobao.exchange.relation;


import com.google.gson.Gson;
import com.taobao.exchange.app.SinaAppClient;
import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-11
 *
 */
public class SinaRelationManager implements IRelationManager<SinaAppClient,String,String> {

	SinaAppClient appClient;
	ICache<String,String> relationCache;
	
	@Override
	public User[] getFriendsByUser(String uid) throws AppClientException {
		
		if (appClient == null)
			throw new AppClientException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		User[] result = null;
		String jsonResult = null;
		Gson gson = new Gson();	
		
		if (relationCache != null)
		{
			jsonResult = relationCache.get(uid);
		}
		
		if (jsonResult == null)
		{
			jsonResult = appClient.api(uid, "GET","friendships/friends", null, null);
			
			if (jsonResult != null && relationCache != null)
				relationCache.put(uid, jsonResult);
			
			if (jsonResult == null || (jsonResult != null && jsonResult.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
			{
				throw new AppClientException(jsonResult);
			}
			
			GetUsersResponse usersResponse = gson.fromJson(jsonResult, GetUsersResponse.class);
			
			result = usersResponse.getUsers();
				
		} 
		else
		{
			result = gson.fromJson(jsonResult, User[].class);
		}
		
		return result;
	}

	
	@Override
	public User[] getIndirectFriendsByUser(String uid) throws AppClientException{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setAppClient(SinaAppClient appClient) {
		this.appClient = appClient;
	}

	@Override
	public void setRelationCache(ICache<String, String> relationCache) {
		this.relationCache = relationCache;
	}

}
