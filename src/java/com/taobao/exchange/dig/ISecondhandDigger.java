/**
 * 
 */
package com.taobao.exchange.dig;


import com.taobao.exchange.relation.AccountZoo;
import com.taobao.exchange.secondhand.Secondhand;
import com.taobao.exchange.util.AppClientException;
import com.taobao.exchange.util.ICache;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface ISecondhandDigger<T extends IDigCondition> {
	
	void setContextCache(ICache<String, String> contextCache);
	void setAccountZooCache(ICache<String, AccountZoo> accountZooCache);
	void setRelationAccountZooCache(
			ICache<String, AccountZoo> relationAccountZooCache);
	
	DigResult dig(T condition) throws AppClientException;
	
	boolean checkSecondhandByCondition(Secondhand s, T condition);

}
