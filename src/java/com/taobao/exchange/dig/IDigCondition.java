/**
 * 
 */
package com.taobao.exchange.dig;

import com.taobao.exchange.util.QuerySession;


/**
 * 二手搜索挖掘条件定义
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface IDigCondition {
	
	public QuerySession getQsession();
	
	public void setQsession(QuerySession qsession);

}
