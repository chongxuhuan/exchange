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
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午11:25:37
 *
 */
public class RenRenAppClient extends AppClient{

	@Override
	public String api(String userId, String httpMethod, String apiName,
			Map<String, String> headers, Map<String, Object> params)
			throws ServiceException {
		if (openPlatformEntry == null)
			throw new ServiceException(Constants.EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		if (userId != null && getAuthEntityByUid(userId) == null)
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
		
		
		String url = openPlatformEntry.getApiEntry();
		
		params.put("method", apiName);
		params.put("v", "1.0");
		
		if (userId != null && getAuthEntityByUid(userId) != null)
		{		
			params.put(Constants.SYS_PARAMETER_ACCESS_TOKEN, getAuthEntityByUid(userId).getAccessToken());
		}
		else
		{
			throw new ServiceException(Constants.EXCEPTION_AUTH_USER_NOT_EXIST);
		}
		
		if (!params.containsKey("format"))
			params.put("format", "json");
		
		try
		{
			params.put("sig",AppClientUtil
				.signatureRenRenVersion(params, openPlatformEntry.getAppSecret(),"sig"));
		}
		catch(Exception ex)
		{
			throw new ServiceException(ex);
		}
				
		response = AppClientUtil.sendRequest(url,httpMethod,headers,params);
		
		return response;
	}

}
