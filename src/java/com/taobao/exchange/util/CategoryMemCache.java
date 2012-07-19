/**
 * 
 */
package com.taobao.exchange.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.exchange.secondhand.Category;
import com.taobao.exchange.secondhand.ISecondhandManager;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午8:09:17
 *
 */
public class CategoryMemCache extends MemCache<String, Category> {
	
	private static final Log logger = LogFactory.getLog(CategoryMemCache.class);
	
	ISecondhandManager<?> secondhandManager;

	public ISecondhandManager<?> getSecondhandManager() {
		return secondhandManager;
	}

	public void setSecondhandManager(ISecondhandManager<?> secondhandManager) {
		this.secondhandManager = secondhandManager;
	}
	
	public void load() throws ServiceException
	{
		if (secondhandManager != null)
		{
			Category[] categorys = secondhandManager.getSecondhandCategory();
			
			if (categorys !=  null && categorys.length > 0)
			{
				for (Category c : categorys)
				{
					this.put(c.getCid(), c);
				}
			}
		}
		else
		{
			logger.warn("sendhandManager is null, can't load Category cache.");
		}
	}

}
