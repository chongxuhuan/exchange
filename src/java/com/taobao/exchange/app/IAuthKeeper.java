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
	
	public boolean store(String uid,AppAuthEntity authEntity);
	
	public AppAuthEntity take(String uid);

}
