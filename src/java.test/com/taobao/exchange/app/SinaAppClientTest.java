package com.taobao.exchange.app;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.Constants;

public class SinaAppClientTest {
	
	IAppClient appclient;

	@Before
	public void setUp() throws Exception {
		OpenPlatformManager.register(Constants.OPENPLATFORM_SINA, "https://api.weibo.com",
				"https://api.weibo.com/oauth2/access_token", "845619194", "3a69bb60ed46d0ceab5f0457657ac0f9", "www.mashupshow.com");
				
		appclient = new SinaAppClient();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApi() throws AppClientException {
		//https://api.weibo.com/oauth2/authorize?response_type=code&&redirect_uri=www.mashupshow.com&client_id=845619194
		
		String code = "13bfc122da7f8332429f9b04e51e0bae";
		
		AppAuthEntity authEntity = appclient.getAccessTokenByCode(Constants.OPENPLATFORM_SINA, code, null, null, "web");
		
		appclient.addAuthToClient(authEntity);
		
		String result = appclient.api(Constants.OPENPLATFORM_SINA,authEntity.getUid(), "GET","friendships/friends/bilateral", null, null);
		
		System.out.println(result);
	}

}
