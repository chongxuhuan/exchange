package com.taobao.exchange.app;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.app.client.IAppClient;
import com.taobao.exchange.app.client.TopAppClient;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.MemCache;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

public class TopAppClientTest {
	
	IAppClient appclient;
	ICache<AppAuthEntity> authCache;

	@Before
	public void setUp() throws Exception {
		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TAOBAO);
		
		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
		topPlatformEntry.setAppKey("12667915");
		topPlatformEntry.setAppSecret("c27b029f3c377d6fa02dfb00d11788f4");
		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
		topPlatformEntry.setCallbackUrl("www.mashupshow.com/channel");
		
		authCache = new MemCache<AppAuthEntity>("AppAuth",true);
			
		appclient = new TopAppClient();
		appclient.setOpenPlatformEntry(topPlatformEntry);
		appclient.setAuthCache(authCache);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApi() throws ServiceException {
		
		String code = TestConstants.TOPAuthCode;
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web",null,null);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fields", "nick,sex,alipay_bind");
		
		String result = appclient.api(authEntity.getUid(), "GET","taobao.user.get", null, params);
		
		System.out.println(result);
		
		Assert.assertTrue(result.indexOf("user_get_response") > 0);
		
	}

}
