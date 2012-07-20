/**
 * 
 */
package com.taobao.exchange.dig;


import junit.framework.Assert;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.IAuthKeeper;
import com.taobao.exchange.app.MemAuthKeeper;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.SinaAppClient;
import com.taobao.exchange.app.TopAppClient;
import com.taobao.exchange.relation.AccountZoo;
import com.taobao.exchange.relation.RelationManagerFactory;
import com.taobao.exchange.relation.SinaRelationManager;
import com.taobao.exchange.relation.User;
import com.taobao.exchange.secondhand.SecondhandManagerFactory;
import com.taobao.exchange.secondhand.TaobaoSecondhandManager;
import com.taobao.exchange.util.CategoryMemCache;
import com.taobao.exchange.util.QuerySession;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.Constants;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.MemCache;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午1:21:26
 *
 */
public class FriendsDiggerTest {
	
	static FriendsDigger digger;
	static SinaAppClient sinaAppClient;
	static TopAppClient topAppClient;
	static IAuthKeeper topAuthKeeper;
	static IAuthKeeper sinaAuthKeeper;
	static TaobaoSecondhandManager secondhandManager;
	static SinaRelationManager sinaRelationManager;
	static ICache<String,AccountZoo> accountZooCache;
	static ICache<String,AccountZoo> userToAccountZooCache;
	static CategoryMemCache categoryCache;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		
		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TAOBAO);
		
		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
		topPlatformEntry.setAppKey("12643042");
		topPlatformEntry.setAppSecret("a1362a9dc2b74b7811d7acafedc99fcc");
		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
		topPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		OpenPlatformEntry sinaPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_SINA);
		sinaPlatformEntry.setApiEntry("https://api.weibo.com");
		sinaPlatformEntry.setAppKey("845619194");
		sinaPlatformEntry.setAppSecret("3a69bb60ed46d0ceab5f0457657ac0f9");
		sinaPlatformEntry.setAuthEntry("https://api.weibo.com/oauth2/access_token");
		sinaPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		
		accountZooCache = new MemCache<String,AccountZoo>();
		userToAccountZooCache = new MemCache<String,AccountZoo>();
		categoryCache = new CategoryMemCache();
		
		topAuthKeeper = new MemAuthKeeper();
		sinaAuthKeeper = new MemAuthKeeper();
		
		sinaAppClient = new SinaAppClient();
		sinaAppClient.setOpenPlatformEntry(sinaPlatformEntry);
		sinaAppClient.setAuthKeeper(sinaAuthKeeper);
		
		topAppClient = new TopAppClient();
		topAppClient.setOpenPlatformEntry(topPlatformEntry);
		topAppClient.setAuthKeeper(topAuthKeeper);
		
		digger = new FriendsDigger();
		digger.setAccountZooCache(accountZooCache);
		digger.setUserToAccountZooCache(userToAccountZooCache);
		
		secondhandManager = new TaobaoSecondhandManager();
		secondhandManager.setAppClient(topAppClient);
		
		categoryCache.setSecondhandManager(secondhandManager);
		categoryCache.load();
		
		sinaRelationManager = new SinaRelationManager();
		sinaRelationManager.setAppClient(sinaAppClient);
		sinaRelationManager.setRelationCache(new MemCache<String,String>());
		
		SecondhandManagerFactory.register(topAppClient.getOpenPlatformEntry().getId(), secondhandManager);
		RelationManagerFactory.register(sinaAppClient.getOpenPlatformEntry().getId(), sinaRelationManager);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.taobao.exchange.dig.FriendsDigger#dig(com.taobao.exchange.dig.FirendsDigCondition)}.
	 * @throws ServiceException 
	 */
	@Test
	public void testDig() throws ServiceException {
		
		//https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=845619194
		
		//String code = "70ffa417fca3ae05f8bcc16f3b912d6c";
		//AppAuthEntity sinaAuthEntity = sinaAppClient.getAccessTokenByCodeAndStore(code, null, null, "web");
		
		AppAuthEntity sinaAuthEntity = new AppAuthEntity();
		sinaAuthEntity.setAccessToken("2.004_BepB0QLIOvb5ccdf44dd0qiJ61");
		sinaAuthEntity.setUid("1679264133");
		sinaAuthKeeper.store(sinaAuthEntity);
		
		AppAuthEntity sinaAuthEntity2 = new AppAuthEntity();
		sinaAuthEntity2.setAccessToken("2.00qtUcxB0QLIOv2e8dd7755f0K5UnH");
		sinaAuthEntity2.setUid("1797111902");
		sinaAuthKeeper.store(sinaAuthEntity2);
		
		// first call this url 
		//https://oauth.taobao.com/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=12643042
		
		//String code = "pjZOjqnAgh3ubEzuZ4GIvHVt259500";
		//AppAuthEntity topAuthEntity = topAppClient.getAccessTokenByCodeAndStore(code, null, null, "web");
		
		AppAuthEntity topAuthEntity = new AppAuthEntity();
		topAuthEntity.setAccessToken("6200e14567b89e4fbe2aeace2962155bd23117396c7bd5e24006395");
		topAuthEntity.setUid("24006395");
		topAuthEntity.setNick("cenwenchu");
		topAuthKeeper.store(topAuthEntity);
		
		//create 卖家帐号
		AccountZoo az = new AccountZoo();
		User keyUser = new User();
		keyUser.setPlatformId(Constants.PLATFORM_ID_TAOBAO);
		keyUser.setId(topAuthEntity.getUid());
		az.setKeyAccount(keyUser);
		
		az.setSecondhandAccount(keyUser);
		
		
		User u = new User();
		u.setPlatformId(Constants.PLATFORM_ID_SINA);
		u.setId(sinaAuthEntity.getUid());	
		az.storeRelation(u);
		
		userToAccountZooCache.put(AppClientUtil.generatePlatformUUID(keyUser.getPlatformId(),keyUser.getId()), az);
		userToAccountZooCache.put(AppClientUtil.generatePlatformUUID(u.getPlatformId(),u.getId()), az);
		
		accountZooCache.put(az.generateAccountZooKey(), az);
		
		//create 买家帐号
		AccountZoo az2 = new AccountZoo();
		
		User keyUser2 = new User();
		keyUser2.setPlatformId(Constants.PLATFORM_ID_SINA);
		keyUser2.setId(sinaAuthEntity2.getUid());	
		
		az2.setKeyAccount(keyUser2);

		
		az2.storeRelation(keyUser2);
		
		userToAccountZooCache.put(AppClientUtil.generatePlatformUUID(keyUser2.getPlatformId(),keyUser2.getId()), az2);
		
		accountZooCache.put(az2.generateAccountZooKey(), az2);
		
		
		FirendsDigCondition friendsDigCondition = new FirendsDigCondition();
		
		friendsDigCondition.setSecondHandPlatformID(Constants.PLATFORM_ID_TAOBAO);
		friendsDigCondition.setPlatformID(keyUser2.getPlatformId());
		friendsDigCondition.setUid(keyUser2.getId());
		friendsDigCondition.setIndirectRelation(true);
		
		
		int cursor = 0;
		QuerySession qSession = new QuerySession();
		qSession.setPageSize(1);
		//if indirect cursor no use
		qSession.setCursor(cursor);
		friendsDigCondition.setQsession(qSession);
		
		DigResult digResult = digger.dig(friendsDigCondition);
		
		Assert.assertTrue(digResult.getSecondhands() != null && digResult.getSecondhands().size() > 0);

		if (friendsDigCondition.isIndirectRelation())
		{
			digResult = digger.dig(friendsDigCondition);
		
			Assert.assertTrue(digResult.getSecondhands() != null && digResult.getSecondhands().size() > 0);
		
		}
		
	}

}
