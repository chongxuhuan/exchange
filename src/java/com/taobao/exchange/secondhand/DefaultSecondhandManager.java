/**
 * 
 */
package com.taobao.exchange.secondhand;

import java.util.List;

import com.taobao.exchange.app.TopAppClient;

/**
 * 二手商品管理接口默认实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-10
 *
 */
public class DefaultSecondhandManager implements ISecondhandManager<TopAppClient> {

	/**
	 * 二手交易的应用客户端支持
	 */
	TopAppClient appClient;

	public void setAppClient(TopAppClient appClient) {
		this.appClient = appClient;
	}

	@Override
	public OperationResult publish(Secondhand secondhand) {
		
		
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.taobao.exchange.secondhand.ISecondhandManager#update(com.taobao.exchange.secondhand.Secondhand)
	 */
	@Override
	public OperationResult update(Secondhand secondhand) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.taobao.exchange.secondhand.ISecondhandManager#delete(java.lang.String)
	 */
	@Override
	public OperationResult delete(String iid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.taobao.exchange.secondhand.ISecondhandManager#comment(java.lang.String, java.lang.String)
	 */
	@Override
	public OperationResult comment(String iid, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.taobao.exchange.secondhand.ISecondhandManager#getSecondhandsByUser(java.lang.String)
	 */
	@Override
	public List<Secondhand> getSecondhandsByUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.taobao.exchange.secondhand.ISecondhandManager#getSecondhandById(java.lang.String)
	 */
	@Override
	public Secondhand getSecondhandById(String iid) {
		// TODO Auto-generated method stub
		return null;
	}

}
