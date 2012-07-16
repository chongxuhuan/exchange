/**
 * 
 */
package com.taobao.exchange.dig;

import java.util.List;

import com.taobao.exchange.secondhand.Secondhand;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午11:40:02
 *
 */
public class DigResult implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2126147965122543576L;
	
	List<Secondhand> secondhands;
	String query_session;
	String errorMessage;
	
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
