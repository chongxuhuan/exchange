/**
 * 
 */
package com.taobao.exchange.secondhand;

import java.util.List;

import com.taobao.exchange.app.IAppClient;



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
	 * 发布二手商品
	 * @param 二手商品
	 * @return
	 */
	OperationResult publish(Secondhand secondhand);
	
	/**
	 * 更新二手商品
	 * @param 二手商品
	 * @return
	 */
	OperationResult update(Secondhand secondhand);
	
	/**
	 * 删除二手商品
	 * @param 商品id
	 * @return
	 */
	OperationResult delete(String iid);
	
	/**
	 * 对二手商品做出评价
	 * @param 商品id
	 * @param content
	 * @return
	 */
	OperationResult comment(String iid,String content);
	
	/**
	 * 根据用户获得二手商品
	 * @param userId
	 * @return
	 */
	List<Secondhand> getSecondhandsByUser(String userId);
	
	/**
	 * 获取二手详细信息
	 * @param 商品id
	 * @return
	 */
	Secondhand getSecondhandById(String iid);

}
