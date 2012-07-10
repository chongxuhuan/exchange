/**
 * 
 */
package com.taobao.exchange.app;

import java.util.Map;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;



/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-5 ÏÂÎç3:12:25
 *
 */
public class TopAppClient extends AppClient {

	@Override
	public String api(String platformId,String userId,String httpMethod,Map<String, String> headers
			,Map<String,Object> params) throws AppClientException{
		
		if (OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId) == null)
			throw new AppClientException(Constants.CLIENT_EXCEPTION_PLATFORM_NOT_REGISTER);
		
		if (userId != null && authPools.get(userId) == null)
			throw new AppClientException(Constants.CLIENT_EXCEPTION_AUTH_USER_NOT_EXIST);
		
		if(!params.containsKey(Constants.SYS_PARAMETER_APINAME))
			throw new AppClientException(Constants.CLIENT_EXCEPTION_APINAME_IS_NULL);
		
		String response = null;
		
		if (httpMethod == null)
			httpMethod = "GET";
		else
			httpMethod = httpMethod.toUpperCase();
		
		
		if (userId != null)
			params.put(Constants.SYS_PARAMETER_ACCESS_TOKEN, authPools.get(userId).getAccessToken());
		
		if (!params.containsKey(Constants.SYS_PARAMETER_VERSION))
			params.put(Constants.SYS_PARAMETER_VERSION, "2.0");
			
		if (!params.containsKey(Constants.SYS_PARAMETER_FORMAT))
			params.put(Constants.SYS_PARAMETER_FORMAT, "json");
		
		OpenPlatformEntry platformEntry = OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId);
		
		response = AppClientUtil.sendRequest(platformEntry.getApiEntry(),httpMethod,headers,params);
		
		return response;
	}

}
