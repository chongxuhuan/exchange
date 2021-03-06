package com.taobao.exchange.relation;


import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.client.RenRenAppClient;
import com.taobao.exchange.relation.renren.RenRenRelationManager;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.MemCache;
import com.taobao.exchange.util.ServiceException;

public class RenRenRelationManagerTest {
	
	static RenRenAppClient appclient;
	static ICache<AppAuthEntity> authCache;
	static RenRenRelationManager renrenRelationManager;
	static AppAuthEntity authEntity;
	static ICache<AccountZoo> userToAccountZooCache;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		OpenPlatformEntry renrenPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_RENREN);
		
		renrenPlatformEntry.setApiEntry("http://api.renren.com/restserver.do");
		renrenPlatformEntry.setAppKey("382c091506ff46d3a49e6e6ed8894507");
		renrenPlatformEntry.setAppSecret("3bb4079e67b540b7802963d42c753e53");
		renrenPlatformEntry.setAuthEntry("https://graph.renren.com/oauth/token");
		renrenPlatformEntry.setCallbackUrl("http://www.mashupshow.com/channel");
		
		authCache = new MemCache<AppAuthEntity>(AppAuthEntity.class.getName(),false);
		userToAccountZooCache = new MemCache<AccountZoo>(AccountZoo.class.getName(),false);
			
		appclient = new RenRenAppClient();
		appclient.setOpenPlatformEntry(renrenPlatformEntry);
		appclient.setAuthCache(authCache);
		
		//https://graph.renren.com/oauth/authorize?client_id=382c091506ff46d3a49e6e6ed8894507&redirect_uri=http://www.mashupshow.com/channel&response_type=code
			
//		String code = "CwrmWZmXugC4sEdU5tEFyP3xD51huNvQ";
//		authEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web");
		
		authEntity = new AppAuthEntity();
		authEntity.setAccessToken("202684|6.7ef22b873023f3183b58d9069918dbf4.2592000.1346212800-254283491");
		authEntity.setUid("254283491");
		authEntity.setNick("岑文初");
		
		authCache.put(AppClientUtil.generatePlatformUUID("renren", "254283491"), authEntity);
		
		renrenRelationManager = new RenRenRelationManager();
		renrenRelationManager.setAppClient(appclient);
		renrenRelationManager.setRelationCache(new MemCache<String>("",false));
		renrenRelationManager.setUserToAccountZooCache(userToAccountZooCache);
	}

	@Test
	public void testGetFriendsByUser() throws ServiceException {
		List<User> users = renrenRelationManager.getFriendsByUser(authEntity.getUid(), authEntity.getUid(), null);
		Assert.assertTrue(users.size() > 0);
	}

}
