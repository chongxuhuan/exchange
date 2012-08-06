/**
 * 
 */
package com.taobao.exchange.util;

/**
 * 异步文件导入导出存储接口
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-8-1
 *
 */
public interface IAsynFileStorage<T> {
	
	/**
	 * 设置存储文件名称
	 * @param 文件名
	 */
	void setStoreFileName(String name);

	/**
	 * 异步存储对象到文件
	 * @param 要存储的对象
	 */
	void asynStore(T obj);
	
	/**
	 * 异步从文件中载入数据
	 * @param 文件名称
	 */
	void asynLoadFormFile(String fileName);
	
}
