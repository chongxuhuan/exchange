/**
 * 
 */
package com.taobao.exchange.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

/**
 * 内存版的缓存实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012－7－12
 *
 */
public class MemCache<V> extends AbstractAsynFileStorage<String> implements ICache<V>{


	ConcurrentMap<String,V> innerCache = new ConcurrentHashMap<String,V>();
	
	public final static String split = "|-|";
	public final static String add = "+";
	public final static String delete = "-";
	public final static String clear = "--";
	
	public MemCache(String className, boolean needStartAsynMode) {
		super(className, needStartAsynMode);
	}
	
	@Override
	public void put(String name, V value) {
		innerCache.put(name, value);
		
		if(this.asynWriter != null)
			this.asynWriter.write(new StringBuilder(add)
			.append(split).append(name).append(split).append(value.hashCode()).append(split).append(value).toString());
	}


	@Override
	public V get(String name) {
		return innerCache.get(name);
	}

	
	@Override
	public V remove(String name) {
		
		if(this.asynWriter != null)
			this.asynWriter.write(new StringBuilder(delete)
				.append(split).append(name).append(split).append("").toString());
		
		return innerCache.remove(name);
	}
	
	@Override
	public void clear() {
		if(this.asynWriter != null)
			this.asynWriter.write(new StringBuilder(clear)
			.append(split).append("").append(split).append("").toString());
		
		innerCache.clear();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void asynLoadFormFile(String fileName) {
		
		BufferedReader fr = null;
		Map<String,String> objCache = new HashMap<String,String>();
		
		try
		{
			fr = new BufferedReader(new FileReader(new File(fileName)));
			
			String content = null;
			
			while((content = fr.readLine()) != null)
			{
				String[] cs = StringUtils.splitByWholeSeparator(content, split);
				
				if (cs == null || (cs != null && cs.length != 4))
					continue;
				
				String operate = cs[0].trim();
				String key = cs[1];
				String hashcode = cs[2];
				String value = cs[3].trim();
				
				
				if (operate.equals(add))
				{
					if (objCache.containsKey(hashcode))
					{
						Object t = this.get(objCache.get(hashcode));
						
						if (t instanceof ILocalSerializable)
							((ILocalSerializable) t).updateObjectFormString(value);
						
						innerCache.put(key, (V)t);
					}
					else
					{
						Class<?> c = Class.forName(className);
						
						Object v = c.getConstructor(String.class).newInstance(value);
						
						innerCache.put(key, (V)v);
						
						objCache.put(hashcode, key);
					}
				}
				else
					if (operate.equals(delete))
					{
						innerCache.remove(key);
					}
					else if (operate.equals(clear))
					{
						innerCache.clear();
					}
				
			}
			
		}
		catch(Exception ex)
		{
			if (fr != null)
			{
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			ex.printStackTrace();
		}
		
	}

}
