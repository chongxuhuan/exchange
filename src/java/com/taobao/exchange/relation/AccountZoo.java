/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


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

	User secondhandAccount;//二手交易平台身份
	private ConcurrentMap<String,User> relationAccounts;//多个社会化关系开放平台身份
	
	public AccountZoo()
	{	
		relationAccounts = new ConcurrentHashMap<String,User>();
	}

	public User getSecondhandAccount() {
		return secondhandAccount;
	}

	public void setSecondhandAccount(User secondhandAccount) {
		this.secondhandAccount = secondhandAccount;
	}
	
	public void storeRelation(User user)
	{
		relationAccounts.put(user.generateUserKey(), user);
	}
	
	public User getRelation(String relation)
	{
		return relationAccounts.get(relation);
	}
	
	public Collection<User> getAllRelations()
	{
		return relationAccounts.values();
	}
	
	public void clearAllRelations()
	{
		relationAccounts.clear();
	}

}
