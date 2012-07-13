/**
 * 
 */
package com.taobao.exchange.secondhand;


import com.taobao.exchange.app.IAppClient;
import com.taobao.exchange.util.AppClientException;



/**
 * 二手管理接口
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface ISecondhandManager<C extends IAppClient> {
	
	
	/**
	 * 设置支持二手交易的应用客户端
	 * @param appClient
	 */
	void setAppClient(C appClient);
	
	/**
	 * 获取二手商品类目
	 * @return
	 */
	Category[] getSecondhandCategory() throws AppClientException;
	
	/**
	 * 发布二手商品
	 * @param 卖家id
	 * @param 二手商品
	 * @return
	 */
	OperationResult publish(String userId,Secondhand secondhand) throws AppClientException;
	
	/**
	 * 更新二手商品
	 * @param 卖家id
	 * @param 二手商品
	 * @return
	 */
	OperationResult update(String userId,Secondhand secondhand) throws AppClientException;
	
	/**
	 * 删除二手商品
	 * @param 卖家id
	 * @param 商品id
	 * @return
	 */
	OperationResult delete(String userId,String iid) throws AppClientException;
	
	/**
	 * 对二手商品做出评价
	 * @param 用户id
	 * @param 商品id
	 * @param content
	 * @param title
	 * @return
	 */
	boolean comment(String userId,String iid,String content,String title) throws AppClientException;
	
	/**
	 * 根据用户获得二手商品
	 * @param userId
	 * @return
	 */
	Secondhand[] getSecondhandsByUser(String userId) throws AppClientException;
	
	/**
	 * 获取二手详细信息
	 * @param 商品id
	 * @return
	 */
	Secondhand getSecondhandById(String iid) throws AppClientException;

}
