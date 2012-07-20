package com.taobao.exchange.app;



import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.util.Constants;

public class AppClientTest {
	
	IAppClient appclient;

	@Before
	public void setUp() throws Exception {
		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TAOBAO);
				
		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
		topPlatformEntry.setAppKey("12667915");
		topPlatformEntry.setAppSecret("c27b029f3c377d6fa02dfb00d11788f4");
		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
		topPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		IAuthKeeper authKeeper = new MemAuthKeeper();
			
		appclient = new TopAppClient();
		appclient.setOpenPlatformEntry(topPlatformEntry);
		appclient.setAuthKeeper(authKeeper);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAccessTokenByCode(){
		
		String code = TestConstants.TOPAuthCode;
		
		try
		{
			AppAuthEntity  appAuthEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web");
			
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
