/**
 * 
 */
package com.taobao.exchange.app.client;

import java.util.HashMap;
import java.util.Map;

import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ServiceException;

/**
 * 腾讯客户端实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 下午4:05:14
 *
 */
public class TencentAppClient extends AppClient{

	@Override
	public String api(String userId, String httpMethod, String apiName,
			Map<String, String> headers, Map<String, Object> params)
			throws ServiceException {
		if (openPlatformEntry == null)
			throw new ServiceException(Constants.EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		if (userId != null && authKeeper.take(userId) == null)
			throw new ServiceException(Constants.EXCEPTION_AUTH_USER_NOT_EXIST);
		
		if(apiName == null)
			throw new ServiceException(Constants.EXCEPTION_APINAME_IS_NULL);
		
		String response = null;
		
		if (httpMethod == null)
			httpMethod = "GET";
		else
			httpMethod = httpMethod.toUpperCase();
		
		if (params == null)
			params = new HashMap<String,Object>();
		
		
		String url = new StringBuilder(openPlatformEntry.getApiEntry()).append("/").append(apiName).toString();
		
		params.put("oauth_consumer_key", openPlatformEntry.getAppKey());
		
		if (userId != null && authKeeper.take(userId) != null)
		{		
			params.put(Constants.SYS_PARAMETER_ACCESS_TOKEN, authKeeper.take(userId).getAccessToken());
			params.put("openid", authKeeper.take(userId).getOpenId());
		}
		else
		{
			throw new ServiceException(Constants.EXCEPTION_AUTH_USER_NOT_EXIST);
		}
		
		params.put("clientip", AppClientUtil.getClientIp());
		params.put("oauth_version", "2.a");
		params.put("scope", "all");
		
		if (!params.containsKey("format"))
			params.put("format", "json");
				
		response = AppClientUtil.sendRequest(url,httpMethod,headers,params);
		
		return response;
	}

}
