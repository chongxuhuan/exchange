/**
 * 
 */
package com.taobao.exchange.util;

import com.taobao.top.xbox.bloom.ByteBloomFilter;



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
	
	//可以用于过滤以前处理过的数据,这里需要考虑设置容量，防止内存溢出
	//fixme 要用bloom filter
	//private Map<String,String> filter;
	ByteBloomFilter bloomFilter;

	public QuerySession()
	{
		//filter = new HashMap<String,String>();
		bloomFilter = new ByteBloomFilter(10000,0.0001F,1);
	}
	
	public void addFilterEntry(String k,String v)
	{
		bloomFilter.add(k.getBytes());
		//filter.put(k, v);
	}
	
	public void clearFilter()
	{
		bloomFilter = null;
		bloomFilter = new ByteBloomFilter(10000,0.0001F,1);
		//filter.clear();
	}
	
	public boolean needFilter(String k)
	{
		return bloomFilter.contains(k.getBytes());
		//return filter.containsKey(k);
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
