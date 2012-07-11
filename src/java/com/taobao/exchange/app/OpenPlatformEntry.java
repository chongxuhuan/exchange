package com.taobao.exchange.app;

/**
 * 平台实体定义，包含对应的app信息
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class OpenPlatformEntry {
	
	private String apiEntry;
	private String authEntry;
	private String appKey;
	private String appSecret;
	private String callbackUrl;
	
	public String getApiEntry() {
		return apiEntry;
	}
	public void setApiEntry(String apiEntry) {
		this.apiEntry = apiEntry;
	}
	public String getAuthEntry() {
		return authEntry;
	}
	public void setAuthEntry(String authEntry) {
		this.authEntry = authEntry;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
