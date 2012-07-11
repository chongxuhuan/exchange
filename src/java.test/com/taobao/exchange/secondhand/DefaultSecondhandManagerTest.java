package com.taobao.exchange.secondhand;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.AppRequestAttachment;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.app.TopAppClient;
import com.taobao.exchange.util.AppClientException;

public class DefaultSecondhandManagerTest {
	
	static TopAppClient appclient;
	static DefaultSecondhandManager secondhandManager;
	static String uid;
	static String iid;

	@BeforeClass
	public static void setUp() throws Exception {
		// first call this url 
		//https://oauth.taobao.com/authorize?response_type=code&redirect_uri=www.mashupshow.com&client_id=12643042
				
		
		OpenPlatformEntry topPlatformEntry = new OpenPlatformEntry();
		
		topPlatformEntry.setApiEntry("https://eco.taobao.com/router/rest");
		topPlatformEntry.setAppKey("12643042");
		topPlatformEntry.setAppSecret("a1362a9dc2b74b7811d7acafedc99fcc");
		topPlatformEntry.setAuthEntry("https://oauth.taobao.com/token");
		topPlatformEntry.setCallbackUrl("www.mashupshow.com");
			
		appclient = new TopAppClient();
		appclient.setOpenPlatformEntry(topPlatformEntry);
		
		secondhandManager = new DefaultSecondhandManager();
		secondhandManager.setAppClient(appclient);
		
//		String code = "C8cyHGZ4QHOpHq2Ws1ov0Ijo28015";
//		AppAuthEntity authEntity = appclient.getAccessTokenByCode(code, null, null, "web");
		
		AppAuthEntity authEntity = new AppAuthEntity();
		authEntity.setAccessToken("6200530ce78fee21d0b47c7f9b35dd673ee7aace056126824006395");
		authEntity.setUid("24006395");
		uid = "24006395";
		
		appclient.addAuthToClient(authEntity);
		
	}


	@Test
	public void testGetSecondhandCategory() throws AppClientException {
		Category[] categorys = secondhandManager.getSecondhandCategory();
		
		Assert.assertTrue(categorys.length > 0);
	}

	@Test
	public void testPublish() throws AppClientException, IOException {
		
		Secondhand secondhand = new Secondhand();
		secondhand.setCat_id("50023914");
		secondhand.setContacter("放翁");
		secondhand.setDescribe("这是一件好东西,test1234");
		secondhand.setDivision_id("330106");
		secondhand.setOfflined(1);
		secondhand.setOrg_price(100.01);
		secondhand.setPhone("13067993324");
		secondhand.setPost_fee(10.0);
		secondhand.setPrice(80.5);
		secondhand.setStuff_status(9);
		secondhand.setTags("美白,防晒,防水");
		secondhand.setTitle("超级美白防晒霜");
		secondhand.setWwsuport(true);
		
		AppRequestAttachment major_image = new AppRequestAttachment();
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
	public void testUpdate() throws IOException, AppClientException {
		
		Secondhand secondhand = new Secondhand();
		secondhand.setCat_id("50023914");
		secondhand.setContacter("放翁1234");
		secondhand.setDescribe("这是一件好东西,test1234");
		secondhand.setDivision_id("330106");
		secondhand.setOfflined(1);
		secondhand.setOrg_price(100.01);
		secondhand.setPhone("13067993324");
		secondhand.setPost_fee(10.0);
		secondhand.setPrice(82.5);
		secondhand.setStuff_status(9);
		secondhand.setTags("美白,防晒,防水");
		secondhand.setTitle("超级美白防晒霜");
		secondhand.setWwsuport(true);
		secondhand.setItem_id(iid);
		
		AppRequestAttachment major_image = new AppRequestAttachment();
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
	public void testComment() throws AppClientException {
		Assert.assertTrue(secondhandManager.comment(uid, iid, "这个东西我喜欢", "超级美白防晒霜"));
	}
	
	@Test
	public void testGetSecondhandById() throws AppClientException {
		Secondhand second = secondhandManager.getSecondhandById(iid);
		
		Assert.assertEquals(second.getItem_id(), iid);
	}
	
	@Test
	public void testDelete() throws AppClientException {
		OperationResult oResult = secondhandManager.delete(uid, iid);
		
		Assert.assertTrue(oResult.getItem_id() != null);
	}

	@Test
	public void testGetSecondhandsByUser() {
		fail("Not yet implemented");
	}	

}
