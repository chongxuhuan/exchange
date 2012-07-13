package com.taobao.exchange.relation;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;


import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.IAuthKeeper;
import com.taobao.exchange.app.MemAuthKeeper;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.SinaAppClient;
import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.MemCache;

public class SinaRelationManagerTest {

	static SinaRelationManager sinaRelationManager;
	static SinaAppClient appClient;
	static String uid;
	static IAuthKeeper authKeeper;
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		OpenPlatformEntry sinaPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_SINA);
		sinaPlatformEntry.setApiEntry("https://api.weibo.com");
		sinaPlatformEntry.setAppKey("845619194");
		sinaPlatformEntry.setAppSecret("3a69bb60ed46d0ceab5f0457657ac0f9");
		sinaPlatformEntry.setAuthEntry("https://api.weibo.com/oauth2/access_token");
		sinaPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		authKeeper = new MemAuthKeeper();
		
		appClient = new SinaAppClient();
		appClient.setOpenPlatformEntry(sinaPlatformEntry);
		appClient.setAuthKeeper(authKeeper);
		
		//https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=845619194
		
		//String code = "be50cefdfb9916ad06fbafa8f4599ead";
		//AppAuthEntity authEntity = appClient.getAccessTokenByCode(code, null, null, "web");
		
		AppAuthEntity authEntity = new AppAuthEntity();
		authEntity.setAccessToken("2.004_BepB0QLIOv18a2e68bechwCHBD");
		authEntity.setUid("1679264133");
		uid = "1679264133";
		
		authKeeper.store(authEntity.getUid(),authEntity);
		
		sinaRelationManager = new SinaRelationManager();
		sinaRelationManager.setAppClient(appClient);
		sinaRelationManager.setRelationCache(new MemCache());
		
	}



	@Test
	public void testGetFriendsByUser() throws AppClientException {
		List<User> users = sinaRelationManager.getFriendsByUser(uid);
		
		
		Assert.assertTrue(users.size() > 0);
		
		users = sinaRelationManager.getFriendsByUser(uid);
	}

	@Test
	public void testGetIndirectFriendsByUser() {
		fail("Not yet implemented");
	}

}
