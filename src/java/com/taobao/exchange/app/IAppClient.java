/**
 * 
 */
package com.taobao.exchange.app;

import java.util.Map;

import com.taobao.exchange.util.AppClientException;


/**
 * 应用开放平台接口
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface IAppClient {
	
	/**
	 * 获取内在平台信息
	 * @return
	 */
	public OpenPlatformEntry getOpenPlatformEntry();
	
	
	/**
	 * 设置平台信息
	 * @param 平台信息
	 */
	public void setOpenPlatformEntry(OpenPlatformEntry openPlatformEntry);
	
	/**
	 * 服务调用接口
	 * @param 用户身份id，如果不需要授权的服务，可以不传入
	 * @param http方法类型，支持get和post
	 * @param 服务名称
	 * @param http消息头中的内容
	 * @param http请求消息体的内容，如果是文件类型，则用AppRequestAttachment来封装
	 * @return
	 */
	public String api(String userId,String httpMethod,String apiName,Map<String, String> headers
			,Map<String,Object> params) throws AppClientException;
	
	
	/**
	 * OAuth2流程中用code换accessToken
	 * @param oauth2第一步流程中的code
	 * @param 参看Oauth2协议
	 * @param 参看Oauth2协议
	 * @param 参看Oauth2协议
	 * @return
	 */
	public AppAuthEntity getAccessTokenByCode(String code,String scope,String state,String view) throws AppClientException;
	
	/**
	 * 将授权增加到平台客户端中，为后续服务调用使用
	 * @param 授权对象
	 */
	public void addAuthToClient(AppAuthEntity auth);

}
