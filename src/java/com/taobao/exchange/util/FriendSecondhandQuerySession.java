/**
 * 
 */
package com.taobao.exchange.util;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午12:38:55
 *
 */
public class FriendSecondhandQuerySession extends QuerySession {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2624462792829588148L;

	private String secondhandId;
	
	private String uid;
	
	private int round;

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getSecondhandId() {
		return secondhandId;
	}

	public void setSecondhandId(String secondhandId) {
		this.secondhandId = secondhandId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
