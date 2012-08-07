/**
 * 
 */
package com.taobao.exchange.app;

import org.apache.commons.lang.StringUtils;
import com.taobao.exchange.util.ILocalSerializable;

/**
 * 应用授权实体,支持多个开放平台的Oauth2协议
 * @author fangweng
 * @email: fangweng@taobao.com
 * @datetime: 2012-7-4
 *
 */
public class AppAuthEntity implements java.io.Serializable,ILocalSerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8765771477651584870L;
	
	private String platformId;//平台id
	private String uid;//用户id
	private String nick;//用户nick，不是每个平台都会有，淘宝开放平台授权后就会有，其他平台需要再多一次查询转换
	private String accessToken;//授权token
	private int expireTime;//OAuth的失效时间
	private String refreshToken;//OAuth的刷新Token，不是每一个开放平台都会有，淘宝的有
	private int refreshExpireTime;//OAuth的刷新Token失效时间
	private int r1ExpireTime;//淘宝开放平台r1级别服务调用Token失效时间
	private int r2ExpireTime;//淘宝开放平台r2级别服务调用Token失效时间
	private int w1ExpireTime;//淘宝开放平台w1级别服务调用Token失效时间
	private int w2ExpireTime;//淘宝开放平台w2级别服务调用Token失效时间
	
	private String openId;//腾讯微博特有的用户标示
	
	public AppAuthEntity(){}
	
	public AppAuthEntity(String content)
	{
		updateObjectFormString(content);
	}
	
	/**
	 * 从字符串中获得Oauth的属性信息
	 * @param content
	 */
	public void updateObjectFormString(String content)
	{
		if (content == null)
			return;
		
		boolean isJsonStr = false;
		
		//部分开放平台不是json数据
		if (content.indexOf("{") >= 0)
		{
			content = content.substring(content.indexOf("{")+1,content.lastIndexOf("}"));
			isJsonStr = true;
		}
		
		String[] cs;
		
		if (content.indexOf(",") >= 0)
			cs = StringUtils.splitByWholeSeparator(content, ",");
		else
			cs = StringUtils.splitByWholeSeparator(content, "&");
		
		for(String c : cs)
		{
			c = c.trim();
			
			if (c.indexOf("platformId") >=0)
			{
				if(isJsonStr)
					this.platformId = getStringValueFromJsonSplitStr(c);
				else
					if (c.indexOf("=null") < 0)
						this.platformId = StringUtils.split(c, "=")[1];
				
				continue;
			}
			
			if (c.indexOf("access_token") >= 0)
			{
				if(isJsonStr)
					this.accessToken = getStringValueFromJsonSplitStr(c);
				else
					if (c.indexOf("=null") < 0)
						this.accessToken = StringUtils.split(c, "=")[1];
				
				continue;
			}
			
			if (c.indexOf("refresh_token") >= 0)
			{
				if (isJsonStr)
					this.refreshToken = getStringValueFromJsonSplitStr(c);
				else
					if (c.indexOf("=null") < 0)
						this.refreshToken = StringUtils.split(c, "=")[1];
				
				continue;
			}
			
			if (c.indexOf("re_expires_in") >= 0)
			{
				if (isJsonStr)
					this.refreshExpireTime = getIntegerValueFromJsonSplitStr(c);
				else
					this.refreshExpireTime = Integer.valueOf(StringUtils.split(c, "=")[1]);
				
				continue;
			}
			
			if (c.indexOf("taobao_user_nick") >= 0 || c.indexOf("name") >= 0)
			{
				if (isJsonStr)
					this.nick = getStringValueFromJsonSplitStr(c);
				else
					if (c.indexOf("=null") < 0)
						this.nick = StringUtils.split(c, "=")[1];
				
				continue;
			}
			
			if (c.indexOf("taobao_user_id") >= 0 || c.indexOf("uid") >= 0 || c.indexOf("id") >= 0)
			{
				if (isJsonStr)
					this.uid = getStringValueFromJsonSplitStr(c);
				else
					if (c.indexOf("=null") < 0)
						this.uid = StringUtils.split(c, "=")[1];
					
				continue;
			}
			
			if (c.indexOf("r1_expires_in") >= 0)
			{
				if (isJsonStr)
					this.r1ExpireTime = getIntegerValueFromJsonSplitStr(c);
				else
					this.r1ExpireTime = Integer.valueOf(StringUtils.split(c, "=")[1]);
				
				continue;
			}
			
			if (c.indexOf("r2_expires_in") >= 0)
			{
				if (isJsonStr)
					this.r2ExpireTime = getIntegerValueFromJsonSplitStr(c);
				else
					this.r2ExpireTime = Integer.valueOf(StringUtils.split(c, "=")[1]);
				
				continue;
			}
			
			if (c.indexOf("w1_expires_in") >= 0)
			{
				if (isJsonStr)
					this.w1ExpireTime = getIntegerValueFromJsonSplitStr(c);
				else
					this.w1ExpireTime = Integer.valueOf(StringUtils.split(c, "=")[1]);
					
				continue;
			}
			
			if (c.indexOf("w2_expires_in") >= 0)
			{
				if (isJsonStr)
					this.w2ExpireTime = getIntegerValueFromJsonSplitStr(c);
				else
					this.w2ExpireTime = Integer.valueOf(StringUtils.split(c, "=")[1]);
					
				continue;
			}
			
			if (c.indexOf("expires_in") >= 0)
			{
				if (isJsonStr)
					this.expireTime = getIntegerValueFromJsonSplitStr(c);
				else
					this.expireTime = Integer.valueOf(StringUtils.split(c, "=")[1]);
				continue;
			}
			
			if (c.indexOf("openId") >= 0)
			{
				if (isJsonStr)
					this.openId = getStringValueFromJsonSplitStr(c);
				else
					if (c.indexOf("=null") < 0)
						this.openId = StringUtils.split(c, "=")[1];
				continue;
			}
			
		}
		
		//处理腾迅的情况
		if (this.nick != null && this.uid == null)
		{
			this.uid = this.nick;
		}
	}
	
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	private String getStringValueFromJsonSplitStr(String splitStr)
	{
		if(splitStr.indexOf("{") >=0)
		{
			splitStr = splitStr.substring(splitStr.indexOf("{")+1);
			return splitStr.substring(splitStr.indexOf(":")+1);
		}
		else
			return splitStr.substring(splitStr.indexOf("\"", splitStr.indexOf(":"))+1,splitStr.lastIndexOf("\""));
	}
	
	private int getIntegerValueFromJsonSplitStr(String splitStr)
	{
		return Integer.parseInt(splitStr.substring(splitStr.indexOf(":")+1).trim());
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String toString()
	{
		return new StringBuilder().append("platformId=").append(platformId).append(",")
				.append("uid=").append(uid).append(",")
				.append("name=").append(nick).append(",")
				.append("access_token=").append(accessToken).append(",")
				.append("refresh_token=").append(refreshToken).append(",")
				.append("re_expires_in=").append(refreshExpireTime).append(",")
				.append("r1_expires_in=").append(r1ExpireTime).append(",")
				.append("r2_expires_in=").append(r2ExpireTime).append(",")
				.append("w1_expires_in=").append(w1ExpireTime).append(",")
				.append("w2_expires_in=").append(w2ExpireTime).append(",")
				.append("expires_in=").append(expireTime).append(",")
				.append("openId=").append(openId).toString();
	}

}
