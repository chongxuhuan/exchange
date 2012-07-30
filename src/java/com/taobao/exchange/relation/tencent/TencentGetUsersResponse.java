/**
 * 
 */
package com.taobao.exchange.relation.tencent;


/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午6:28:21
 *
 */
public class TencentGetUsersResponse {
	
	private int curnum;
	private int hasnext;
	private TencentUser[] info;
	private int nextstartpos;
	private int totalnum;
	
	public int getCurnum() {
		return curnum;
	}
	public void setCurnum(int curnum) {
		this.curnum = curnum;
	}
	public int getHasnext() {
		return hasnext;
	}
	public void setHasnext(int hasnext) {
		this.hasnext = hasnext;
	}
	public TencentUser[] getInfo() {
		return info;
	}
	public void setInfo(TencentUser[] info) {
		this.info = info;
	}
	public int getNextstartpos() {
		return nextstartpos;
	}
	public void setNextstartpos(int nextstartpos) {
		this.nextstartpos = nextstartpos;
	}
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}

}
