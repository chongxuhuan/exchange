/**
 * 
 */
package com.taobao.exchange.app;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;

/**
 * 应用客户端抽象类实现，支持多个开放平台
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9
 *
 */
public abstract class AppClient implements IAppClient{
	
	private static final Log logger = LogFactory.getLog(AppClient.class);
	
	OpenPlatformEntry openPlatformEntry;//开放平台身份
	IAuthKeeper authKeeper;//不同的client用于保存某一开放平台这个应用的授权信息

	public void setAuthKeeper(IAuthKeeper authKeeper) {
		this.authKeeper = authKeeper;
	}

	public OpenPlatformEntry getOpenPlatformEntry()
	{
		return openPlatformEntry;
	}
	
	public void setOpenPlatformEntry(OpenPlatformEntry openPlatformEntry)
	{
		this.openPlatformEntry = openPlatformEntry;
	}

	public AppAuthEntity getAuthEntityByUid(String uid) throws ServiceException
	{
		if (authKeeper != null)
			return authKeeper.take(uid);
		else
			throw new ServiceException("authKeeper is null.");
	}
	

	/**
	 * Oauth2的server流程第二步，用code换access_token
	 * @param code
	 * @param scope
	 * @param state
	 * @param view
	 * @param cbAppend
	 * @return
	 * @throws ServiceException
	 */
	public AppAuthEntity getAccessTokenByCodeAndStore(String code,String scope,String state,String view
			,String cbAppend) throws ServiceException
	{
		if (openPlatformEntry == null)
			throw new ServiceException(Constants.EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		AppAuthEntity entity = new AppAuthEntity();
		entity.setPlatformId(openPlatformEntry.getId());
				
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put(Constants.SYS_AUTH_PARAMETER_CLIENTID, openPlatformEntry.getAppKey());
		params.put(Constants.SYS_AUTH_PARAMETER_SECRETCODE, openPlatformEntry.getAppSecret());
		
		if (openPlatformEntry.getCallbackUrl() != null)
			if (cbAppend != null)
				params.put(Constants.SYS_AUTH_PARAMETER_REDIRECT_URI, openPlatformEntry.getCallbackUrl()+cbAppend);
			else
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
		{
			if (result != null)
				throw new ServiceException(result);
			else
				throw new ServiceException("getAccessTokenByCode return null!");
		}
		else
		{
			entity.loadAuthInfoFromString(result);
			authKeeper.store(entity); 
			
			logger.info(result);
		}
		
		return entity;
	}

}
