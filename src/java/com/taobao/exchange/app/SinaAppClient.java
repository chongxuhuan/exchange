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
 * 新浪应用客户端实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-5
 *
 */
public class SinaAppClient extends AppClient{

	@Override
	public String api(String userId,String httpMethod,String apiName,Map<String, String> headers
			,Map<String,Object> params) throws AppClientException{

		if (openPlatformEntry == null)
			throw new AppClientException(Constants.EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		if (userId != null && authKeeper.take(userId) == null)
			throw new AppClientException(Constants.EXCEPTION_AUTH_USER_NOT_EXIST);
		
		if(apiName == null)
			throw new AppClientException(Constants.EXCEPTION_APINAME_IS_NULL);
		
		String response = null;
		
		if (httpMethod == null)
			httpMethod = "GET";
		else
			httpMethod = httpMethod.toUpperCase();
		
		if (params == null)
			params = new HashMap<String,Object>();
		
		
		String url = new StringBuilder(openPlatformEntry.getApiEntry()).append("/2/").append(apiName).append(".json").toString();
		
		if (userId != null)
		{		
			params.put(Constants.SYS_PARAMETER_ACCESS_TOKEN, authKeeper.take(userId).getAccessToken());
			params.put(Constants.SYS_PARAMETER_UID, userId);
		}
				
		response = AppClientUtil.sendRequest(url,httpMethod,headers,params);
		
		return response;

	}

}
