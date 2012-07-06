/**
 * 
 */
package com.taobao.exchange.app;

import java.util.Map;


/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4 обнГ6:09:07
 *
 */
public interface IAppSDK {
	
	AppAuthEntity auth(OpenPlatformEntry platform);
	
	String api(OpenPlatformEntry platform,Map<String,String> params);

}
