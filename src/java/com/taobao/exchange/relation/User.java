/**
 * 
 */
package com.taobao.exchange.relation;

import com.taobao.exchange.util.AppClientUtil;

/**
 * 关系平台的用户信息
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class User implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7105030970070142792L;
	private String platformId;//平台id
	private String id;//用户id
	private String name;//用户名称
	
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String generateUserKey()
	{
		return AppClientUtil.generatePlatformUUID(platformId, id);
	}
}
