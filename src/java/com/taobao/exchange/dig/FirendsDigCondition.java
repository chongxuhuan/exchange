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
	
	String secondHandPlatformID;//二手平台标示
	String secondHandUID; //二手平台身份id
	boolean indirectRelation;//是否是间接关系搜索
	
	public boolean isIndirectRelation() {
		return indirectRelation;
	}
	public void setIndirectRelation(boolean indirectRelation) {
		this.indirectRelation = indirectRelation;
	}
	public String getSecondHandPlatformID() {
		return secondHandPlatformID;
	}
	public void setSecondHandPlatformID(String secondHandPlatformID) {
		this.secondHandPlatformID = secondHandPlatformID;
	}
	public String getSecondHandUID() {
		return secondHandUID;
	}
	public void setSecondHandUID(String secondHandUID) {
		this.secondHandUID = secondHandUID;
	}

}
