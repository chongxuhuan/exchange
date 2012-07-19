/**
 * 
 */
package com.taobao.exchange.dig;


/**
 * 好友关系二手搜索条件定义
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class FirendsDigCondition extends SecondhandCondition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 227897228753040294L;
	
	String secondHandPlatformID;
	String platformID;//开放平台标示
	String uid; //用户id
	boolean indirectRelation;//是否是间接关系搜索
	
	
	public String getSecondHandPlatformID() {
		return secondHandPlatformID;
	}
	public void setSecondHandPlatformID(String secondHandPlatformID) {
		this.secondHandPlatformID = secondHandPlatformID;
	}
	public boolean isIndirectRelation() {
		return indirectRelation;
	}
	public void setIndirectRelation(boolean indirectRelation) {
		this.indirectRelation = indirectRelation;
	}
	public String getPlatformID() {
		return platformID;
	}
	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
