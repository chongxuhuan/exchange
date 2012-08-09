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

	private String secondhandId;//当前搜索到的二手id，为了下一次继续搜索
	
	private String uid;//当前搜索到的用户id，为了下一次继续搜索
	
	private int friendCursor = 0;//对于间接朋友商品搜索，朋友搜索的游标，为了下一次继续搜索
	
	private int friendPageSize = 20;//对于间接朋友商品搜索，每一次捞取多少个间接好友
	
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

	public int getFriendCursor() {
		return friendCursor;
	}

	public void setFriendCursor(int friendCursor) {
		this.friendCursor = friendCursor;
	}

	public int getFriendPageSize() {
		return friendPageSize;
	}

	public void setFriendPageSize(int friendPageSize) {
		this.friendPageSize = friendPageSize;
	}

}
