package com.taobao.exchange.relation;


import java.util.List;

import junit.framework.Assert;


import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.client.SinaAppClient;
import com.taobao.exchange.relation.sina.SinaRelationManager;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.MemCache;

public class SinaRelationManagerTest {

	static SinaRelationManager sinaRelationManager;
	static SinaAppClient appClient;
	static String uid;
	static ICache<AppAuthEntity> authCache;
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		OpenPlatformEntry sinaPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_SINA);
		sinaPlatformEntry.setApiEntry("https://api.weibo.com");
		sinaPlatformEntry.setAppKey("845619194");
		sinaPlatformEntry.setAppSecret("3a69bb60ed46d0ceab5f0457657ac0f9");
		sinaPlatformEntry.setAuthEntry("https://api.weibo.com/oauth2/access_token");
		sinaPlatformEntry.setCallbackUrl("http://www.mashupshow.com/channel");
		
		authCache = new MemCache<AppAuthEntity>("AppAuth",true);
		
		appClient = new SinaAppClient();
		appClient.setOpenPlatformEntry(sinaPlatformEntry);
		appClient.setAuthCache(authCache);
		
		//https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=http://www.mashupshow.com/channel&client_id=845619194
		
		//String code = "0a333664f9b66758869bb5c7d4bb69eb";
		//AppAuthEntity authEntity = appClient.getAccessTokenByCodeAndStore(code, null, null, "web");
		
		AppAuthEntity authEntity = new AppAuthEntity();
		authEntity.setAccessToken("2.004_BepB0QLIOvfa07ca5b2cKSXWqC");
		authEntity.setUid("1679264133");
		uid = "1679264133";
		
		authCache.put(AppClientUtil.generatePlatformUUID("sina", uid), authEntity);
		
		sinaRelationManager = new SinaRelationManager();
		sinaRelationManager.setAppClient(appClient);
		sinaRelationManager.setRelationCache(new MemCache<String>("",false));
		
	}

	@Test
	public void testGetFriendsByUser() throws ServiceException {
		List<User> users = sinaRelationManager.getFriendsByUser(uid,uid,null);
				
		Assert.assertTrue(users.size() > 0);
		
		sinaRelationManager.getRelationCache().clear();
		
		appClient.getAuthEntityByUid(uid).getRelationConfig().setRelationLevel(Constants.RELATION_LEVEL_ONEWAY);
		
		users = sinaRelationManager.getFriendsByUser(uid,uid,null);
	}

	@Test
	public void testGetIndirectFriendsByUser() throws ServiceException {
		
		QuerySession session = new QuerySession();
		session.setCursor(0);
		session.setPageSize(10);
		List<User> users = sinaRelationManager.getIndirectFriendsByUser(uid,session);
			
		Assert.assertTrue(users.size() > 0);
		
	}

}
