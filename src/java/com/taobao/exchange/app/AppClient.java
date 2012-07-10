/**
 * 
 */
package com.taobao.exchange.app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;

/**
 * 应用客户端抽象类实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9
 *
 */
public abstract class AppClient implements IAppClient{
	
	ConcurrentMap<String,AppAuthEntity> authPools;
	
	public AppClient()
	{
		super();
		
		authPools = new ConcurrentHashMap<String,AppAuthEntity>();
	}

	public void addAuthToClient(AppAuthEntity auth)
	{
		authPools.put(auth.getUid(), auth);
	}
	
	public AppAuthEntity getAccessTokenByCode(String platformId,String code,String scope,String state,String view) throws AppClientException
	{
		if (OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId) == null)
			throw new AppClientException(Constants.CLIENT_EXCEPTION_PLATFORM_NOT_REGISTER);
		
		AppAuthEntity entity = new AppAuthEntity();
		entity.setPlatformId(platformId);
				
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put(Constants.SYS_AUTH_PARAMETER_CLIENTID, OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId).getAppKey());
		params.put(Constants.SYS_AUTH_PARAMETER_SECRETCODE, OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId).getAppSecret());
		
		if (OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId).getCallbackUrl() != null)
			params.put(Constants.SYS_AUTH_PARAMETER_REDIRECT_URI, OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId).getCallbackUrl());
		
		params.put(Constants.SYS_AUTH_PARAMETER_GRANT,"authorization_code");
		params.put(Constants.SYS_AUTH_PARAMETER_CODE, code);
		
		if (scope != null)
			params.put(Constants.SYS_AUTH_PARAMETER_SCOPE, scope);
		
		if (state != null)
			params.put(Constants.SYS_AUTH_PARAMETER_STATE, state);
		
		if (view != null)
			params.put(Constants.SYS_AUTH_PARAMETER_VIEW, view);
		
		
		String result = AppClientUtil.sendRequest(OpenPlatformManager.getOpenPlatformEntryFromPoolsById(platformId).getAuthEntry()
				, "POST", null, params);
		
		
		if (result == null || (result != null && result.indexOf("access_token") < 0))
			return null;
		else
			entity.loadAuthInfoFromJsonString(result);
		
		return entity;
	}

}
