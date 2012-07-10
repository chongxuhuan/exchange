package com.taobao.exchange.app;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppClientTest {
	
	IAppClient appclient;

	@Before
	public void setUp() throws Exception {
		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry();
				
		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
		topPlatformEntry.setAppKey("12667915");
		topPlatformEntry.setAppSecret("c27b029f3c377d6fa02dfb00d11788f4");
		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
		topPlatformEntry.setCallbackUrl("www.mashupshow.com");
			
		appclient = new TopAppClient();
		appclient.setOpenPlatformEntry(topPlatformEntry);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAccessTokenByCode(){
		
		//call first then change code content
		//https://oauth.taobao.com/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=12667915
		
		String code = "6Bid2CEDg7fiiSc9l1mvpjDK18587";
		
		try
		{
			AppAuthEntity  appAuthEntity = appclient.getAccessTokenByCode(code, null, null, "web");
			
			System.out.println(appAuthEntity);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
