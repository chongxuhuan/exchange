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

	User keyAccount;//应用内的唯一身份标示，如果是买家身份进入的，则可能是一个关系平台帐号，如果是卖家身份进入可以是二手身份，也可以是一个二手平台帐号
	User secondhandAccount;//二手交易平台身份
	private List<User> relationAccounts;//多个社会化关系开放平台身份
	
	public AccountZoo()
	{	
		relationAccounts = new CopyOnWriteArrayList<User>();
	}
	
	public String generateAccountZooKey()
	{
		return AppClientUtil.generatePlatformUUID(keyAccount.getPlatformId(), keyAccount.getId());
	}
	
	public User getKeyAccount() {
		return keyAccount;
	}

	public void setKeyAccount(User keyAccount) {
		this.keyAccount = keyAccount;
	}

	public User getSecondhandAccount() {
		return secondhandAccount;
	}

	public void setSecondhandAccount(User secondhandAccount) {
		this.secondhandAccount = secondhandAccount;
	}

	public List<User> getRelationAccounts() {
		return relationAccounts;
	}

	public void setRelationAccounts(List<User> relationAccounts) {
		this.relationAccounts = relationAccounts;
	}

}
