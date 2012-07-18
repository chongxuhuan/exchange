/**
 * 
 */
package com.taobao.exchange.dig;


import java.util.ArrayList;
import java.util.List;

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
	static ICache<String,String> contextCache;
	static TaobaoSecondhandManager secondhandManager;
	static SinaRelationManager sinaRelationManager;
	static ICache<String,AccountZoo> accountZooCache;
	static ICache<String,AccountZoo> relationAccountZooCache;
	

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
		relationAccountZooCache = new MemCache<String,AccountZoo>();
		contextCache = new MemCache<String,String>();
		
		topAuthKeeper = new MemAuthKeeper();
		sinaAuthKeeper = new MemAuthKeeper();
		
		sinaAppClient = new SinaAppClient();
		sinaAppClient.setOpenPlatformEntry(sinaPlatformEntry);
		sinaAppClient.setAuthKeeper(sinaAuthKeeper);
		
		topAppClient = new TopAppClient();
		topAppClient.setOpenPlatformEntry(topPlatformEntry);
		topAppClient.setAuthKeeper(topAuthKeeper);
		
		digger = new FriendsDigger();
		digger.setContextCache(contextCache);
		digger.setAccountZooCache(accountZooCache);
		digger.setRelationAccountZooCache(relationAccountZooCache);
		
		secondhandManager = new TaobaoSecondhandManager();
		secondhandManager.setAppClient(topAppClient);
		
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
		
		//String code = "e44d5fbee14a2af5bebbd963bd0dd068";
		//AppAuthEntity sinaAuthEntity = sinaAppClient.getAccessTokenByCode(code, null, null, "web");
		
		AppAuthEntity sinaAuthEntity = new AppAuthEntity();
		sinaAuthEntity.setAccessToken("2.00FvkrWB0QLIOvc319834fafU5dvtD");
		sinaAuthEntity.setUid("1401787331");
		sinaAuthKeeper.store(sinaAuthEntity.getUid(), sinaAuthEntity);
		
		AppAuthEntity sinaAuthEntity2 = new AppAuthEntity();
		sinaAuthEntity2.setAccessToken("2.004_BepB0QLIOve19916815fqXIIDE");
		sinaAuthEntity2.setUid("1679264133");
		sinaAuthKeeper.store(sinaAuthEntity2.getUid(), sinaAuthEntity2);
		
		// first call this url 
		//https://oauth.taobao.com/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=12643042
		
//		String code = "wBHEl6dMNghWNVee84p47tWc164481";
//		AppAuthEntity topAuthEntity = topAppClient.getAccessTokenByCode(code, null, null, "web");
		
		AppAuthEntity topAuthEntity = new AppAuthEntity();
		topAuthEntity.setAccessToken("62027007ZZ8c48548eee24738d3a4d393d5d6b9b93feb2d263685215");
		topAuthEntity.setUid("263685215");
		topAuthKeeper.store(topAuthEntity.getUid(), topAuthEntity);
		
		AppAuthEntity topAuthEntity2 = new AppAuthEntity();
		topAuthEntity2.setAccessToken("6201f282db6b77328032507dbb6da87a8e95ZZf92f2957024006395");
		topAuthEntity2.setUid("24006395");
		topAuthEntity2.setNick("cenwenchu");
		topAuthKeeper.store(topAuthEntity2.getUid(), topAuthEntity2);
		
		//create az
		AccountZoo az = new AccountZoo();
		az.setSecondHandPlatformID(Constants.PLATFORM_ID_TAOBAO);
		az.setSecondHandUID(topAuthEntity.getUid());
		
		List<User> relation = new ArrayList<User>();
		az.setRelationAccounts(relation);
		
		User u = new User();
		u.setPlatformId(Constants.PLATFORM_ID_SINA);
		u.setId(sinaAuthEntity.getUid());	
		relation.add(u);
		
		relationAccountZooCache.put(AppClientUtil.generatePlatformUUID(Constants.PLATFORM_ID_SINA,sinaAuthEntity.getUid()), az);
		
		accountZooCache.put(az.generateAccountZooKey(), az);
		
		//create az2
		AccountZoo az2 = new AccountZoo();
		az2.setSecondHandPlatformID(Constants.PLATFORM_ID_TAOBAO);
		az2.setSecondHandUID(topAuthEntity2.getUid());
		
		List<User> relation2 = new ArrayList<User>();
		az2.setRelationAccounts(relation2);
		
		u = new User();
		u.setPlatformId(Constants.PLATFORM_ID_SINA);
		u.setId(sinaAuthEntity2.getUid());	
		relation2.add(u);
		
		relationAccountZooCache.put(AppClientUtil.generatePlatformUUID(Constants.PLATFORM_ID_SINA,sinaAuthEntity2.getUid()), az2);
		
		accountZooCache.put(az2.generateAccountZooKey(), az2);
		
		
		FirendsDigCondition friendsDigCondition = new FirendsDigCondition();
		
		friendsDigCondition.setSecondHandPlatformID(Constants.PLATFORM_ID_TAOBAO);
		friendsDigCondition.setSecondHandUID(az.getSecondHandUID());
		
		DigResult digResult = digger.dig(friendsDigCondition);
		
		Assert.assertTrue(digResult.getSecondhands() != null && digResult.getSecondhands().size() > 0);
		
	}

}
