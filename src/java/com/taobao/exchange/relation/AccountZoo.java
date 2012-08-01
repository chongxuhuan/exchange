/**
 * 
 */
package com.taobao.exchange.relation;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import com.taobao.exchange.util.ServiceException;


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
	
	public static String split = "\"||\"";

	User secondhandAccount;//二手交易平台身份
	private ConcurrentMap<String,User> relationAccounts;//多个社会化关系开放平台身份
	
	public AccountZoo()
	{	
		relationAccounts = new ConcurrentHashMap<String,User>();
	}
	
	public AccountZoo(String az) throws ServiceException
	{
		relationAccounts = new ConcurrentHashMap<String,User>();
		
		if (az.indexOf(split) > 0)
		{
			String[] users = StringUtils.splitByWholeSeparator(az, split);
			
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
	
	
	public static void main(String[] args) throws ServiceException
	{
		AccountZoo az = new AccountZoo();
//		User s = new User();
//		s.setId("sfafda");
//		s.setPlatformId("taobao");
//		az.setSecondhandAccount(s);
		
		User r1 = new User();
		r1.setId("1231312");
		r1.setPlatformId("sina");
		az.storeRelation(r1);
		
		User r2 = new User();
		r2.setId("12313adsf12");
		r2.setPlatformId("tencent");
		r2.setName("fangweng");
		az.storeRelation(r2);
		
		String ss = az.toString();
		
		System.out.println(ss);
		
		AccountZoo az1 = new AccountZoo(ss);
		System.out.println(az1.toString());
		
	}
	
}
