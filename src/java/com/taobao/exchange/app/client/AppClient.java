/**
 * 
 */
package com.taobao.exchange.app.client;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.util.ICache;
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
	private ICache<AppAuthEntity> authCache;//不同的client用于保存某一开放平台这个应用的授权信息

	public void setAuthCache(ICache<AppAuthEntity> authCache) {
		this.authCache = authCache;
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
		if (authCache != null)
			return authCache.get(AppClientUtil.generatePlatformUUID(openPlatformEntry.getId(),uid));
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
			,String cbAppend,String openId) throws ServiceException
	{
		if (openPlatformEntry == null)
			throw new ServiceException(Constants.EXCEPTION_PLATFORMENTRY_NOT_EXIST);
		
		AppAuthEntity entity = new AppAuthEntity();
		entity.setPlatformId(openPlatformEntry.getId());
		entity.setOpenId(openId);
				
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
			entity.updateObjectFormString(result);
			authCache.put(AppClientUtil.generatePlatformUUID(openPlatformEntry.getId(), entity.getUid()), entity); 
			
			//针对新浪特殊处理,获得用户名
			if (entity.getPlatformId().endsWith(Constants.PLATFORM_ID_SINA))
			{
				String r = api(entity.getUid(), "GET","users/show", null, null);
				
				if(r.indexOf("\"name\":") > 0)
				{
					r = r.substring(r.indexOf("\"name\":") + "\"name\":".length()+1);
					r = r.substring(0,r.indexOf("\""));
					
					entity.setNick(r);
				}
			}
			
			logger.info(result);
		}
		
		return entity;
	}

}
