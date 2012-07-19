/**
 * 
 */
package com.taobao.exchange.relation;

import com.taobao.exchange.util.Constants;

/**
 * 对于关系获取控制，保证隐私（卖东西可以被搜索，但是不可以被告知是什么关系层）
 * 指定搜索的时候需要多强的关系（互相关注，单向关注）
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-19
 *
 */
public class RelationConfig implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2760155078203730979L;
	
	boolean hideSecondhandUserInfo = false;//是否被搜索出来的商品要隐藏自己的名字
	
	int relationLevel = Constants.RELATION_LEVEL_BILATERAL;//搜索好友的关系设置

	public boolean isHideSecondhandUserInfo() {
		return hideSecondhandUserInfo;
	}

	public void setHideSecondhandUserInfo(boolean hideSecondhandUserInfo) {
		this.hideSecondhandUserInfo = hideSecondhandUserInfo;
	}

	public int getRelationLevel() {
		return relationLevel;
	}

	public void setRelationLevel(int relationLevel) {
		this.relationLevel = relationLevel;
	}

}
