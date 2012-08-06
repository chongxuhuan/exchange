package com.taobao.exchange.app;



import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.app.client.IAppClient;
import com.taobao.exchange.app.client.RenRenAppClient;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.MemCache;

public class AppClientTest {
	
	IAppClient appclient;

	@Before
	public void setUp() throws Exception {
//		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TAOBAO);
//				
//		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
//		topPlatformEntry.setAppKey("12667915");
//		topPlatformEntry.setAppSecret("c27b029f3c377d6fa02dfb00d11788f4");
//		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
//		topPlatformEntry.setCallbackUrl("www.mashupshow.com/channel");
		
//		OpenPlatformEntry tencentPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TENCENT);
//		
//		tencentPlatformEntry.setApiEntry("https://open.t.qq.com/api");
//		tencentPlatformEntry.setAppKey("28068");
//		tencentPlatformEntry.setAppSecret("c40b30ab26834797bfdbf67c15f3e523");
//		tencentPlatformEntry.setAuthEntry("https://open.t.qq.com/cgi-bin/oauth2/access_token");
//		tencentPlatformEntry.setCallbackUrl("http://www.mashupshow.com/channel");
		
		OpenPlatformEntry renrenPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_RENREN);
		
		renrenPlatformEntry.setApiEntry("http://api.renren.com/restserver.do");
		renrenPlatformEntry.setAppKey("382c091506ff46d3a49e6e6ed8894507");
		renrenPlatformEntry.setAppSecret("3bb4079e67b540b7802963d42c753e53");
		renrenPlatformEntry.setAuthEntry("https://graph.renren.com/oauth/token");
		renrenPlatformEntry.setCallbackUrl("http://www.mashupshow.com/channel");
		
		ICache<AppAuthEntity> authCache = new MemCache<AppAuthEntity>("AppAuth",true);
			
		appclient = new RenRenAppClient();
		appclient.setOpenPlatformEntry(renrenPlatformEntry);
		appclient.setAuthCache(authCache);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAccessTokenByCode(){
		
		String code = TestConstants.RenrenAuthCode;
		
		try
		{
			AppAuthEntity  appAuthEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web",null,null);
			
			Assert.assertNotNull(appAuthEntity);
			
			System.out.println(appAuthEntity);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
