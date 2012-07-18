/**
 * 
 */
package com.taobao.exchange.app;

/**
 * 授权管理接口定义，授权可以存储在内存里面，
 * 也可以持久化到外部存储或者集中式缓存和DB
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-13
 *
 */
public interface IAuthKeeper {
	
	/**
	 * 存储用户对应的授权信息，授权信息对应与一个平台的一个应用的一个用户
	 * @param 用户id
	 * @param 授权信息
	 * @return
	 */
	public boolean store(String uid,AppAuthEntity authEntity);
	
	/**
	 * 获取授权信息
	 * @param uid
	 * @return
	 */
	public AppAuthEntity take(String uid);

}
