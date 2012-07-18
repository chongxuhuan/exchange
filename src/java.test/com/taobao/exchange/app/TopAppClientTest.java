package com.taobao.exchange.app;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

public class TopAppClientTest {
	
	IAppClient appclient;
	IAuthKeeper authKeeper;

	@Before
	public void setUp() throws Exception {
		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TAOBAO);
		
		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
		topPlatformEntry.setAppKey("12667915");
		topPlatformEntry.setAppSecret("c27b029f3c377d6fa02dfb00d11788f4");
		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
		topPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		authKeeper = new MemAuthKeeper();
			
		appclient = new TopAppClient();
		appclient.setOpenPlatformEntry(topPlatformEntry);
		appclient.setAuthKeeper(authKeeper);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApi() throws ServiceException {
		// first call this url 
		//https://oauth.taobao.com/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=12667915
		
		String code = "0Pu2hjPr3rvgtsEvJRmiraBt77161";
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCode(code, null, null, "web");
		
		authKeeper.store(authEntity.getUid(),authEntity);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fields", "nick,sex,alipay_bind");
		
		String result = appclient.api(authEntity.getUid(), "GET","taobao.user.get", null, params);
		
		System.out.println(result);
		
		Assert.assertTrue(result.indexOf("user_get_response") > 0);
		
	}

}
