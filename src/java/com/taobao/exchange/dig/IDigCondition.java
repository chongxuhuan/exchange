/**
 * 
 */
package com.taobao.exchange.dig;


/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface IDigCondition {
	/**
	 * 获取每一页的结果集大小
	 * @return
	 */
	int getPage_size();
	/**
	 * 设置每一页的结果集大小
	 * @return
	 */
	void setPage_size(int page_size);
	/**
	 * 获得当前查询会话的缓存编号，用于翻页
	 * @return
	 */
	String getQuery_session();
	/**
	 * 设置当前查询会话的缓存编号，用于翻页
	 * @return
	 */
	void setQuery_session(String qsession);
	/**
	 * 结合query session，来做翻页操作，0当前页，－1上一页，1下一页
	 * @return
	 */
	int getOperation();
	/**
	 * 结合query session，来做翻页操作，0当前页，－1上一页，1下一页
	 * @return
	 */
	void setOperation(int operation);

}
