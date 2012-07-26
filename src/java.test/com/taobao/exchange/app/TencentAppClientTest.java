package com.taobao.exchange.app;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ServiceException;

public class TencentAppClientTest {
	
	static IAppClient appclient;
	static IAuthKeeper authKeeper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		OpenPlatformEntry tencentPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TENCENT);
		
		tencentPlatformEntry.setApiEntry("https://open.t.qq.com/api");
		tencentPlatformEntry.setAppKey("28068");
		tencentPlatformEntry.setAppSecret("c40b30ab26834797bfdbf67c15f3e523");
		tencentPlatformEntry.setAuthEntry("https://open.t.qq.com/cgi-bin/oauth2/access_token");
		tencentPlatformEntry.setCallbackUrl("http://www.mashupshow.com");
		
		authKeeper = new MemAuthKeeper();
		
		appclient = new TencentAppClient();
		appclient.setOpenPlatformEntry(tencentPlatformEntry);
		appclient.setAuthKeeper(authKeeper);
	}

	@Test
	public void testApi() throws ServiceException {
		String code = TestConstants.RenrenAuthCode;
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web");
		
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("page", "1");
		params.put("fields", "id,name,sex");
		
		String result = appclient.api(authEntity.getUid(), "GET","Friends.getFriends", null, params);
		
		System.out.println(result);
		
		Assert.assertTrue(result.indexOf("data") > 0);
	}

}
