/**
 * 
 */
package com.taobao.exchange.app.client;

import java.util.HashMap;
import java.util.Map;

import com.taobao.exchange.util.ServiceException;
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
			,Map<String,Object> params) throws ServiceException{
		
		if (openPlatformEntry == null)
			throw new ServiceException(Constants.EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		if (userId != null && getAuthEntityByUid(userId) == null)
			throw new ServiceException(Constants.EXCEPTION_AUTH_USER_NOT_EXIST);
		
		if(apiName == null)
			throw new ServiceException(Constants.EXCEPTION_APINAME_IS_NULL);
		
		if (params == null)
			params = new HashMap<String,Object>();
		
		String response = null;
		
		if (httpMethod == null)
			httpMethod = "GET";
		else
			httpMethod = httpMethod.toUpperCase();
		
		params.put(Constants.SYS_PARAMETER_APINAME, apiName);
		
		if (!params.containsKey(Constants.SYS_PARAMETER_VERSION))
			params.put(Constants.SYS_PARAMETER_VERSION, "2.0");
			
		if (!params.containsKey(Constants.SYS_PARAMETER_FORMAT))
			params.put(Constants.SYS_PARAMETER_FORMAT, "json");
		
		if (userId != null)
			params.put(Constants.SYS_PARAMETER_ACCESS_TOKEN, getAuthEntityByUid(userId).getAccessToken());
		else
		{
			//这里还要用普通的签名方式
			params.put(Constants.SYS_PARAMETER_APPKEY, openPlatformEntry.getAppKey());
			params.put(Constants.SYS_PARAMETER_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
			params.put(Constants.SYS_PARAMETER_SIGNMETHOD, "md5");
			
			try
			{
				params.put(Constants.SYS_PARAMETER_SIGN,AppClientUtil
						.signature(params, openPlatformEntry.getAppSecret(), false, Constants.SYS_PARAMETER_SIGN));
			}
			catch(Exception ex)
			{
				throw new ServiceException(ex);
			}
			
		}
		
		response = AppClientUtil.sendRequest(openPlatformEntry.getApiEntry(),httpMethod,headers,params);
		
		return response;
	}

}
