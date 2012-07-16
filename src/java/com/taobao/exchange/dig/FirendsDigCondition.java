/**
 * 
 */
package com.taobao.exchange.dig;


/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class FirendsDigCondition extends SecondhandCondition {
	
	String secondHandPlatformID;
	String secondHandUID; 
	boolean indirectRelation;
	
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
