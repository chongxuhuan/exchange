package com.taobao.exchange.app;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4 ÏÂÎç4:23:26
 *
 */
public class OpenPlatformEntry {
	
	private String id;
	private String apiEntry;
	private String authEntry;
	private String appKey;
	private String appSecretcode;
	private String callbackUrl;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getAppSecretcode() {
		return appSecretcode;
	}
	public void setAppSecretcode(String appSecretcode) {
		this.appSecretcode = appSecretcode;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	

}
