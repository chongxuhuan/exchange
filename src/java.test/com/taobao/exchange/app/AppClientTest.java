package com.taobao.exchange.app;


import java.awt.Desktop;
import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.Constants;

public class AppClientTest {
	
	IAppClient appclient;

	@Before
	public void setUp() throws Exception {
		
		OpenPlatformManager.register(Constants.OPENPLATFORM_TAOBAO, "https://eco.taobao.com/router/rest",
				"https://oauth.taobao.com/token", "12667915", "c27b029f3c377d6fa02dfb00d11788f4", "www.mashupshow.com");
		
		OpenPlatformManager.register(Constants.OPENPLATFORM_SINA, "https://api.weibo.com",
				"https://api.weibo.com/oauth2/access_token", "845619194", "3a69bb60ed46d0ceab5f0457657ac0f9", "www.mashupshow.com");
				
		appclient = new TopAppClient();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAccessTokenByCode(){
		
		Desktop desktop = Desktop.getDesktop();
		
		try {
			
		    //创建URI统一资源标识符
		    //URI uri = new URI("https://oauth.taobao.com/authorize?response_type=code&&redirect_uri=www.mashupshow.com&client_id=12667915");
		    //URI uri = new URI("https://api.weibo.com/oauth2/authorize?response_type=code&&redirect_uri=www.mashupshow.com&client_id=845619194");
		   //URI uri = new URI("https://graph.renren.com/oauth/authorize?response_type=code&&redirect_uri=www.mashupshow.com&client_id=845619194");
			
		    //使用默认浏览器打开超链接
		    //desktop.browse(uri);
		} catch(Exception ex) {
		    ex.printStackTrace();
		}
		
		String code = "7d3a932501d6a62361f10a4669a8a3e2";
		
		try
		{
			//appclient.getAccessTokenByCode(Constants.OPENPLATFORM_TAOBAO, code, null, null, "web");
			appclient.getAccessTokenByCode(Constants.OPENPLATFORM_SINA, code, null, null, "web");
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
