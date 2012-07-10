package com.taobao.exchange.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.util.AppClientException;

public class SinaAppClientTest {
	
	IAppClient appclient;

	@Before
	public void setUp() throws Exception {
		OpenPlatformEntry sinaPlatformEntry = new OpenPlatformEntry();
		sinaPlatformEntry.setApiEntry("https://api.weibo.com");
		sinaPlatformEntry.setAppKey("845619194");
		sinaPlatformEntry.setAppSecret("3a69bb60ed46d0ceab5f0457657ac0f9");
		sinaPlatformEntry.setAuthEntry("https://api.weibo.com/oauth2/access_token");
		sinaPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		appclient = new SinaAppClient();
		appclient.setOpenPlatformEntry(sinaPlatformEntry);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApi() throws AppClientException {
		//https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=845619194
		
		String code = "8411ccb4eee101c9d0b432cfef5c2b11";
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCode(code, null, null, "web");
		
		appclient.addAuthToClient(authEntity);
		
		String result = appclient.api(authEntity.getUid(), "GET","friendships/friends/bilateral", null, null);
		
		System.out.println(result);
	}

}
