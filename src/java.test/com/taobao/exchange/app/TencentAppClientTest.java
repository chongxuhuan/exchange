package com.taobao.exchange.app;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.exchange.app.client.IAppClient;
import com.taobao.exchange.app.client.TencentAppClient;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.MemCache;
import com.taobao.exchange.util.ServiceException;

public class TencentAppClientTest {
	
	static IAppClient appclient;
	static ICache<AppAuthEntity> authCache;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		OpenPlatformEntry tencentPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TENCENT);
		
		tencentPlatformEntry.setApiEntry("https://open.t.qq.com/api");
		tencentPlatformEntry.setAppKey("28068");
		tencentPlatformEntry.setAppSecret("c40b30ab26834797bfdbf67c15f3e523");
		tencentPlatformEntry.setAuthEntry("https://open.t.qq.com/cgi-bin/oauth2/access_token");
		tencentPlatformEntry.setCallbackUrl("http://www.mashupshow.com/channel");
		
		authCache = new MemCache<AppAuthEntity>("AppAuth",true);
		
		appclient = new TencentAppClient();
		appclient.setOpenPlatformEntry(tencentPlatformEntry);
		appclient.setAuthCache(authCache);
	}

	@Test
	public void testApi() throws ServiceException {
		String code = TestConstants.TencentAuthCode;
		String openId = TestConstants.TencentOpenId;
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web",null,openId);
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("reqnum", "200");
		params.put("startindex", "0");
		
		String result = appclient.api(authEntity.getUid(), "GET","friends/idollist_s", null, params);
		
		System.out.println(result);
		
		Assert.assertTrue(result.indexOf("data") > 0);
	}

}
