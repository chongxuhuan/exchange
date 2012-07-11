/**
 * 
 */
package com.taobao.exchange.app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	
	private static final Log logger = LogFactory.getLog(AppClient.class);
	
	ConcurrentMap<String,AppAuthEntity> authPools;
	OpenPlatformEntry openPlatformEntry;
	
	public AppClient()
	{
		super();
		
		authPools = new ConcurrentHashMap<String,AppAuthEntity>();
	}
		
	/**
	 * 获取内在平台信息
	 * @return
	 */
	public OpenPlatformEntry getOpenPlatformEntry()
	{
		return openPlatformEntry;
	}
	
	
	/**
	 * 设置平台信息
	 * @param 平台信息
	 */
	public void setOpenPlatformEntry(OpenPlatformEntry openPlatformEntry)
	{
		this.openPlatformEntry = openPlatformEntry;
	}

	public void addAuthToClient(AppAuthEntity auth)
	{
		authPools.put(auth.getUid(), auth);
	}
	
	public AppAuthEntity getAccessTokenByCode(String code,String scope,String state,String view) throws AppClientException
	{
		if (openPlatformEntry == null)
			throw new AppClientException(Constants.EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		AppAuthEntity entity = new AppAuthEntity();
				
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put(Constants.SYS_AUTH_PARAMETER_CLIENTID, openPlatformEntry.getAppKey());
		params.put(Constants.SYS_AUTH_PARAMETER_SECRETCODE, openPlatformEntry.getAppSecret());
		
		if (openPlatformEntry.getCallbackUrl() != null)
			params.put(Constants.SYS_AUTH_PARAMETER_REDIRECT_URI, openPlatformEntry.getCallbackUrl());
		
		params.put(Constants.SYS_AUTH_PARAMETER_GRANT,"authorization_code");
		params.put(Constants.SYS_AUTH_PARAMETER_CODE, code);
		
		if (scope != null)
			params.put(Constants.SYS_AUTH_PARAMETER_SCOPE, scope);
		
		if (state != null)
			params.put(Constants.SYS_AUTH_PARAMETER_STATE, state);
		
		if (view != null)
			params.put(Constants.SYS_AUTH_PARAMETER_VIEW, view);
		
		
		String result = AppClientUtil.sendRequest(openPlatformEntry.getAuthEntry()
				, "POST", null, params);
		
		
		if (result == null || (result != null && result.indexOf("access_token") < 0))
			return null;
		else
		{
			entity.loadAuthInfoFromJsonString(result);
			 
			logger.info(result);
		}
		
		return entity;
	}

}
