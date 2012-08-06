package com.taobao.exchange.secondhand;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.RequestAttachment;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.client.TopAppClient;
import com.taobao.exchange.dig.SecondhandCondition;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.MemCache;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

public class TaobaoSecondhandManagerTest {
	
	static TopAppClient appclient;
	static TaobaoSecondhandManager secondhandManager;
	static String uid;
	static String iid;
	static ICache<AppAuthEntity> authCache;

	@BeforeClass
	public static void setUp() throws Exception {	
		
		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry(Constants.PLATFORM_ID_TAOBAO);
		
		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
		topPlatformEntry.setAppKey("12643042");
		topPlatformEntry.setAppSecret("a1362a9dc2b74b7811d7acafedc99fcc");
		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
		topPlatformEntry.setCallbackUrl("www.mashupshow.com");
		
		authCache = new MemCache<AppAuthEntity>("AppAuth",true);
			
		appclient = new TopAppClient();
		appclient.setOpenPlatformEntry(topPlatformEntry);
		appclient.setAuthCache(authCache);
		
		secondhandManager = new TaobaoSecondhandManager();
		secondhandManager.setAppClient(appclient);
		
		// first call this url 
		//https://oauth.taobao.com/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=12643042
		
//		String code = "eiQF8nPEQvxR4cScEuTMibUJ178453";
//		AppAuthEntity authEntity = appclient.getAccessTokenByCode(code, null, null, "web");
		
		AppAuthEntity authEntity = new AppAuthEntity();
		authEntity.setAccessToken("6200126d6af85a44ceef003277330dac0fhjdc9fdd158d524006395");
		authEntity.setUid("24006395");
		authEntity.setNick("cenwenchu");
		uid = "24006395";
		
		authCache.put(AppClientUtil.generatePlatformUUID("taobao", uid), authEntity);
	}


	@Test
	public void testGetSecondhandCategory() throws ServiceException {
		Category[] categorys = secondhandManager.getSecondhandCategory();
		
		Assert.assertTrue(categorys.length > 0);
	}
	
	@Test
	public void testCommonSearch() throws ServiceException {
		
		SecondhandCondition condition = new SecondhandCondition();
		condition.setSeller_nick("cenwenchu");
		
		Secondhand[] oResult = secondhandManager.commonSearch(condition);
		
		Assert.assertNotNull(oResult);
		Assert.assertTrue(oResult.length > 0);
	}
	
	@Test
	public void testList() throws ServiceException {
		
		Secondhand[] oResult = secondhandManager.list(uid);
		
		Assert.assertNotNull(oResult);
		Assert.assertTrue(oResult.length > 0);
	}
	@Test
	public void testPublish() throws ServiceException, IOException {
		
		Secondhand secondhand = new Secondhand();
		secondhand.setCat_id("50023914");
		secondhand.setContacter("放翁");
		secondhand.setDescribe("这是一件好东西,test1234");
		secondhand.setDivision_id("330106");
		secondhand.setOfflined(1);
		secondhand.setOrg_price("100.01");
		secondhand.setPhone("13067993324");
		secondhand.setPost_fee("10.0");
		secondhand.setPrice("80.5");
		secondhand.setStuff_status(9);
		secondhand.setTags("美白,防晒,防水");
		secondhand.setTitle("超级美白防晒霜");
		secondhand.setWwsuport(true);
		
		RequestAttachment major_image = new RequestAttachment();
		major_image.setName("test.jpg");
		
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.jpg");
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		IOUtils.copy(in, bout);
		
		major_image.setContent(bout.toByteArray());
		
		secondhand.setMajor_image(major_image);
		
		if (in != null)
			in.close();
		
		if (bout != null)
			bout.close();
		
		OperationResult oResult = secondhandManager.publish(uid, secondhand);
		
		Assert.assertTrue(oResult.getItem_id() != null);
		iid = oResult.getItem_id();
	}

	@Test
	public void testUpdate() throws IOException, ServiceException {
		
		Secondhand secondhand = new Secondhand();
		secondhand.setCat_id("50023914");
		secondhand.setContacter("放翁1234");
		secondhand.setDescribe("这是一件好东西,test1234");
		secondhand.setDivision_id("330106");
		secondhand.setOfflined(1);
		secondhand.setOrg_price("100.01");
		secondhand.setPhone("13067993324");
		secondhand.setPost_fee("10.0");
		secondhand.setPrice("82.5");
		secondhand.setStuff_status(9);
		secondhand.setTags("美白,防晒,防水");
		secondhand.setTitle("超级美白防晒霜");
		secondhand.setWwsuport(true);
		secondhand.setItem_id(iid);
		
		RequestAttachment major_image = new RequestAttachment();
		major_image.setName("test.jpg");
		
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.jpg");
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		IOUtils.copy(in, bout);
		
		major_image.setContent(bout.toByteArray());
		
		secondhand.setMajor_image(major_image);
		
		if (in != null)
			in.close();
		
		if (bout != null)
			bout.close();
		
		OperationResult oResult = secondhandManager.update(uid, secondhand);
		
		Assert.assertTrue(oResult.getItem_id() != null);
	}


	@Test
	@Ignore
	public void testComment() throws ServiceException {
		Assert.assertTrue(secondhandManager.comment(uid, iid, "这个东西我喜欢", "超级美白防晒霜"));
	}
	
	@Test
	public void testGetSecondhandById() throws ServiceException {
		Secondhand second = secondhandManager.getSecondhandById(iid);
		
		Assert.assertEquals(second.getItem_id(), iid);
	}
	

	@Test
	public void testGetSecondhandsByUser() throws ServiceException {
		Secondhand[]  secondhands = secondhandManager.getSecondhandsByUser(uid);
		
		Assert.assertTrue(secondhands.length > 0);
	}	
	
	@Test
	public void testDelete() throws ServiceException {
		OperationResult oResult = secondhandManager.delete(uid, iid);
		
		Assert.assertTrue(oResult.getItem_id() != null);
	}
	
}
