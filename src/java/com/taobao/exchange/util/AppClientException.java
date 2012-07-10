/**
 * 
 */
package com.taobao.exchange.util;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9
 *
 */
public class AppClientException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2061969627329617762L;
	
	public AppClientException() {
		super();
	}

	public AppClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppClientException(String message) {
		super(message);
	}

	public AppClientException(Throwable cause) {
		super(cause);
	}

}
