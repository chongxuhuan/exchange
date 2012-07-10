/**
 * 
 */
package com.taobao.exchange.app;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-5 ÏÂÎç3:27:04
 *
 */
public class OpenPlatformManager {
	
	static ConcurrentMap<String,OpenPlatformEntry> pools = new ConcurrentHashMap<String,OpenPlatformEntry>();
	
	public static OpenPlatformEntry register(String id ,String apiEntry,String authEntry,String appKey,String appSecretcode,String callbackUrl)
	{
		OpenPlatformEntry entry = null;
		
		if (pools.containsKey(id))
		{
			return pools.get(id);
		}
		else
		{
			entry = new OpenPlatformEntry();
			entry.setApiEntry(apiEntry);
			entry.setAppKey(appKey);
			entry.setAppSecretcode(appSecretcode);
			entry.setAuthEntry(authEntry);
			entry.setCallbackUrl(callbackUrl);
			entry.setId(id);
			
			entry = pools.putIfAbsent(id, entry);
		}
		
		return entry;	
	}
	
	
	public static OpenPlatformEntry getOpenPlatformEntryFromPoolsById(String id)
	{
		return pools.get(id);
	}

}
