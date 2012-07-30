/**
 * 
 */
package com.taobao.exchange.relation.tencent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taobao.exchange.app.client.TencentAppClient;
import com.taobao.exchange.relation.AbstractRelationManager;
import com.taobao.exchange.relation.User;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午4:18:48
 *
 */
public class TencentRelationManager extends AbstractRelationManager<TencentAppClient>{

	private static final Log logger = LogFactory.getLog(TencentRelationManager.class);

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
			TencentGetUsersResponse usersResponse;
			int cursor = 0;
			params.put("name", uid);
			
			if (session != null)
				params.put("reqnum", session.getPageSize());
			else
				params.put("reqnum", 30);
			
			do
			{
				if (session !=  null)
					params.put("startindex", session.getCursor());
				else
					params.put("startindex", cursor);
				
				
				if (appClient.getAuthEntityByUid(sessionUid)
						.getRelationConfig().getRelationLevel() == Constants.RELATION_LEVEL_ONEWAY)
				{
					jsonResult = appClient.api(sessionUid, "GET","friends/idollist_s", null, params);
				}
				else
				{
					jsonResult = appClient.api(sessionUid, "GET","friends/mutual_list", null, params);
				}

				if (jsonResult == null || (jsonResult != null && jsonResult.indexOf("\"errcode\":0") < 0))
				{
					throw new ServiceException(jsonResult);
				}
				
				jsonResult = jsonResult.substring(jsonResult.indexOf("\"data\":")+"\"data\":".length(),jsonResult.lastIndexOf("}") );
				jsonResult = jsonResult.substring(0,jsonResult.lastIndexOf("}")+1);
				
				usersResponse = gson.fromJson(jsonResult, TencentGetUsersResponse.class);
				
				if (usersResponse.getInfo() != null && usersResponse.getInfo().length > 0)
				{
					for (TencentUser u : usersResponse.getInfo())
					{
						User iu = new User();
						iu.setPlatformId(appClient.getOpenPlatformEntry().getId());
						iu.setId(u.getName());
						iu.setName(u.getNick());
						result.add(iu);
					}
					
					if (session != null)
					{
						session.setCursor(usersResponse.getNextstartpos());
						break;
					}
					else
						cursor = usersResponse.getNextstartpos();
				}
			}
			while(usersResponse != null && usersResponse.getHasnext() == 0 
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

}
