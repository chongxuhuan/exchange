/**
 * 
 */
package com.taobao.exchange.util;

/**
 * 应用客户端异常
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9
 *
 */
public class ServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2061969627329617762L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
