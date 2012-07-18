/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.taobao.exchange.util.AppClientUtil;

/**
 * 卖家用户，拥有一个二手平台帐号和一个或或者多个关系网站帐号
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-11
 *
 */
public class AccountZoo implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3370983959338992307L;
	
	private String secondHandPlatformID;//二手交易开放平台身份
	private String secondHandUID;//二手交易开放平台用户id
	private List<User> relationAccounts;//多个社会化关系开放平台身份
	
	public AccountZoo()
	{	
		relationAccounts = new CopyOnWriteArrayList<User>();
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

	public List<User> getRelationAccounts() {
		return relationAccounts;
	}

	public void setRelationAccounts(List<User> relationAccounts) {
		this.relationAccounts = relationAccounts;
	}
	
	/**
	 * 用于作为外部帐号的主键
	 * @return
	 */
	public String generateAccountZooKey()
	{
		return AppClientUtil.generatePlatformUUID(secondHandPlatformID,secondHandUID);
	}

}
