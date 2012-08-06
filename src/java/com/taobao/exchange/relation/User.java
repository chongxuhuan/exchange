/**
 * 
 */
package com.taobao.exchange.relation;

import org.apache.commons.lang.StringUtils;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.util.AppClientUtil;
import com.taobao.exchange.util.ILocalSerializable;
import com.taobao.exchange.util.ServiceException;

/**
 * 关系平台的用户信息
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class User implements java.io.Serializable,ILocalSerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7105030970070142792L;
	private String platformId;//平台id
	private String id;//用户id
	private String name;//用户名称
	
	public static String split = "\"-\"";
	
	public User()
	{
		super();
	}
	
	public User(String u) throws ServiceException
	{
		updateObjectFormString(u);
	}
	
	public User(AppAuthEntity authEntity)
	{
		this.platformId = authEntity.getPlatformId();
		this.id = authEntity.getUid();
		this.name = authEntity.getNick();
	}
	
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String generateUserKey()
	{
		return AppClientUtil.generatePlatformUUID(platformId, id);
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder().append(platformId)
				.append(split).append(id).append(split).append(name).toString();
	}

	@Override
	public void updateObjectFormString(String content) throws ServiceException {
		if (content.indexOf(split) > 0)
		{
			String[] us = StringUtils.splitByWholeSeparator(content, split);
			
			this.setPlatformId(us[0]);
			this.setId(us[1]);
			
			if (!us[2].equals("null"))
				this.setName(us[2]);
			
		}
		else
			throw new ServiceException("User string format error!");
	}
}
