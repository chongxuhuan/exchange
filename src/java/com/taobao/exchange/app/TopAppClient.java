/**
 * 
 */
package com.taobao.exchange.app;

import java.util.HashMap;
import java.util.Map;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;



/**
 * 淘宝开放平台实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-5
 *
 */
public class TopAppClient extends AppClient {

	@Override
	public String api(String userId,String httpMethod,String apiName,Map<String, String> headers
			,Map<String,Object> params) throws AppClientException{
		
		if (openPlatformEntry == null)
			throw new AppClientException(Constants.CLIENT_EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		if (userId != null && authPools.get(userId) == null)
			throw new AppClientException(Constants.CLIENT_EXCEPTION_AUTH_USER_NOT_EXIST);
		
		if(apiName == null)
			throw new AppClientException(Constants.CLIENT_EXCEPTION_APINAME_IS_NULL);
		
		if (params == null)
			params = new HashMap<String,Object>();
		
		String response = null;
		
		if (httpMethod == null)
			httpMethod = "GET";
		else
			httpMethod = httpMethod.toUpperCase();
		
		params.put(Constants.SYS_PARAMETER_APINAME, apiName);
		
		if (userId != null)
			params.put(Constants.SYS_PARAMETER_ACCESS_TOKEN, authPools.get(userId).getAccessToken());
		
		if (!params.containsKey(Constants.SYS_PARAMETER_VERSION))
			params.put(Constants.SYS_PARAMETER_VERSION, "2.0");
			
		if (!params.containsKey(Constants.SYS_PARAMETER_FORMAT))
			params.put(Constants.SYS_PARAMETER_FORMAT, "json");
		
		response = AppClientUtil.sendRequest(openPlatformEntry.getApiEntry(),httpMethod,headers,params);
		
		return response;
	}

}
