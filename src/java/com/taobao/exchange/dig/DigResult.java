/**
 * 
 */
package com.taobao.exchange.dig;

import java.util.List;

import com.taobao.exchange.secondhand.Secondhand;

/**
 * 二手挖掘搜索的结果
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-15
 *
 */
public class DigResult implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2126147965122543576L;
	
	List<Secondhand> secondhands;//返回符合条件的二手商品列表
	String query_session;//用于查询上下翻页的会话，保存在服务端有对应上次搜索的位置
	String errorMessage;//错误信息
	
	public List<Secondhand> getSecondhands() {
		return secondhands;
	}
	public void setSecondhands(List<Secondhand> secondhands) {
		this.secondhands = secondhands;
	}
	public String getQuery_session() {
		return query_session;
	}
	public void setQuery_session(String query_session) {
		this.query_session = query_session;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
