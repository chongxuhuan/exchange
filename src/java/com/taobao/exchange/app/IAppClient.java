/**
 * 
 */
package com.taobao.exchange.app;

import java.util.Map;

import com.taobao.exchange.util.AppClientException;


/**
 * 应用客户端接口，实现服务调用，用户授权维护等功能
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4 下午6:09:07
 *
 */
public interface IAppClient {
	
	/**
	 * 发起服务调用
	 * @param 平台id
	 * @param 用户id，如果调用不需要用户授权的服务可以传入空
	 * @param http的方法，支持get和post
	 * @param httpheader中的内容
	 * @param 系统和业务参数
	 * @return
	 */
	public String api(String platformId,String userId,String httpMethod,Map<String, String> headers
			,Map<String,Object> params) throws AppClientException;
	
	/**
	 * 增加授权信息到client中
	 * @param 将授权后得到的auth放入到client中
	 */
	public void addAuthToClient(AppAuthEntity auth);

}
