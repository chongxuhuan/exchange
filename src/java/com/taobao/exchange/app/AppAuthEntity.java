/**
 * 
 */
package com.taobao.exchange.app;

import org.apache.commons.lang.StringUtils;

/**
 * 应用授权实体
 * @author fangweng
 * @email: fangweng@taobao.com
 * @datetime: 2012-7-4
 *
 */
public class AppAuthEntity {
	
	private String uid;
	private String accessToken;
	private int expireTime;
	private String refreshToken;
	private int refreshExpireTime;
	private int r1ExpireTime;
	private int r2ExpireTime;
	private int w1ExpireTime;
	private int w2ExpireTime;
	
	public void loadAuthInfoFromJsonString(String content)
	{
		if (content == null)
			return;
		
		content = content.substring(content.indexOf("{")+1,content.indexOf("}"));
		
		String[] cs = StringUtils.splitByWholeSeparator(content, ",");
		
		for(String c : cs)
		{
			if (c.indexOf("access_token") >= 0)
			{
				this.accessToken = getStringValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("refresh_token") >= 0)
			{
				this.refreshToken = getStringValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("re_expires_in") >= 0)
			{
				this.refreshExpireTime = getIntegerValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("taobao_user_id") >= 0 || c.indexOf("uid") >= 0)
			{
				this.uid = getStringValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("r1_expires_in") >= 0)
			{
				this.r1ExpireTime = getIntegerValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("r2_expires_in") >= 0)
			{
				this.r2ExpireTime = getIntegerValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("w1_expires_in") >= 0)
			{
				this.w1ExpireTime = getIntegerValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("w2_expires_in") >= 0)
			{
				this.w2ExpireTime = getIntegerValueFromJsonSplitStr(c);
				continue;
			}
			
			if (c.indexOf("expires_in") >= 0)
			{
				this.expireTime = getIntegerValueFromJsonSplitStr(c);
				continue;
			}
			
		}
	}
	
	private String getStringValueFromJsonSplitStr(String splitStr)
	{
		return splitStr.substring(splitStr.indexOf("\"", splitStr.indexOf(":"))+1,splitStr.lastIndexOf("\""));
	}
	
	private int getIntegerValueFromJsonSplitStr(String splitStr)
	{
		return Integer.parseInt(splitStr.substring(splitStr.indexOf(":")+1).trim());
	}
	
	public int getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public int getRefreshExpireTime() {
		return refreshExpireTime;
	}
	public void setRefreshExpireTime(int refreshExpireTime) {
		this.refreshExpireTime = refreshExpireTime;
	}
	public int getR1ExpireTime() {
		return r1ExpireTime;
	}
	public void setR1ExpireTime(int r1ExpireTime) {
		this.r1ExpireTime = r1ExpireTime;
	}
	public int getR2ExpireTime() {
		return r2ExpireTime;
	}
	public void setR2ExpireTime(int r2ExpireTime) {
		this.r2ExpireTime = r2ExpireTime;
	}
	public int getW1ExpireTime() {
		return w1ExpireTime;
	}
	public void setW1ExpireTime(int w1ExpireTime) {
		this.w1ExpireTime = w1ExpireTime;
	}
	public int getW2ExpireTime() {
		return w2ExpireTime;
	}
	public void setW2ExpireTime(int w2ExpireTime) {
		this.w2ExpireTime = w2ExpireTime;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String toString()
	{
		return new StringBuilder().append("uid=").append(uid).append(" , ")
				.append("accessToken=").append(accessToken).append(" , ")
				.append("refreshToken=").append(refreshToken).append(" , ")
				.append("refreshExpireTime=").append(refreshExpireTime).append(" , ")
				.append("r1ExpireTime=").append(r1ExpireTime).append(" , ")
				.append("r2ExpireTime=").append(r2ExpireTime).append(" , ")
				.append("w1ExpireTime=").append(w1ExpireTime).append(" , ")
				.append("w2ExpireTime=").append(w2ExpireTime).append(" , ").toString();
	}

}
