/**
 * 
 */
package com.taobao.exchange.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午11:51:10
 *
 */
public class QuerySession implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7122552414003182536L;

	private int cursor = 0;
	
	private int pageSize = 20;
	
	private Map<String,String> filter;//可以用于过滤以前处理过的数据,这里需要考虑设置容量，防止内存溢出

	public QuerySession()
	{
		filter = new HashMap<String,String>();
	}
	
	public void addFilterEntry(String k,String v)
	{
		filter.put(k, v);
	}
	
	public void clearFilter()
	{
		filter.clear();
	}
	
	public boolean needFilter(String k)
	{
		return filter.containsKey(k);
	}
	
	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String toString()
	{
		return new StringBuilder().append(cursor).append(":").append(pageSize).toString();
	}
	
}
