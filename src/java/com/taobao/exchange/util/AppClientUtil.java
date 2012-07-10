package com.taobao.exchange.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import com.taobao.exchange.app.AppRequestAttachment;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9 ÏÂÎç10:13:42
 *
 */
public class AppClientUtil {
	
	public static String sendRequest(String url,String httpMethod,Map<String,String>headers,Map<String,Object>params) 
			throws AppClientException
	{
		byte[] result = null;
		HttpURLConnection httpConn = null;
		
		try
		{
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
			
			prepareRequest(httpConn,httpMethod,params);
			
			httpConn.connect();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			
			try
			{
				InputStream in = httpConn.getInputStream();

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
			
			throw new AppClientException(ex);
		}
		catch(Exception e)
		{
			throw new AppClientException(e);
		}
		
		return null;
	}
	
	
	static void prepareRequest(HttpURLConnection httpConn,String httpMethod,Map<String,Object>params) 
			throws UnsupportedEncodingException, IOException
	{
		if (httpMethod.equalsIgnoreCase("GET"))
		{
			for(Map.Entry<String, Object> e : params.entrySet())
				httpConn.addRequestProperty(e.getKey(), (String)e.getValue());
		}
		else
		{
			httpConn.setDoOutput(true);
			
			boolean isMutilPart = false;
			
			for(Object v : params.values())
			{
				if (v instanceof AppRequestAttachment)
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
					if (e.getValue() instanceof AppRequestAttachment)
					{
						StringBuilder content = new StringBuilder();
						
						AppRequestAttachment attach = (AppRequestAttachment)e.getValue();
						
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
				httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
				for(Map.Entry<String, Object> e : params.entrySet())
				{
					httpConn.getOutputStream().write(new StringBuilder().append(e.getKey())
							.append("=").append((String)e.getValue()).append("&")
							.toString().getBytes("UTF-8"));
				}
			}
			
		}
	}

}
