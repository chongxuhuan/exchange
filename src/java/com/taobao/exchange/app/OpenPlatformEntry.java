package com.taobao.exchange.app;

/**
 * 平台实体定义，包含对应的app信息
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class OpenPlatformEntry implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6423885060936123569L;
	
	private String id;//平台标示id
	private String apiEntry;//开放平台访问api的入口地址
	private String authEntry;//开放平台授权入口地址
	private String appKey;//开放平台中某一个app的身份
	private String appSecret;//开放平台中某一个app对应的secretcode
	private String callbackUrl;//对应app的回调地址，用于授权返回
	
	public OpenPlatformEntry(String id)
	{
		this.id = id;
	}
	
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
