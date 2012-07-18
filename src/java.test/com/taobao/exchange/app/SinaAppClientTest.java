package com.taobao.exchange.app;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

public class SinaAppClientTest {
	
	IAppClient appclient;
	IAuthKeeper authKeeper;

	@Before
	public void setUp() throws Exception {
		OpenPlatformEntry sinaPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_SINA);
		sinaPlatformEntry.setApiEntry("https://api.weibo.com");
		sinaPlatformEntry.setAppKey("845619194");
		sinaPlatformEntry.setAppSecret("3a69bb60ed46d0ceab5f0457657ac0f9");
		sinaPlatformEntry.setAuthEntry("https://api.weibo.com/oauth2/access_token");
		sinaPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		authKeeper = new MemAuthKeeper();
		
		appclient = new SinaAppClient();
		appclient.setOpenPlatformEntry(sinaPlatformEntry);
		appclient.setAuthKeeper(authKeeper);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApi() throws ServiceException {
		//https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=845619194
		
		String code = "7fff6db5ef9080fe40cd2021fa34f595";
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCode(code, null, null, "web");
		
		authKeeper.store(authEntity.getUid(), authEntity);
		
		String result = appclient.api(authEntity.getUid(), "GET","friendships/friends", null, null);
		
		System.out.println(result);
		
		Assert.assertTrue(result.indexOf("users") > 0);
	}

}
