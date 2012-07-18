package com.taobao.exchange.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;

import com.taobao.exchange.app.RequestAttachment;

/**
 * 应用客户端工具类
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9
 *
 */
public class AppClientUtil {
	
	/**
	 * 根据平台id和用户id获得一个组合id
	 * @param platformId
	 * @param uid
	 * @return
	 */
	public static String generatePlatformUUID(String platformId,String uid)
	{
		return new StringBuilder().append(platformId)
				.append("::").append(uid).toString();
	}
	
	/**
	 * 发送http请求（支持https），作为业务协议的通信底层
	 * @param 请求地址
	 * @param http的方法（GET，POST）
	 * @param http的消息头
	 * @param 业务参数
	 * @return
	 * @throws ServiceException
	 */
	public static String sendRequest(String url,String httpMethod,Map<String,String>headers,Map<String,Object>params) 
			throws ServiceException
	{
		byte[] result = null;
		HttpURLConnection httpConn = null;
		
		try
		{
			SSLContext ctx = SSLContext.getInstance("TLS");
	        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
	        SSLContext.setDefault(ctx);
	        
			if (httpMethod.equalsIgnoreCase("GET"))
			{
				StringBuilder reqParams = new StringBuilder().append("?");
				for(Map.Entry<String, Object> e : params.entrySet())
				{
					reqParams.append(e.getKey())
							.append("=").append(e.getValue()).append("&");
				}
				
				url += reqParams.toString();
			}
			
			httpConn = (HttpURLConnection) new URL(url).openConnection();

			// Should never cache GData requests/responses
			httpConn.setUseCaches(false);
			// Always follow redirects
			httpConn.setInstanceFollowRedirects(true);
			
			if (headers != null && headers.size() > 0)
			{
				Iterator<String> keys = headers.keySet().iterator();

				while (keys.hasNext())
				{
					String key = keys.next();
					httpConn.setRequestProperty(key, headers.get(key));
				}
			}
			
			httpConn.setRequestMethod(httpMethod);
			
			if (httpMethod.equalsIgnoreCase("POST"))
				preparePostRequest(httpConn,params);
			
			httpConn.connect();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			
			try
			{
				InputStream in = null;
				
				if (httpConn.getResponseCode() == 200)
				{
					in = httpConn.getInputStream();
				}
				else
				{
					in = httpConn.getErrorStream();
				}
				
				byte[] buf = new byte[3000];
				int count = 0;

				while ((count = in.read(buf)) > 0)
				{
					bout.write(buf, 0, count);
				}

				result = bout.toByteArray();
				
			}
			finally
			{
				if (bout != null)
					bout.close();
			}
 
			if (result != null)
				return new String(result,"UTF-8");
		}
		catch(IOException ex)
		{
			if (httpConn != null)
				httpConn.disconnect();
			
			throw new ServiceException(ex);
		}
		catch(Exception e)
		{
			throw new ServiceException(e);
		}
		
		return null;
	}
	
	
	/**
	 * 对post类型的请求做参数预处理，如果是multipart则开始组合数据对外写出
	 * @param httpConn
	 * @param params
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	static void preparePostRequest(HttpURLConnection httpConn,Map<String,Object>params) 
			throws UnsupportedEncodingException, IOException
	{
		if (params == null)
			return;
		
		httpConn.setDoOutput(true);
		
		boolean isMutilPart = false;
		
		for(Object v : params.values())
		{
			if (v instanceof RequestAttachment)
			{
				isMutilPart = true;
				break;
			}
		}
		
		if (isMutilPart)
		{
			String requestBoundary = "txwe9802";
			httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=txwe9802");
			
			httpConn.getOutputStream().write("--txwe9802\r\n".getBytes("UTF-8"));
			
			for(Map.Entry<String, Object> e : params.entrySet())
			{
				if (e.getValue() instanceof RequestAttachment)
				{
					StringBuilder content = new StringBuilder();
					
					RequestAttachment attach = (RequestAttachment)e.getValue();
					
					content.append("Content-Disposition: form-data; name=\"").append(e.getKey())
						.append("\"; filename=\"").append(attach.getName()).append("\"\r\n");
					content.append("Content-Type: application/octet-stream\r\n\r\n");
					
					httpConn.getOutputStream().write(content.toString().getBytes("UTF-8"));
					
					httpConn.getOutputStream().write(attach.getContent());
					
					httpConn.getOutputStream().write("\r\n".getBytes("UTF-8"));
					httpConn.getOutputStream().write("--txwe9802\r\n".getBytes("UTF-8"));
					
				}
				else
				{
					StringBuilder content = new StringBuilder();
					
					content.append("Content-Disposition: form-data; name=\"").append(e.getKey()).append("\"\r\n\r\n");
					content.append(e.getValue()).append("\r\n");
					content.append("--").append(requestBoundary).append("\r\n");
					
					httpConn.getOutputStream().write(content.toString().getBytes("UTF-8"));
					
				}
			}
			
			httpConn.getOutputStream().write("--txwe9802--\r\n".getBytes("UTF-8"));
			
		}
		else
		{
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			StringBuilder reqParams = new StringBuilder();
			for(Map.Entry<String, Object> e : params.entrySet())
			{
				reqParams.append(e.getKey())
						.append("=").append(e.getValue()).append("&");
			}
			
			httpConn.getOutputStream().write(reqParams.toString().getBytes("UTF-8"));
		}
			
		
	}
	
	/**
	 * 生成有效签名
	 * 
	 * @param params
	 * @param secret
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws Exception 
	 */
	public static String signature(Map<String, Object> params, String secret,
			boolean isHMac, String signName) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException{
		params.remove(signName);
		String[] names = params.keySet().toArray(ArrayUtils.EMPTY_STRING_ARRAY);
		Arrays.sort(names);
		StringBuilder sb = new StringBuilder();
		// append if not hmac
		if (!isHMac) 
		{
			sb.append(secret);
		}
		
		for (int i = 0; i < names.length; i++) 
		{
			String name = names[i];
			sb.append(name);
			sb.append(params.get(name));
		}
		
		if (!isHMac)
		{
			sb.append(secret);
		}
		
		String sign = null;

		if(isHMac) {
			//hmac
			sign = byte2hex(encryptHMAC(sb.toString().getBytes("utf-8"), secret.getBytes("utf-8")));
		} else {
			//md5
		sign = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"))
				.toUpperCase();
		}

		return sign;
	}
	
	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws Exception 
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);

	}

	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
	
	private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}
