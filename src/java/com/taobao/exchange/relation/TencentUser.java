/**
 * 
 */
package com.taobao.exchange.relation;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午6:30:55
 *
 */
public class TencentUser implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6910257237153915210L;
	private String name; //帐户名
	private String nick;//昵称
	private String head;//头像url
	private int sex;//用户性别，1-男，2-女，0-未填写
	private int fansnum;//听众数
	private int idolnum;//收听人数
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getFansnum() {
		return fansnum;
	}
	public void setFansnum(int fansnum) {
		this.fansnum = fansnum;
	}
	public int getIdolnum() {
		return idolnum;
	}
	public void setIdolnum(int idolnum) {
		this.idolnum = idolnum;
	}

}
