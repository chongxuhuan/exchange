/**
 * 
 */
package com.taobao.exchange.app.client;

import java.util.Map;

import com.taobao.exchange.app.AppAuthEntity;
import com.taobao.exchange.app.OpenPlatformEntry;
import com.taobao.exchange.util.ICache;
import com.taobao.exchange.util.ServiceException;


/**
 * 应用开放平台接口
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface IAppClient {
	
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
			,Map<String,Object> params) throws ServiceException;
	
	
	/**
	 * OAuth2流程中用code换accessToken
	 * @param oauth2第一步流程中的code
	 * @param 参看Oauth2协议
	 * @param 参看Oauth2协议
	 * @param 参看Oauth2协议
	 * @param 需要对callback增加的内容串，用与支持类似腾讯不支持state的情况
	 * @param 腾讯特殊的字符串
	 * @return
	 */
	public AppAuthEntity getAccessTokenByCodeAndStore(String code,String scope,String state,String view,String cbAppend,String openId) throws ServiceException;
	
	
	/**
	 * 获得授权的管理实现
	 * @param authCache
	 */
	public void setAuthCache(ICache<AppAuthEntity> authCache) throws ServiceException;
	
	/**
	 * 根据用户id获取授权
	 * @param uid
	 * @return
	 */
	public AppAuthEntity getAuthEntityByUid(String uid) throws ServiceException;
	
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

}
