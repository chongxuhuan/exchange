/**
 * 
 */
package com.taobao.exchange.dig;


import com.taobao.exchange.relation.AccountZoo;
import com.taobao.exchange.secondhand.Secondhand;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.ICache;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface ISecondhandDigger<T extends IDigCondition> {
	
	/**
	 * 设置某一个关系帐号和二手卖家帐号的对应关系，用于将关系帐号转变为卖家帐号的场景（新浪好友A是否是个淘宝二手卖家）
	 * @param userToAccountZooCache
	 */
	void setUserToAccountZooCache(
			ICache<AccountZoo> userToAccountZooCache);
	
	/**
	 * 挖掘二手商品
	 * @param condition
	 * @return
	 * @throws ServiceException
	 */
	DigResult dig(T condition) throws ServiceException;
	
	/**
	 * 检查搜索出来的二手是否符合过滤条件，通常用于好友的二手搜索以后再次需要过滤
	 * @param s
	 * @param condition
	 * @return
	 */
	boolean checkSecondhandByCondition(Secondhand s, T condition);

}
