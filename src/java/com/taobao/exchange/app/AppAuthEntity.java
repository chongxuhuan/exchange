/**
 * 
 */
package com.taobao.exchange.app;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * @datetime: 2012-7-4 обнГ4:23:26
 *
 */
public class AppAuthEntity {
	
	private String platformId;
	private String uid;
	private String accessToken;
	
	
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

}
