package com.taobao.exchange.relation;

import java.util.List;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.IAuthKeeper;
import com.taobao.exchange.app.MemAuthKeeper;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.TencentAppClient;
import com.taobao.exchange.relation.tencent.TencentRelationManager;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.MemCache;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;

public class TencentRelationManagerTest {

	static TencentAppClient appclient;
	static IAuthKeeper authKeeper;
	static TencentRelationManager tencentRelationManager;
	static AppAuthEntity authEntity;
	
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
		
		//https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=28068&response_type=code&redirect_uri=http://www.mashupshow.com
		
		//String code = "e3e134dcd6ccd81c76ca1dd726f898e1";
		//AppAuthEntity authEntity = appclient.getAccessTokenByCodeAndStore(code, null, null, "web");
		
		authEntity = new AppAuthEntity();
		authEntity.setAccessToken("58a1c1c3cbd4b4a8f2c1a410e57a9f25");
		authEntity.setUid("cenwenchu79");
		authEntity.setNick("cenwenchu79");
		authEntity.setOpenId("0000000000000000000000001A5649A8");
		
		authKeeper.store(authEntity);
		
		tencentRelationManager = new TencentRelationManager();
		tencentRelationManager.setAppClient(appclient);
		tencentRelationManager.setRelationCache(new MemCache<String,String>());
	}

	@Test
	@Ignore
	public void testGetFriendsByUser() throws ServiceException {
		List<User> users = tencentRelationManager.getFriendsByUser(authEntity.getUid(), authEntity.getUid(), null);
		Assert.assertTrue(users.size() > 0);
		
		tencentRelationManager.getRelationCache().clear();
		
		appclient.getAuthEntityByUid("cenwenchu79").getRelationConfig().setRelationLevel(Constants.RELATION_LEVEL_ONEWAY);
		
		users = tencentRelationManager.getFriendsByUser(authEntity.getUid(), authEntity.getUid(), null);
		
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testGetIndirectFriendsByUser() throws ServiceException {
		
		//List<User> users = tencentRelationManager.getIndirectFriendsByUser(authEntity.getUid(),null);
		
		QuerySession session = new QuerySession();
		session.setCursor(0);
		session.setPageSize(5);
		List<User> users = tencentRelationManager.getIndirectFriendsByUser(authEntity.getUid(),session);
			
		Assert.assertTrue(users.size() > 0);
		
		users = tencentRelationManager.getIndirectFriendsByUser(authEntity.getUid(),session);
		
		Assert.assertTrue(users.size() > 0);
	}

}
