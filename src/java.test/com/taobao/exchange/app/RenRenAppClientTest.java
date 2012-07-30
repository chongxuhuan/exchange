package com.taobao.exchange.app;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ServiceException;

public class RenRenAppClientTest {
	
	static IAppClient appclient;
	static IAuthKeeper authKeeper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		OpenPlatformEntry renrenPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_RENREN);
		
		renrenPlatformEntry.setApiEntry("http://api.renren.com/restserver.do");
		renrenPlatformEntry.setAppKey("382c091506ff46d3a49e6e6ed8894507");
		renrenPlatformEntry.setAppSecret("3bb4079e67b540b7802963d42c753e53");
		renrenPlatformEntry.setAuthEntry("https://graph.renren.com/oauth/token");
		renrenPlatformEntry.setCallbackUrl("http://www.mashupshow.com/channel");
		
		IAuthKeeper authKeeper = new MemAuthKeeper();
			
		appclient = new RenRenAppClient();
		appclient.setOpenPlatformEntry(renrenPlatformEntry);
		appclient.setAuthKeeper(authKeeper);
	}

	@Test
	public void testApi() throws ServiceException {
		String code = TestConstants.RenrenAuthCode;
	
		AppAuthEntity authEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web",null);
	
	
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("page", "1");
	
		String result = appclient.api(authEntity.getUid(), "POST","friends.getFriends", null, params);
	
		System.out.println(result);
	
		Assert.assertTrue(result.indexOf("id") > 0);
	}

}
