package com.taobao.exchange.relation;


import java.util.List;

import junit.framework.Assert;


import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.client.SinaAppClient;
import com.taobao.exchange.relation.sina.SinaRelationManager;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.FriendSecondhandQuerySession;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.MemCache;

public class SinaRelationManagerTest {

	static SinaRelationManager sinaRelationManager;
	static SinaAppClient appClient;
	static String uid;
	static ICache<AppAuthEntity> authCache;
	static ICache<AccountZoo> userToAccountZooCache;
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		OpenPlatformEntry sinaPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_SINA);
		sinaPlatformEntry.setApiEntry("https://api.weibo.com");
		sinaPlatformEntry.setAppKey("845619194");
		sinaPlatformEntry.setAppSecret("3a69bb60ed46d0ceab5f0457657ac0f9");
		sinaPlatformEntry.setAuthEntry("https://api.weibo.com/oauth2/access_token");
		sinaPlatformEntry.setCallbackUrl("http://www.mashupshow.com/channel");
		
		authCache = new MemCache<AppAuthEntity>(AppAuthEntity.class.getName(),false);
		userToAccountZooCache = new MemCache<AccountZoo>(AccountZoo.class.getName(),false);
		
		appClient = new SinaAppClient();
		appClient.setOpenPlatformEntry(sinaPlatformEntry);
		appClient.setAuthCache(authCache);
		
		//https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=http://www.mashupshow.com/channel&client_id=845619194
		
		//String code = "9f98195dd8f628246dc4191ce3afb002";
		//AppAuthEntity authEntity = appClient.getAccessTokenByCodeAndStore(code, null, null, "web",null,null);
		
		AppAuthEntity authEntity = new AppAuthEntity();
		authEntity.setAccessToken("2.004_BepB0QLIOv04f7c5eed7D2XQUB");
		authEntity.setUid("1679264133");
		uid = "1679264133";
		
		authCache.put(AppClientUtil.generatePlatformUUID("sina", uid), authEntity);
		
		AccountZoo az = new AccountZoo();
		
		userToAccountZooCache.put(AppClientUtil.generatePlatformUUID("sina", uid), az);
		
		sinaRelationManager = new SinaRelationManager();
		sinaRelationManager.setAppClient(appClient);
		sinaRelationManager.setRelationCache(new MemCache<String>("",false));
		sinaRelationManager.setUserToAccountZooCache(userToAccountZooCache);
	}

	@Test
	@Ignore
	public void testGetFriendsByUser() throws ServiceException {
		List<User> users = sinaRelationManager.getFriendsByUser(uid,uid,null);
				
		Assert.assertTrue(users.size() > 0);
		
		sinaRelationManager.getRelationCache().clear();
		
		userToAccountZooCache.get(AppClientUtil.generatePlatformUUID("sina", uid)).getRelationConfig().setRelationLevel(Constants.RELATION_LEVEL_ONEWAY);
		
		users = sinaRelationManager.getFriendsByUser(uid,uid,null);
	}

	@Test
	public void testGetIndirectFriendsByUser() throws ServiceException {
		
		FriendSecondhandQuerySession session = new FriendSecondhandQuerySession();
		session.setFriendCursor(0);
		session.setFriendPageSize(20);
		List<User> users = sinaRelationManager.getIndirectFriendsByUser(uid,session);
			
		Assert.assertTrue(users.size() > 0);
		
	}

}
