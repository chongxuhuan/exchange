/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

import com.taobao.exchange.util.ILocalSerializable;
import com.taobao.exchange.util.ServiceException;


/**
 * 卖家用户，拥有一个二手平台帐号和一个或或者多个关系网站帐号
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-11
 *
 */
public class AccountZoo implements java.io.Serializable,ILocalSerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3370983959338992307L;
	
	public static String split = "\"||\"";

	User secondhandAccount;//二手交易平台身份
	private ConcurrentMap<String,User> relationAccounts;//多个社会化关系开放平台身份
	
	public AccountZoo()
	{	
		relationAccounts = new ConcurrentHashMap<String,User>();
	}
	
	public AccountZoo(String az) throws ServiceException
	{
		this.updateObjectFormString(az);		
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

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		
		if (secondhandAccount != null)
			result.append(secondhandAccount.toString());
		else
			result.append("null");
		
		result.append(split);
		
		
		
		for (User r : relationAccounts.values())
		{
			result.append(r.toString()).append(split);
		}
		
		return result.toString();
	}
	

	@Override
	public void updateObjectFormString(String content) throws ServiceException {
		relationAccounts = new ConcurrentHashMap<String,User>();
		
		if (content.indexOf(split) > 0)
		{
			String[] users = StringUtils.splitByWholeSeparator(content, split);
			
			if (!users[0].equals("null"))
			{
				secondhandAccount = new User(users[0]);
			}
			
			if (users.length > 1)
			{
				for (int i = 1; i < users.length; i++)
				{
					if (users[i].indexOf(User.split) < 0)
						continue;
						
					User u = new User(users[i]);
					relationAccounts.put(u.generateUserKey(), u);
				}
			}
			
		}
		else
			throw new ServiceException("User string format error!");
	}
	
}
