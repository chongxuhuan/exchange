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
import com.taobao.exchange.util.AppClientException;
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
	 * @throws AppClientException 
	 */
	@Test
	public void testDig() throws AppClientException {
		
		//https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=845619194
		
		//String code = "393476a3a97b8c7ff4f4c73086fb965c";
		//AppAuthEntity sinaAuthEntity = sinaAppClient.getAccessTokenByCode(code, null, null, "web");
		
		AppAuthEntity sinaAuthEntity = new AppAuthEntity();
		sinaAuthEntity.setAccessToken("2.00FvkrWB0QLIOvc319834fafU5dvtD");
		sinaAuthEntity.setUid("1401787331");
		sinaAuthKeeper.store(sinaAuthEntity.getUid(), sinaAuthEntity);
		
		// first call this url 
		//https://oauth.taobao.com/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=12643042
		
//		String code = "X5lCKcvEb0Rlnv23xC39X67Q163204";
//		AppAuthEntity topAuthEntity = topAppClient.getAccessTokenByCode(code, null, null, "web");
		
		AppAuthEntity topAuthEntity = new AppAuthEntity();
		topAuthEntity.setAccessToken("6201f282db6b77328032507dbb6da87a8e95ZZf92f2957024006395");
		topAuthEntity.setUid("24006395");
		topAuthEntity.setNick("cenwenchu");
		topAuthKeeper.store(topAuthEntity.getUid(), topAuthEntity);
		
		FirendsDigCondition friendsDigCondition = new FirendsDigCondition();
		
		friendsDigCondition.setSecondHandPlatformID(Constants.PLATFORM_ID_TAOBAO);
		friendsDigCondition.setSecondHandUID("24006395");
		
		AccountZoo az = new AccountZoo();
		az.setSecondHandPlatformID(Constants.PLATFORM_ID_TAOBAO);
		az.setSecondHandUID("24006395");
		
		List<User> relation = new ArrayList<User>();
		az.setRelationAccounts(relation);
		
		User u = new User();
		u.setPlatformId(Constants.PLATFORM_ID_SINA);
		u.setId("1401787331");
		
		relation.add(u);
		
		accountZooCache.put(new StringBuilder().append(Constants.PLATFORM_ID_TAOBAO)
					.append("::").append("24006395").toString(), az);
		
		accountZooCache.put(az.generateAccountZooKey(), az);
		
		DigResult digResult = digger.dig(friendsDigCondition);
		
		Assert.assertTrue(digResult.getSecondhands() != null && digResult.getSecondhands().size() > 0);
		
	}

}
