package com.taobao.exchange.app;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.Constants;

public class TopAppClientTest {
	
	IAppClient appclient;

	@Before
	public void setUp() throws Exception {
		OpenPlatformManager.register(Constants.OPENPLATFORM_TAOBAO, "https://eco.taobao.com/router/rest",
				"https://oauth.taobao.com/token", "12667915", "c27b029f3c377d6fa02dfb00d11788f4", "www.mashupshow.com");
		
		appclient = new TopAppClient();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApi() throws AppClientException {
		// first call this url 
		//https://oauth.taobao.com/authorize?response_type=code&&redirect_uri=www.mashupshow.com&client_id=12667915
		
		String code = "W4ThF4Uzd5GyhEa5TiTs44lA9947";
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCode(Constants.OPENPLATFORM_TAOBAO, code, null, null, "web");
		
		appclient.addAuthToClient(authEntity);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fields", "nick,sex,alipay_bind");
		
		String result = appclient.api(Constants.OPENPLATFORM_TAOBAO,authEntity.getUid(), "GET","taobao.user.get", null, params);
		
		System.out.println(result);
		
	}

}
