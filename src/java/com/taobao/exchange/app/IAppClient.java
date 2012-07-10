/**
 * 
 */
package com.taobao.exchange.app;

import java.util.Map;

import com.taobao.exchange.util.AppClientException;


/**
 * Ӧ�ÿͻ��˽ӿڣ�ʵ�ַ�����ã��û���Ȩά���ȹ���
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4 ����6:09:07
 *
 */
public interface IAppClient {
	
	/**
	 * ����������
	 * @param ƽ̨id
	 * @param �û�id��������ò���Ҫ�û���Ȩ�ķ�����Դ����
	 * @param http�ķ�����֧��get��post
	 * @param httpheader�е�����
	 * @param ϵͳ��ҵ�����
	 * @return
	 */
	public String api(String platformId,String userId,String httpMethod,Map<String, String> headers
			,Map<String,Object> params) throws AppClientException;
	
	/**
	 * ������Ȩ��Ϣ��client��
	 * @param ����Ȩ��õ���auth���뵽client��
	 */
	public void addAuthToClient(AppAuthEntity auth);

}
