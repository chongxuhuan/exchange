/**
 * 
 */
package com.taobao.exchange.app;

import org.apache.commons.lang.StringUtils;

import com.taobao.exchange.relation.RelationConfig;

/**
 * 应用授权实体,支持多个开放平台的Oauth2协议
 * @author fangweng
 * @email: fangweng@taobao.com
 * @datetime: 2012-7-4
 *
 */
public class AppAuthEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8765771477651584870L;
	
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
	
	private RelationConfig relationConfig;
	
	public AppAuthEntity()
	{
		relationConfig = new RelationConfig();
	}
	
	/**
	 * 从josn字符串中获得Oauth的属性信息
	 * @param content
	 */
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
			
			if (c.indexOf("taobao_user_nick") >= 0)
			{
				this.nick = getStringValueFromJsonSplitStr(c);
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
	
	public RelationConfig getRelationConfig() {
		return relationConfig;
	}

	public void setRelationConfig(RelationConfig relationConfig) {
		this.relationConfig = relationConfig;
	}

	public String toString()
	{
		return new StringBuilder().append("uid=").append(uid).append(" , ")
				.append("nick=").append(nick).append(" , ")
				.append("accessToken=").append(accessToken).append(" , ")
				.append("refreshToken=").append(refreshToken).append(" , ")
				.append("refreshExpireTime=").append(refreshExpireTime).append(" , ")
				.append("r1ExpireTime=").append(r1ExpireTime).append(" , ")
				.append("r2ExpireTime=").append(r2ExpireTime).append(" , ")
				.append("w1ExpireTime=").append(w1ExpireTime).append(" , ")
				.append("w2ExpireTime=").append(w2ExpireTime).append(" , ").toString();
	}

}
